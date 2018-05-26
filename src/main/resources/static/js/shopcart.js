$(function () {
    /**
     * 删除条目
     * */
    $(".delete").click(function () {
        var itemId = $(this).prop("title");
        $.ajax({
            type : 'post',
            url : '/deleteItem',
            data : 'itemId=' + itemId,
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
                console.log("未知错误:{" + data + "}");
            }
        });
    });

    $(".add").click(function () {
        var goodId = $(this).prop("title");
        var unitPrice = $("#" + goodId + 'unitPrice');
        var totalMoney = $("#" + goodId + 'totalMoney');
        $.ajax({
            type : 'post',
            url : '/changeNumber',
            data : 'goodId=' + goodId + '&number=' + 1,
            success : function (data) {
                if(data.code == 200){
                    unitPrice.html(data.data.unitPrice);
                    totalMoney.html(data.data.totalMoney);
                    checked();
                }else if(data.code == 400){
                    location.href = '/toLogin';
                }else if(data.code == 500){
                    console.log("未知错误:{" + data + "}");
                }
            },
            error : function (data) {
                console.log("未知错误:{" + data + "}");
            }
        });
    });

    $(".min").click(function () {
        var goodId = $(this).prop("title");
        var unitPrice = $("#" + goodId + 'unitPrice');
        var totalMoney = $("#" + goodId + 'totalMoney');
        $.ajax({
            type : 'post',
            url : '/changeNumber',
            data : 'goodId=' + goodId + '&number=' + '-1',
            success : function (data) {
                if(data.code == 200){
                    unitPrice.html(data.data.unitPrice);
                    totalMoney.html(data.data.totalMoney);
                    checked();
                }else if(data.code == 400){
                    location.href = '/toLogin';
                }else if(data.code == 500){
                    console.log("未知错误:{" + data + "}");
                }
            },
            error : function (data) {
                console.log("未知错误:{" + data + "}");
            }
        });
    });

    $(".text_box").on("keyup", function (event) {
        var key = event.which;
        if(key == 13){
            var goodId = $(this).prop("title");
            var value = $(this).val();
            if(value < 0){
                return;
            }
            var unitPrice = $("#" + goodId + 'unitPrice');
            var totalMoney = $("#" + goodId + 'totalMoney');
            $.ajax({
                type : 'post',
                url : '/changeNumber',
                data : 'goodId=' + goodId + '&number=' + value + 'a',
                success : function (data) {
                    if(data.code == 200){
                        unitPrice.html(data.data.unitPrice);
                        totalMoney.html(data.data.totalMoney);
                        checked();
                    }else if(data.code == 400){
                        location.href = '/toLogin';
                    }else if(data.code == 500){
                        console.log("未知错误:{" + data + "}");
                    }
                },
                error : function (data) {
                    console.log("未知错误:{" + data + "}");
                }
            });
        }
    });
    
    $(".text_box").blur(function () {
        var goodId = $(this).prop("title");
        var value = $(this).val();
        if(value < 0){
            return;
        }
        var unitPrice = $("#" + goodId + 'unitPrice');
        var totalMoney = $("#" + goodId + 'totalMoney');
        console.log(unitPrice);
        $.ajax({
            type : 'post',
            url : '/changeNumber',
            data : 'goodId=' + goodId + '&number=' + value + 'a',
            success : function (data) {
                if(data.code == 200){
                    unitPrice.html(data.data.unitPrice);
                    totalMoney.html(data.data.totalMoney);
                    checked();
                }else if(data.code == 400){
                    location.href = '/toLogin';
                }else if(data.code == 500){
                    console.log("未知错误:{" + data + "}");
                }
            },
            error : function (data) {
                console.log("未知错误:{" + data + "}");
            }
        });
    });

    $(".check-all").click(function () {
        if($(this).prop("checked") == true){
            $(".check").prop("checked", true);
            var total = 0;
            $(".J_ItemSum").each(function () {
                total += parseFloat(this.innerHTML);
            });
            if(total.toString().indexOf(".") == -1){
                $("#J_Total").html(total + '.00');
            }else if((total.toString().length - (total.toString().indexOf(".") + 1) == 1)) {
                $("#J_Total").html(total + '0');
            }else {
                $("#J_Total").html(total);
            }
        }else {
            $(".check").prop("checked", false);
            $("#J_Total").html('0.00');
        }
    });

    $("input[name='items']").each(function () {
        $(this).click(checked);
    });

    function checked() {
        var goodIds = '';
        var i = 0;
        $("input[name='items']").each(function () {
            i ++;
            if($(this).prop("checked")){
                goodIds = goodIds + $(this).val() + ',';
            }
        });
        if(goodIds.length >= 2){
            var goodIdsStr = goodIds.substr(0, goodIds.length - 1);
            var goodIdArr = goodIdsStr.split(",");
            var total = 0;
            for (key in goodIdArr){
                total += parseFloat($("#" + goodIdArr[key] + 'totalMoney').html());
                if(i == parseInt(key) + 1){
                    $(".check-all").prop("checked", true);
                    i = 0;
                }else {
                    $(".check-all").prop("checked", false);
                }
            }
            if(total.toString().indexOf(".") == -1){
                $("#J_Total").html(total + '.00');
            }else if((total.toString().length - (total.toString().indexOf(".") + 1) == 1)) {
                $("#J_Total").html(total + '0');
            }else {
                $("#J_Total").html(total);
            }
        }else {
            $(".check-all").prop("checked", false);
            $("#J_Total").html('0.00');
        }
    }
    
    $("#J_Go").click(function () {
        $("#msg").empty();
        var itemIdStr = '';
        $("input[name='items']").each(function () {
            if($(this).prop("checked")){
                itemIdStr = itemIdStr + $(this).prop("title") + ',';
            }
        });
        if(itemIdStr.length > 0){
            itemIdStr = itemIdStr.substr(0, itemIdStr.length - 1);
            location.href = '/pay?itemIdStr=' + encode64(itemIdStr);
        }else {
            $("#msg").append('<span style="color: red;">请至少选择一个商品</span>');
        }
    })
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