$(function () {
    $(".refund").click(function () {
        var orderId = $(this).prop("title");
        var itemId = $(this).attr("alt");
        console.log(orderId);
        console.log(itemId);
        location.href = '/refund?orderId=' + encode64(orderId) + '&itemId=' + encode64(itemId);
    });
    
    $("#received").click(received);
    
});

function received() {
    var orderId = $(this).prop('title');
    $.ajax({
        type : 'post',
        url : '/deliveredOrder',
        data : 'orderId=' + orderId,
        success : function (data) {
            if(data.code == 200){
                $("#qrsh").removeClass('step-3');
                $("#qrsh").addClass('step-1');
                $(".Mystatus").html("确认收货");
                $("#received").html("评价商品");
            }
        }
    });
}

function orderInfo(orderId) {
    location.href = '/orderInfo/' + encode64(orderId);
}
function logistics(orderId) {
    location.href = '/logistics/' + encode64(orderId);
}
function cancelOrder(obj) {
    var $this = $(obj);
    var orderId = $this.prop("title");
    $.ajax({
        type : 'post',
        url : '/cancelOrder',
        data : 'orderId=' + orderId,
        success : function (data) {
            var code = data.code;
            if(code == 200){
                var $parent = $this.closest(".item-status");
                $parent.empty().append('<p class="order-info"><a>订单已取消</a></p>');
            }else if(code == 400){
                console.log("该订单已经不能取消了");
            }
        },
        error : function (data) {
            console.log("未知错误:{" + data + "}");
        }
    });
}

function deleteOrder(orderId) {
    $.ajax({
        type : 'post',
        url : '/deleteOrder',
        data : 'orderId=' + orderId,
        success : function (data) {
            if(data.code == 200){
                location.reload();
            }else {
                console.log("forbidden:{" + data.description + "}");
            }
        },
        error : function (data) {
            console.log("error:{" + data + "}");
        }
    });
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