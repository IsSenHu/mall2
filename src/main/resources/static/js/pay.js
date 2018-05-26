$(function () {
   $(".user-addresslist").click(function () {
      var addressId = $(this).prop("title");
      $.ajax({
          type : 'post',
          url : '/findAddressById',
          data : 'addressId=' + addressId,
          success : function (data) {
              if(data.code == 200){
                  var address = data.data;
                  $("#province").html(address.province);
                  $("#city").html(address.city);
                  $("#dist").html(address.area);
                  $("#street").html(address.address);
                  $("#buy-user").html(address.receivePersonName);
                  $("#buy-phone").html(address.phone);
                  $("#addressId").val(addressId);
              }else if(data.code == 500){
                  console.log("未知错误:{" + data + "}");
              }
          },
          error : function (data) {
              console.log("未知错误:{" + data + "}");
          }
      });
   });

   $("#qq").click(function () {
      $(this).prop("title", "qq");
      $("#card").prop("title", "");
      $("#taobao").prop("title", "");
   });

    $("#card").click(function () {
        $(this).prop("title", "qq");
        $("#qq").prop("title", "");
        $("#taobao").prop("title", "");
    });

    $("#taobao").click(function () {
        $(this).prop("title", "qq");
        $("#qq").prop("title", "");
        $("#card").prop("title", "");
    });

    $('#J_Go').click(function() {
        $("#pay").empty();
        var qq = $("#qq").prop("title");
        var card = $("#card").prop("title");
        var taobao = $("#taobao").prop("title");
        if(qq == '' && card == '' && taobao == ''){
            $("#pay").append('<h3 style="color: red;">请选择支付方式</h3>');
            return;
        }
//					禁止遮罩层下面的内容滚动
        $(document.body).css("overflow","hidden");

        $(this).addClass("selected");
        $(this).parent().addClass("selected");

        $("#form").empty();
        $("#operation").html("立即支付");
        $("#information").html("pay now");
        if(qq != ''){
            $("#form").append('<b style="font-size: 26px; color: green;">微信支付</b><br>').append('<img style="width: 50%;" src="../weixin.JPG"/>');
            $("#form").append('<br><br><div class="am-btn am-btn-danger" onclick="closeForm()">取消</div>');
        }else if(taobao != ''){
            $("#form").append('<b style="font-size: 26px; color: green;">支付宝</b><br>').append('<img style="width: 50%;" src="../zhifubao.JPG"/>');
            $("#form").append('<br><br><div class="am-btn am-btn-danger" onclick="closeForm()">取消</div>');
        }else {
            $("#form").append('<br><div style="text-align: center;">' +
                '<b style="font-size: 26px; color: green;">快捷支付</b><br>' +
                '<b style="font-size: 16px;">选择银行卡</b><br><br>' +
                '<input type="radio" name="bank" checked value="工商银行"/>&nbsp;工商银行&nbsp;' +
                '<input type="radio" name="bank" value="建设银行"/>&nbsp;建设银行&nbsp;' +
                '<input type="radio" name="bank" value="农业银行"/>&nbsp;农业银行&nbsp;' +
                '<input type="radio" name="bank" value="招商银行"/>&nbsp;招商银行&nbsp;' +
                '<br><br><b style="font-size: 16px;">输入支付密码</b><br><br>' +
                '<input type="password" onfocus="reset()" id="payPassword" name="" style="width: 30%;"/>' +
                '</div>');
            $("#form").append('<br><br><div class="am-btn am-btn-danger" onclick="createOrder(1)">支付</div>&nbsp;<div class="am-btn am-btn-danger" onclick="createOrder(0)">暂不支付</div>&nbsp;<div class="am-btn am-btn-danger" onclick="closeForm()">取消</div>');
        }
        $('.theme-popover-mask').show();
        $('.theme-popover-mask').height($(window).height());
        $("#pay_now").slideDown(200);
    });
});

function closeForm() {
    $(document.body).css("overflow","visible");
    $('.theme-popover-mask').hide();
    $("#pay_now").slideUp(200);
}
/**
 * 生成订单
 * */
function createOrder(statu) {
    var addressId = $("#addressId").val();
    console.log(addressId);
    var itemIdStr = $("#itemIdStr").val();
    var totalMoney = $("#totalMoney").val();
    var bankName = '';
    $("input[name='bank']").each(function () {
        if($(this).prop("checked")){
            bankName = $(this).val();
        }
    });
    console.log(bankName);
    var userNote = '';
    $("input[name='user-note']").each(function () {
        userNote = $(this).prop("title") + '_' + $(this).val().trim() + ",";
    });
    if(userNote.length > 0){
        userNote = userNote.substr(0, userNote.length - 1);
    }
    var password = $("#payPassword").val().trim();
    var data = 'statu=' + statu + '&addressId=' + addressId + '&itemIdStr=' + itemIdStr + '&totalMoney=' + totalMoney + '&userNote=' + userNote + '&password=' + password + '&bankName=' + bankName;
    $.ajax({
        type : 'post',
        url : '/createOrder',
        data : data,
        success : function (data) {
            var code = data.code;
            if(code == 200){
                //支付成功
                var statu = data.data;
                if(statu == 1){
                    location.href = '/success?totalMoney=' + encode64(totalMoney) + '&addressId=' + encode64(addressId);
                }else {
                    //跳转到订单的页面
                    location.href = '/order'
                }
            }else if(code == 400){
                location.href = '/toLogin';
            }else if(code == 401){
                closeForm();
                console.log("别搞事");
            }else if(code == 301){
                //没有设置支付密码，跳转到设置支付密码页面
                location.href = '/setpay';
            }else if(code == 300){
                //支付密码错误
                $("#payPassword").val("支付密码错误").css("color", "red").prop("name", "error").prop("type", "text");
            }else if(code == 500){
                closeForm();
                console.log("未知错误:{" + data.message + "}");
            }
        },
        error : function (data) {
             console.log("未知错误:{" + data.message + "}");
        }
    });
}
function reset() {
    if($(this).prop("name") == "error"){
        $(this).css("color", "black").val("").prop("name", "");
    }
}

// base64加密开始
var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
    + "wxyz0123456789+/" + "=";
function encode64(input) {

    var output = "";
    var chr1, chr2, chr3 = "";
    var enc1, enc2, enc3, enc4 = "";
    var i = 0;
    do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);
        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;
        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
            + keyStr.charAt(enc3) + keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
    } while (i < input.length);
    return output;
}