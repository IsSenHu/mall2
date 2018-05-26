$(function () {
   $("#coupons").click(function () {
       alert("coupons");
   });
   $("#collectMobile").click(collect);
   $("#collect").click(collect);
   $("#collectedMobile").click(cancelCollect);
   $("#collected").click(cancelCollect);
   $("#LikBasket").click(addGoodToShopCart);
   $("#LikBuy").click(quickPay);
});

function coupons() {
    var goodId = $("#goodId").val();
    $.ajax({
        type : 'post',
        url : '/getCoupons',
        data : 'goodId=' + goodId,
        success : function (data) {
            if(data.code == 200){
                alert("领取成功");
            }else if(data.code == 400){
                location.href = '/toLogin';
            }else if(data.code == 401){
                alert("你已经领取过了");
            }else if(data.code == 500){
                console.log("未知错误:{" + data + "}");
            }
        },
         error : function (data) {
             console.log("未知错误:{" + data + "}");
         }
    });
}
/**
 * 跳转到商品介绍页面
 * */
function introduct(goodId) {
    location.href = '/introduction?goodId=' + goodId;
}
/**
 * 收藏商品
 * */
function collect() {
    if($(this).prop("title") == "collected"){
        cancelCollect();
    }
    var goodId = $("#goodId").val();
    $.ajax({
        type : 'post',
        url : '/collect',
        data : 'goodId=' + goodId,
        success : function (data) {
            if(data.code == 200){
                $("#ifCollect").empty();
                $("#ifCollect").append('<a><span style="display: block; width: 70px; margin-left: 10px;" onclick="cancelCollect()">取消收藏</span></a>');
                $("#collectMobile").html("取消收藏").prop("title", "collected");
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
/**
 * 取消收藏
 * */
function cancelCollect() {
    if($(this).prop("title") != "collected"){
        collect();
    }
    var goodId = $("#goodId").val();
    $.ajax({
        type : 'post',
        url : '/cancelCollect',
        data : 'goodId=' + goodId,
        success : function (data) {
            if(data.code == 200){
                $("#ifCollect").empty();
                $("#ifCollect").append('<a><span class="am-icon-heart am-icon-fw" style="display: block; width: 50px; margin-left: 10px;" onclick="collect()">收藏</span></a>');
                $("#collectMobile").html("收藏").prop("title", "");
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
/**
 * 生成条目
 * */
function addGoodToShopCart() {
    var goodId = $("#goodId").val();
    var number = $("#text_box").val();
    $.ajax({
        type : 'post',
        url : '/addGoodToShopCart',
        data : 'goodId=' + goodId + '&number=' + number,
        success : function (data) {
            if(data.code == 200){
                alert("已加入购物车");
            }else if(data.code == 400){
                location.href = '/toLogin';
            }else if(data.code == 500){
                console.log("未知错误:{" + data +"}");
            }
        },
        error : function (data) {
            console.log("未知错误:{" + data +"}");
        }
    });
}
function quickPay () {
    var goodId = $("#goodId").val();
    var number = $("#text_box").val();
    location.href = '/quickPay?goodId=' + goodId + '&number=' + number;
}