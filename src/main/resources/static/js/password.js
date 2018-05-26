$(function () {
    $("#save").click(function () {
        var oldPassword = $("#user-old-password").val();
        var newPassword = $("#user-new-password").val();
        var confirmPassword = $("#user-confirm-password").val();
        $.ajax({
            type : 'post',
            url : '/updatePassword',
            data : 'oldPassword=' + oldPassword + '&newPassword=' + newPassword + '&confirmPassword=' + confirmPassword,
            success : function (data) {
                if (data.code == 200) {
                    $("#over").removeClass("step-2").addClass("step-1");
                    $("#form").empty();
                } else if (data.code == 400) {
                    location.href = '/toLogin';
                } else if (data.code == 401) {
                    var errors = data.data;
                    for (key in errors) {
                        var error = errors[key];
                        if (error.indexOf("新密码") != -1) {
                            $("#user-new-password").css("color", "red").val(error).prop("type", "text");
                        } else if (error.indexOf("重复") != -1 || error.indexOf("两次") != -1) {
                            $("#user-confirm-password").css("color", "red").val(error).prop("type", "text");
                        }
                    }
                } else if (data.code == 402) {
                    $("#user-old-password").css("color", "red").val("旧密码错误").prop("type", "text");
                    if (data.data != null) {
                        var errors = data.data;
                        for (key in errors) {
                            var error = errors[key];
                            if (error.indexOf("新密码") != -1) {
                                $("#user-new-password").css("color", "red").val(error).prop("type", "text");
                            } else if (error.indexOf("重复") != -1 || errors.indexOf("两次") != -1) {
                                $("#user-confirm-password").css("color", "red").val(error).prop("type", "text");
                            }
                        }
                    } else if (data.code == 500) {
                        console.log("发生未知错误");
                    }
                }
            },
            error : function (data) {
                console.log("请求出错:{" + data + "}");
            }
        })
    });
    $("#user-new-password").focus(reset);
    $("#user-old-password").focus(reset);
    $("#user-confirm-password").focus(reset);
});

function reset() {
    if($(this).prop("type") == 'text'){
        $(this).css("color", "black").prop("type", "password").val("");
    }
}