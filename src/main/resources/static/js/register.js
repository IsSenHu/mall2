$(function () {
    /**
     * 同意协议
     * */
    $("#reader-me").click(function () {
        if($(this).is(":checked")){
            $("#register").prop("disabled", false);
        }else {
            $("#register").prop("disabled", true);
        }
    });

    /**
     * 同意协议
     * */
    $("#reader-me-phone").click(function () {
        if($(this).is(":checked")){
            $("#registerPhone").prop("disabled", false);
        }else {
            $("#registerPhone").prop("disabled", true);
        }
    });

    /**
     * 邮箱用户注册
     * */
    $("#register").click(function () {
        var email = $("#email").val();
        var password = $("#password").val();
        var password2 = $("#passwordRepeat").val();
        var data = dataEmail(email, password, password2);
        ajaxMethod('/registerForEmail', data)
    });
    /**
     * 手机用户注册
     * */
    $("#registerPhone").click(function () {
       var phone = $("#phone").val();
       var password = $("#passwordPhone").val();
       var password2 = $("#passwordRepeatPhone").val();
       var code = $("#code").val();
       if(checkPhone(phone)){
           $.ajax({
               type : 'post',
               url : '/registerForPhone',
               data : 'phone=' + phone + '&code=' + code + '&password=' + password + '&password2=' + password2,
               success : function (data) {
                   if(data.code == 200){
                       alert("注册成功");
                       location.href = '/home';
                   }else if(data.code == 400){
                       alert(data.description);
                   }else if(data.code == 401){
                       for(key in data.data){
                           var error = data.data[key];
                           if(error.indexOf("手机") != -1){
                               $("#phone").css("color", "red").val(error).prop("name", "error");
                           }else if(error.indexOf("重复密码") != -1){
                               $("#passwordRepeatPhone").css("color", "red").val(error).prop("type", "text");
                           }else if(error.indexOf("两次密码") != -1){
                               $("#passwordRepeatPhone").css("color", "red").val(error).prop("type", "text");
                           }else if(error.indexOf("密码") != -1){
                               $("#passwordPhone").css("color", "red").val(error).prop("type", "text");
                           }else if(error.indexOf("注册失败") != -1){
                               alert(error);
                           }
                       }
                   }else if(data.code == 402){
                       alert(data.description);
                   }else if(data.code == 500){
                       alert(data.description);
                   }
               },
               error : function (data) {
                   error(data);
               }
           });
       }else {
           $("#phone").css("color", "red").val("手机号格式不正确").prop("name", "error");
       }
    });
    /**
     * 绑定输入框获得焦点的事件
     * */
    $("#email").focus(resetEmailForm);
    $("#password").focus(resetEmailForm);
    $("#passwordRepeat").focus(resetEmailForm);
    $("#phone").focus(resetEmailForm);
    $("#passwordPhone").focus(resetEmailForm);
    $("#passwordRepeatPhone").focus(resetEmailForm);
});

function dataEmail(email, password, password2) {
    var data = "email=" + email + "&password=" + password + "&password2=" + password2;
    return data;
}
/**
 * 注册请求发送成功的结果处理函数
 * 1，注册成功后自动登录，并跳转到首页
 * */
function successEmail(data) {
    if(data.code == 400){
        for(key in data.data){
            var error = data.data[key];
            if(error.indexOf("邮箱") != -1){
                $("#email").css("color", "red").val(error).prop("name", "error");
            }else if(error.indexOf("重复密码") != -1){
                $("#passwordRepeat").css("color", "red").val(error).prop("type", "text");
            }else if(error.indexOf("两次密码") != -1){
                $("#passwordRepeat").css("color", "red").val(error).prop("type", "text");
            }else if(error.indexOf("密码") != -1){
                $("#password").css("color", "red").val(error).prop("type", "text");
            }else if(error.indexOf("已被注册") != -1){
                $("#email").css("color", "red").val(error).prop("name", "error");
            }else if(error.indexOf("注册失败") != -1){
                alert(error);
            }
        }
    }else if(data.code == 200){
        alert(data.description);
        //跳转到首页（如果可以，跳转到之前的页面）
        location.href = '/home';
    }
}
/**
 * ajax请求发生错误的处理函数
 * */
function error(data) {
    alert("未知错误:{" + data + "}");
}
/**
 * 发送ajax请求的函数
 * */
function ajaxMethod(url, params) {
    $.ajax({
        type : 'post',
        url : url,
        data : params,
        success : function (data) {
            successEmail(data);
        },
        error : function(data) {
            error(data);
        }
    });
}
/**
 * 重置email注册表单的函数
 * */
function resetEmailForm() {
    if($(this).attr("id") == 'email' || $(this).attr("id") == 'phone'){
        if($(this).prop("name") == 'error'){
            $(this).css("color", "black").val("").prop("name", "");
        }
    }else if($(this).attr("id") == 'passwordPhone' || $(this).attr("id") == 'passwordRepeatPhone'){
        if($(this).prop("type") == 'text'){
            $(this).css("color", "black").val("").prop("type", "password");
        }
    }
}
/**
 * 检查手机号的方法
 * */
function checkPhone(phone) {
    //手机号正则
    var phoneReg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if(!phoneReg.test(phone)){
        return false;
    }else {
        return true;
    }
}
/**
 * 发送短信
 * */
function sendMobileCode() {
    var phone = $("#phone").val();
    if(checkPhone(phone)){
        $.ajax({
            type : 'post',
            url : '/sendSMS',
            data : 'phone=' + phone,
            success : function (data) {
                if(data.code == 401){
                    $("#phone").css("color", "red").val("该手机号已被注册").prop("name", "error");
                }else if(data.code == 400){
                    alert("短信发送失败");
                }else if(data.code == 402){
                    alert("短信发送失败");
                }else if(data.code == 200){
                    alert("短信发送成功");
                }
            },
            error : function(data) {
                error(data);
            }
        });
    }else {
        $("#phone").css("color", "red").val("手机号格式不正确").prop("name", "error");
    }
}