$(function () {
    $("#user-name").focus(reset);
    $("#user-phone").focus(reset);
    $("#user-intro").focus(reset);
   $("select[name='provinceid']").change(function () {
      var provinceid = $(this).val();
      $.ajax({
          type : 'post',
          url : '/changeProvince',
          data : 'provinceid=' + provinceid,
          success : function (data) {
              if(data.code == 200){
                  $("select[name='cityid']").empty();
                  $("select[name='areaid']").empty();
                  var cities = data.data.cities;
                  var areas = data.data.areas;
                  for(key in cities){
                      var city = cities[key];
                      var option = '<option value="' + city.cityid + '">' + city.city + '</option>';
                      $("select[name='cityid']").append(option);
                  }
                  for(index in areas){
                      var area = areas[index];
                      var option = '<option value="' + area.areaid + '">' + area.area + '</option>';
                      $("select[name='areaid']").append(option);
                  }
              }else if(data.code == 500){
                  console.log("未知错误:{" + data + "}");
              }
          },
          error : function (data) {
              console.log("请求失败{" + data + "}");
          }
      });
   });
   
   $("select[name='cityid']").change(function () {
      var cityid = $(this).val();
      $.ajax({
          type : 'post',
          url : '/changeCity',
          data : 'cityid=' + cityid,
          success : function (data) {
              if(data.code == 200){
                  $("select[name='areaid']").empty();
                  var areas = data.data;
                  for(index in areas){
                      var area = areas[index];
                      var option = '<option value="' + area.areaid + '">' + area.area + '</option>';
                      $("select[name='areaid']").append(option);
                  }
              }else if(data.code == 500){
                  console.log("未知错误:{" + data + "}");
              }
          },
          error : function (data) {
              console.log("请求失败{" + data + "}");
          }
      });
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
   /**
    * 保存
    * */
   $("#save").click(function () {
       var receivePersonName = $("#user-name").val().trim();
       var phone = $("#user-phone").val().trim();
       var detailAddress = $("#user-intro").val().trim();
       var provinceid = $("select[name='provinceid']").val().trim();
       var cityid = $("select[name='cityid']").val().trim();
       var areaid = $("select[name='areaid']").val().trim();
       var i = 0;
       if(receivePersonName == ''){
           i++;
           $("#user-name").css("color", "red").val("收货人不能为空").prop("name", "error");
       }
       if(!checkPhone(phone)){
           i++;
           $("#user-phone").css("color", "red").val("请输入正确的手机号").prop("name", "error");
       }
       if(detailAddress == ''){
           i++;
           $("#user-intro").css("color", "red").val("请输入详细的地址").prop("name", "error");
       }
       if(i > 0){
           return;
       }
       $.ajax({
           type : 'post',
           url : '/addAddress',
           data : 'receivePersonName=' + receivePersonName + '&phone=' + phone + '&detailAddress=' + detailAddress + '&provinceid=' + provinceid + '&cityid=' + cityid + '&areaid=' + areaid,
           success : function (data) {
               if(data.code == 200){
                   location.reload();
               }else if(data.code == 400){
                   location.href = '/toLogin';
               }else if(data.code == 500){
                   console.log("未知错误:{" + data + "}");
               }
           },
           error : function (data) {
               console.log("请求失败{" + data + "}");
           }
       });
   });
});
/**
 * 设为默认地址
 * */
function setDefault(addressId) {
    $.ajax({
        type : 'post',
        url : '/setDefault',
        data : 'addressId=' + addressId,
        success : function (data) {
            if(data.code == 200){
                console.log("设置成功");
            }else if(data.code == 400){
                location.href = '/toLogin';
            }else if(data.code == 500){
                console.log("未知错误:{" + data + "}");
            }
        },
        error : function (data) {
            console.log("请求失败{" + data + "}");
        }
    });
}
/**
 * 删除收货地址
 * */
function deleteAddress(addressId) {
    $.ajax({
        type : 'post',
        url : '/deleteAddress',
        data : 'addressId=' + addressId,
        success : function (data) {
            if(data.code == 200){
                location.reload();
            }else if(data.code == 400){
                location.href = '/toLogin';
            }else if(data.code == 500){
                console.log("未知错误:{" + data + "}");
            }
        },
        error : function (data) {
            console.log("请求失败{" + data + "}");
        }
    });
}