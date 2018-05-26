$(function () {
    $(".click_brand").click(search_by_brand);
    $(".click_applyer").click(search_by_applyer);
    $(".click_material").click(search_by_material);
    $("#allBrand").click(search_no_by_brand);
    $("#allApplyer").click(search_no_by_applyer);
    $("#allMaterial").click(search_no_by_material);
    $("#ai-topsearch").click(Search_by_goodName);
    $("#COMPREHENSIVE").click(COMPREHENSIVE);
    $("#PRICE").click(PRICE);
    $("#SALE").click(SALE);
    $("#REVIEW").click(REVIEW);
    /**
     * 加载页面后显示分页
     * */
    var number = $("#number").val();
    var totalPage = $("#totalPage").val();
    var rowsTotal = $("#rowsTotal").val();
    if(rowsTotal >= 12){
        $("#page").empty();
        if(totalPage <= 3){
            if(number == 1){
                $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
            }else {
                $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
            }
            var page_li;
            for(var i = 1; i <= totalPage; i++){
                if(number == i){
                    page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                    $("#page").append(page_li);
                }else {
                    page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                    $("#page").append(page_li);
                }
            }
            if(number == totalPage){
                $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
            }else {
                $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
            }
            $("#page").css("display", "block");
        }else {
            var start = number - 2;
            var end = number + 3;
            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
            for(var x = start; i <= end; i++){
                if(x == number){
                    page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                    $("#page").append(page_li);
                }else {
                    page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                    $("#page").append(page_li);
                }
            }
            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
            $("#page").css("display", "block");
        }
    }else {
        $("#page").css("display", "none");
    }
});

/**
 * 根据品牌来搜索
 * 如果是刚开始查询的话，那么应该是当前页应该是1，每页大小应该是12
 * 把条件存进session中，写一个分页的查询方法，条件使用session的中的条件，分页使用传过去的分页
 * 调用方法查询结果后，把条件值重设为这次查找的条件
 * */
