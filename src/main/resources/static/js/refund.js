$(function () {
   $("#save").click(function () {
       var orderId = $("#orderId").val();
       var itemId = $("#itemId").val();
       var refundType = $("#refund-type").val();
       var refundReason = $("#refund-reason").val();
       var refundMoney = $("#refund-money").val();
       var description = $("#description").val();
       var data = 'orderId=' + orderId + '&itemId=' + itemId + '&refundType=' + refundType + '&refundReason=' + refundReason + '&refundMoney=' + refundMoney + '&description=' + description;
       $.ajax({
           type : 'post',
           url : '/submitRefund',
           data : data,
           success : function (data) {
               var code = data.code;
               if(code == 200){
                   location.reload();
               }else if(code == 400){
                   location.href = '/toLogin';
               }else if(code == 401){

               }else if(code == 500){
                   console.log("未知错误:{" + data + "}");
               }
           },
           error : function (data) {
             console.log("未知错误:{" + data + "}");
           }
       });
   });
   
   $("#cancel").click(function () {
       var refundId = $("#refundId").val();
       $.ajax({
           type : 'post',
           url : '/cancelRefund',
           data : 'refundId=' + refundId,
           success : function (data) {
               var code = data.code;
               if(code == 200){
                   var orderId = $("#orderId").val();
                   var itemId = $("#itemId").val();
                   location.href = '/cancelSuccess?orderId=' + encode64(orderId) + '&itemId=' + encode64(itemId);
               }else if(code == 400){
                   console.log("该订单已经不能删除")
               }else if(code == 401){
                   console.log("无效的id");
               }else if(code == 500){
                   console.log("未知错误:{" + data + "}");
               }
           },
           error : function (data) {
               console.log("未知错误:{" + data + "}");
           }
       });
   });
});

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