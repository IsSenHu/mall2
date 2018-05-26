$(function () {
    /**
     * 保存银行卡信息
     * */
   $("#save").click(function () {
      var realName = $("#user-name").val().trim();
      var cardId = $("#user-IDcard").val().trim();
      var bankCardId = $("#user-BankID").val().trim();
      var phone = $("#user-phone").val().trim();
      var code = $("#user-new-code").val().trim();
      if(checkPhone(phone)){
          var i = 0;
          if(realName == ''){
              i++;
              $("#user-name").css("color", "red").val("真实姓名不能为空").prop("name", "error");
          }
          if(cardId == ''){
              i++;
              $("#user-IDcard").css("color", "red").val("身份证号不能为空").prop("name", "error");
          }
          if(bankCardId == ''){
              i++;
              $("#user-BankID").css("color", "red").val("银行卡号不能为空").prop("name", "error");
          }
          if(code == ''){
              i++;
              $("#user-new-code").css("color", "red").val("验证码不能为空").prop("name", "error");
          }
          if(i > 0){
              return;
          }
          $.ajax({
              type : 'post',
              url : '/addBankCard',
              data : 'realName=' + realName + '&cardId=' + cardId + '&bankCardId=' + bankCardId + '&phone=' + phone + '&code=' + code,
              success : function (data) {
                  var code = data.code;
                  if(code == 200){
                      location.href = '/cardMethodFinish'
                  }else if(code == 400){
                      location.href = '/toLogin';
                  }else if(code == 401){
                      var errors = data.data;
                      for(index in errors){
                          var error = errors[index];
                          if(error.indexOf("姓名") != -1){
                              $("#user-name").css("color", "red").val(error).prop("name", "error");
                          }else if(error.indexOf("身份") != -1){
                              $("#user-IDcard").css("color", "red").val(error).prop("name", "error");
                          }else if(error.indexOf("银行") != -1){
                              $("#user-BankID").css("color", "red").val(error).prop("name", "error");
                          }else {
                              $("#user-new-code").css("color", "red").val(error).prop("name", "error");
                          }
                      }
                  }else if(code == 402){
                      $("#user-new-code").css("color", "red").val("验证码已过期").prop("name", "error");
                  }else if(code == 403){
                      $("#user-new-code").css("color", "red").val("验证码错误").prop("name", "error");
                  }else if(code == 404){
                      $("#user-BankID").css("color", "red").val("该银行卡号已存在").prop("name", "error");
                  }else if(code == 500){
                      console.log("未知错误:{" + data + "}");
                  }
              },
              error : function (data) {
                  console.log("未知错误:{" + data + "}");
              }
          });
      }else {
          $("#user-phone").css("color", "red").val("手机号格式不正确").prop("name", "error");
          if(realName == ''){
              i++;
              $("#user-name").css("color", "red").val("真实姓名不能为空").prop("name", "error");
          }
          if(cardId == ''){
              i++;
              $("#user-IDcard").css("color", "red").val("身份证号不能为空").prop("name", "error");
          }
          if(bankCardId == ''){
              i++;
              $("#user-BankID").css("color", "red").val("银行卡号不能为空").prop("name", "error");
          }
          if(code == ''){
              i++;
              $("#user-new-code").css("color", "red").val("验证码不能为空").prop("name", "error");
          }
      }
   });
    /**
     * 获取短信验证码
     * */
    $("#sendSms").click(function () {
        var phone = $("#user-phone").val().trim();
        if(checkPhone(phone)){
            $.ajax({
                type : 'post',
                url : '/sendBankSmsCode',
                data : 'phone=' + phone,
                success : function (data) {
                    if(data.code == 200){
                        alert("短信验证码已发送，请注意接收");
                    }else if(data.code == 400){
                        location.href = '/toLogin';
                    }else if(data.code == 401){
                        alert("操作频繁，一分钟内只能发送一次短信");
                    } else if(data.code == 500){
                        console.log("未知错误:{" + data + "}");
                    }
                },
                error : function (data) {
                    console.log("未知错误:{" + data + "}");
                }
            });
        }else {
            $("#user-phone").css("color", "red").val("手机号格式不正确").prop("name", "error");
        }
    });
    /**
     * 重置输入框
     * */
    $("#user-phone").focus(function () {
        if($(this).prop("name") == "error"){
            $(this).val("").css("color", "black").prop("name", "");
        }
    })
    $("#user-name").focus(function () {
        if($(this).prop("name") == "error"){
            $(this).val("").css("color", "black").prop("name", "");
        }
    })
    $("#user-IDcard").focus(function () {
        if($(this).prop("name") == "error"){
            $(this).val("").css("color", "black").prop("name", "");
        }
    })
    $("#user-BankID").focus(function () {
        if($(this).prop("name") == "error"){
            $(this).val("").css("color", "black").prop("name", "");
        }
    })
    $("#user-new-code").focus(function () {
        if($(this).prop("name") == "error"){
            $(this).val("").css("color", "black").prop("name", "");
        }
    })
});

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