function search_by_brand() {
    var brandId = $(this).attr('value');
    var data = 'name=brandId' + '&value=' + brandId;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                            '<div class="i-pic limit">' +
                                '<img src="' + good.firstPic + '"/>' +
                                '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                                '<p class="price fl" style="position: relative; top: 28px;">' +
                                    '<b>¥</b>' +
                                    '<strong>' + good.price +'</strong>' +
                                '</p>' +
                                '<p style="text-align: right;">' +
                                    '销量<span>' + good.accumulatedSales + '</span>' +
                                '</p>' +
                            '</div>' +
                         '</li>';
                $("#goods").append(li);
                //修改brandId的值为现在的值
                $("#brandId").val(brandId);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 根据适用者来搜索
 * */
function search_by_applyer() {
    var applyerId = $(this).attr('value');
    var data = 'name=applyerId' + '&value=' + applyerId;
    console.log(data);
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                //修改applyerId的值为现在的值
                $("#applyerId").val(applyerId);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 使用材质来搜索
 * */
function search_by_material() {
    var materialId = $(this).attr('value');
    var data = 'name=materialId' + '&value=' + materialId;
    console.log(data);
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                //修改brandId的值为现在的值
                $("#materialId").val(materialId);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 查询全部的品牌的商品
 * */
function search_no_by_brand() {
    var brandId = '';
    var data = 'name=brandId' + '&value=' + brandId;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                //修改brandId的值为现在的值
                $("#brandId").val('');
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 查询全部适用者的商品
 * */
function search_no_by_applyer() {
    var applyerId = '';
    var data = 'name=applyerId' + '&value=' + applyerId;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                //修改brandId的值为现在的值
                $("#applyerId").val('');
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 查询全部材质的商品
 * */
function search_no_by_material() {
    var materialId = '';
    var data = 'name=materialId' + '&value=' + materialId;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for (key in goods) {
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price + '</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                //修改brandId的值为现在的值
                $("#applyerId").val('');
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if (rowsTotal >= size) {
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if (totalPage <= 3) {
                        if (number == 1) {
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        } else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for (var i = 1; i <= totalPage; i++) {
                            if (number == i) {
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            } else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if (number == totalPage) {
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        } else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    } else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for (var x = start; i <= end; i++) {
                            if (x == number) {
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            } else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                } else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 根据分页查询商品
 * */
function search_by_page(currentPage) {
    var data = 'name=currentPage' + '&value=' + currentPage;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 综合排序
 * */
function COMPREHENSIVE() {
    var data = 'name=sort' + '&value=' + 1;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            $("#sorts").empty();
            $("#sorts").append('<li id="COMPREHENSIVE" class="first" onclick="COMPREHENSIVE()"><a title="综合">综合排序</a></li>');
            $("#sorts").append('<li id="SALE" onclick="SALE()"><a title="销量">销量排序</a></li>');
            $("#sorts").append('<li id="PRICE" onclick="PRICE()"><a title="价格">价格优先</a></li>');
            $("#sorts").append('<li id="REVIEW" onclick="REVIEW()"><a title="评价">评价为主</a></li>');
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 根据价格排序
 * */
function PRICE() {
    var data = 'name=sort' + '&value=' + 3;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            $("#sorts").empty();
            $("#sorts").append('<li id="COMPREHENSIVE" onclick="COMPREHENSIVE()"><a title="综合">综合排序</a></li>');
            $("#sorts").append('<li id="SALE" onclick="SALE()"><a title="销量">销量排序</a></li>');
            $("#sorts").append('<li id="PRICE" class="first" onclick="PRICE()"><a title="价格">价格优先</a></li>');
            $("#sorts").append('<li id="REVIEW" onclick="REVIEW()"><a title="评价">评价为主</a></li>');
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                            '<div class="i-pic limit">' +
                                '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                                '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                                '<p class="price fl" style="position: relative; top: 28px;">' +
                                '<b>¥</b>' +
                                '<strong>' + good.price +'</strong>' +
                                '</p>' +
                                '<p style="text-align: right;">' +
                                '销量<span>' + good.accumulatedSales + '</span>' +
                                '</p>' +
                            '</div>' +
                        '</li>';
                $("#goods").append(li);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 根据销量排序
 * */
function SALE() {
    var data = 'name=sort' + '&value=' + 2;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            $("#sorts").empty();
            $("#sorts").append('<li id="COMPREHENSIVE" onclick="COMPREHENSIVE()"><a title="综合">综合排序</a></li>');
            $("#sorts").append('<li id="SALE" class="first" onclick="SALE()"><a title="销量">销量排序</a></li>');
            $("#sorts").append('<li id="PRICE" onclick="PRICE()"><a title="价格">价格优先</a></li>');
            $("#sorts").append('<li id="REVIEW" onclick="REVIEW()"><a title="评价">评价为主</a></li>');
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 根据评论排序
 * */
function REVIEW() {
    var data = 'name=sort' + '&value=' + 4;
    $.ajax({
        type : 'post',
        url : '/searchJs',
        data : data,
        success : function (data) {
            $("#sorts").empty();
            $("#sorts").append('<li id="COMPREHENSIVE" onclick="COMPREHENSIVE()"><a title="综合">综合排序</a></li>');
            $("#sorts").append('<li id="SALE" onclick="SALE()"><a title="销量">销量排序</a></li>');
            $("#sorts").append('<li id="PRICE" onclick="PRICE()"><a title="价格">价格优先</a></li>');
            $("#sorts").append('<li id="REVIEW" class="first" onclick="REVIEW()"><a title="评价">评价为主</a></li>');
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 根据物品名称查询
 * */
function Search_by_goodName() {
    var goodName = $("#goodName").val();
    var data = "goodName=" + goodName;
    $.ajax({
        type : 'post',
        url : '/searchByName',
        data : data,
        success : function (data) {
            var page = data.data;
            var goods = page.content;
            $("#goods").empty();
            for(key in goods){
                var good = goods[key];
                var li = '<li onclick="introduct(' + good.goodId + ')">' +
                    '<div class="i-pic limit">' +
                    '<img style="height: 200px;" src="' + good.firstPic + '"/>' +
                    '<p style="text-align: left; margin-left: 13px; font-size: 13px;">' + good.goodName + '</p>' +
                    '<p class="price fl" style="position: relative; top: 28px;">' +
                    '<b>¥</b>' +
                    '<strong>' + good.price +'</strong>' +
                    '</p>' +
                    '<p style="text-align: right;">' +
                    '销量<span>' + good.accumulatedSales + '</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
                $("#goods").append(li);
                /**
                 * 分页处理
                 * 1，先得到当前页，每页大小，总共多少页，总的数量
                 * 2，先判断总的数量是否大于每页的大小，如果不大于则不需要分页（清除分页）
                 * 3，基准线为3，当小于等于3的时候，有多少页就显示多少页，当大于3的时候，以第3个位置为基准为当前页，前面2个，后面3个，再分别加一个第一页和最后一页
                 * */
                var size = page.pageSize;
                var rowsTotal = page.rowsTotal;
                var number = page.currentPage;
                if(rowsTotal >= size){
                    $("#page").empty();
                    var totalPage = page.totalPage;
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + i + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="search_by_page(1)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="search_by_page(' + x + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="search_by_page(' + totalPage + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        }
    });
}
/**
 * 跳转到商品介绍页面
 * */
function introduct(goodId) {
    location.href = '/introduction?goodId=' + goodId;
}