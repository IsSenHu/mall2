$(function () {
    /**
     * 发送短信
     * */
    $("#sendCode").click(function () {
        $.ajax({
            type : 'post',
            url : '/setpaySendSms',
            success : function (data) {
                if(data.code == 200){
                    alert("短信发送成功")
                }else if(data.code == 401){
                    location.href = '/toLogin';
                }else if(data.code == 500){
                    console.log("未知错误");
                }else if(data.code == 402){
                    alert("短信发送失败");
                }else if(data.code == 403){
                    alert(data.description);
                }else if(data.code == 400){
                    alert(data.description);
                }
            },
            error : function (data) {
                alert("短信发送失败");
            }
        });
    });

    $("#save").click(function () {
        var newPassword = $("#user-password").val().trim();
        var confirmPassword = $("#user-confirm-password").val().trim();
        var code = $("#code").val().trim();

        if(newPassword.length != 6){
            $("#user-password").css("color", "red").val("密码长度必须位6位").prop("type", "text");
            return;
        }
        if(confirmPassword != newPassword){
            $("#user-confirm-password").css("color", "red").val("两次密码输入不相同").prop("type", "text");
            return;
        }
        $.ajax({
            type : 'post',
            url : '/resetpay',
            data : 'code=' + code + '&newPassword=' + newPassword + '&confirmPassword=' + confirmPassword,
            success : function (data) {
                if(data.code == 200){
                    $("#form").empty();
                    $("#finish").removeClass("step-2").addClass("step-1");
                }else if(data.code == 400){
                    location.href = '/toLogin';
                }else if(data.code == 401){
                    $("#code").css("color", "red").val(data.description).prop("name", "error");
                }else if(data.code == 402){
                    $("#code").css("color", "red").val(data.description).prop("name", "error");
                }else if(data.code == 403){
                    console.log("输入错误");
                }else if(data.code == 500){
                    console.log("未知错误");
                }
            },
            error : function (data) {
                console.log("未知错误:{" + data + "}");
            }
        });

    });
    $("#user-password").focus(reset);
    $("#user-confirm-password").focus(reset);
    $("#code").focus(function () {
       if($(this).prop("name") == 'error'){
           $(this).css("color", "black").prop("name", "").val("");
       }
    });
});

function reset() {
    if($(this).prop("type") == 'text'){
        $(this).css("color", "black").prop("type", "password").val("");
    }
}