$(function () {
    $("#sendEmailCode").click(function () {
        var email = $("#user-email").val().trim();
        $.ajax({
            type : 'post',
            url : '/sendEmailCode',
            data : 'email=' + email,
            success : function (data) {
                var x = data.code;
                if(x == 200){
                    alert("邮件已发送，请到邮箱查收");
                }else if(x == 400){
                    location.href = '/toLogin'
                }else if(x == 401){
                    $("#user-email").css("color", "red").val(data.data).prop("name", "error");
                }else if(x == 402){
                    $("#user-email").css("color", "red").val("该邮箱已被注册").prop("name", "error");
                }else if(x == 500){
                    alert("邮件发生失败");
                }
            },
            error : function (data) {
                console.log("请求失败:{" + data + "}");
            }
        });
    });
    $("#user-email").focus(function () {
        if($(this).prop("name") == "error"){
            $(this).val("").css("color", "black").prop("name", "");
        }
    });
    $("#user-code").focus(function () {
        if($(this).prop("name") == "error"){
            $(this).val("").css("color", "black").prop("name", "");
        }
    });
    $("#save").click(function () {
        var email = $("#user-email").val().trim();
        var code = $("#user-code").val().trim();
        $.ajax({
            type : 'post',
            url : '/bindEmail',
            data : 'email=' + email + '&code=' + code,
            success : function (data) {
                var x = data.code;
                if(x == 200){
                    $("#form").empty();
                    $("#finish").removeClass("step-2").addClass("step-1")
                }else if(x == 400){
                    location.href = '/toLogin'
                }else if(x == 401){
                    $("#user-email").css("color", "red").val(data.data).prop("name", "error");
                }else if(x == 402){
                    $("#user-code").css("color", "red").val("验证码错误").prop("name", "error");
                }else if(x == 500){
                    console.log("未知错误：{" + data + "}");
                }
            },
            error : function (data) {
                console.log("请求失败:{" + data + "}");
            }
        });
    });
});