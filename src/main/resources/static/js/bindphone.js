$(function () {
   $("#sendOldPhoneCode").click(function () {
      $.ajax({
          type : 'post',
          url : '/sendNoPhone',
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
              console.log("未知错误:{" +data + "}");
          }
      });
   });
   $("#save").click(function () {
      var newPhone = $("#user-new-phone").val().trim();
      var code = $("#user-code").val().trim();
      var newCode = $("#user-new-code").val().trim();
      console.log(newPhone + code + newCode);
      var i = 0;
      if(code == ''){
          i++;
          $("#user-code").css("color", "red").val("请输入验证码").prop("name", "error");
      }
      if(newPhone == ''){
          i++;
          $("#user-new-phone").css("color", "red").val("请输入手机号").prop("name", "error");
      }
      if(newCode == ''){
          i++;
          $("#user-new-code").css("color", "red").val("请输入验证码").prop("name", "error");
      }
      if(i > 0){
          console.log("i>0")
          return;
      }
      if(checkPhone(newPhone)){
          $.ajax({
              type : 'post',
              url : '/bingingPhone',
              data : 'newPhone=' + newPhone + '&code=' + code + '&newCode=' + newCode,
              success : function (data) {
                  var code = data.code;
                  if(code  == 200){
                    $("#form").empty();
                    $("#finish").removeClass("step-2").addClass("step-1")
                  }else if(code == 400){
                    location.href = '/toLogin';
                  }else if(code == 401){
                      var errors = data.data;
                      for(key in errors){
                          var error = errors[key];
                          if(error.indexOf("a") != -1){
                              $("#user-code").val(error.substr(1, error.length)).css("color", "red").prop("name", "error");
                          }else if(error.indexOf("b") != -1){
                              $("#user-new-code").val(error.substr(1, error.length)).css("color", "red").prop("name", "error");
                          }else if(error.indexOf("手机") != -1){
                              $("#user-new-phone").val(error).css("color", "red").prop("name", "error");
                          }
                      }
                  }else if(code == 500){
                      console.log("未知错误:{" +data + "}");
                  }
              },
              error : function (data) {
                  console.log("未知错误:{" +data + "}");
              }
          });
      }else {
          $("#user-new-phone").css("color", "red").val("手机号格式不正确").prop("name", "error");
      }
   });
    $("#sendNewPhoneCode").click(function () {
        var newPhone = $("#user-new-phone").val().trim();
        if(checkPhone(newPhone)){
            $.ajax({
                type : 'post',
                url : '/sendByPhone',
                data : 'newPhone=' + newPhone,
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
                    }else if(data.code == 233){
                        $("#user-new-phone").css("color", "red").val(data.description).prop("name", "error");
                    }
                },
                error : function (data) {
                    console.log("未知错误:{" +data + "}");
                }
            });
        }else {
            $("#user-new-phone").css("color", "red").val("手机号格式不正确").prop("name", "error");
        }
    });
    $("#user-new-phone").focus(reset);
    $("#user-code").focus(reset);
    $("#user-new-code").focus(reset);
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
function reset() {
    if($(this).prop("name") == 'error'){
        $(this).val("").css("color", "black").prop("name", "");
    }
}