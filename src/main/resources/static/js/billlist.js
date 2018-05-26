$(function () {
   $.ajax({
       type : 'post',
       url : '/billDetails/1/12',
       success : function (data) {
           var bills = data.data.content;
           for(key in bills){
               var bill = bills[key];
               var date = bill.payTime;
               var date_time = date.split('T');
               date = date_time[0];
               var time = date_time[1].substr(0, 8);
               var money = bill.money.toString();
               if(money.indexOf('.') != -1){
                   var point_number = money.length - (money.indexOf('.') + 1);
                   if(point_number == 1){
                       money += '0';
                   }
               }else {
                    money += '.00';
               }
               var tr = '<tr>' +
                            '<td class="img">' +
                                '<i><img src="' + bill.pic + '"></i>' +
                            '</td>' +
                            '<td class="time">' +
                                '<p>' + date +'</p><p class="text-muted">' + time + '</p>' +
                            '</td>' +
                            '<td class="time name">' +
                                '<p class="content">' + bill.goodName + '</p>' +
                            '</td>' +
                            '<td class="amount">' +
                                '<span class="amount-pay">-' + money + '</span>' +
                            '</td>' +
                            '<td class="amount">' +
                                '<span class="amount-pay">' + bill.bankName + '</span>' +
                            '</td>' +
                        '</tr>';
               $("#tbody_tr").append(tr);
               /**
                * 加载页面后显示分页
                * */
               var number = data.data.currentPage;
               var totalPage = data.data.totalPage;
               var rowsTotal = data.data.rowsTotal;
               if(rowsTotal >= 12){
                   $("#page").empty();
                   if(totalPage <= 3){
                       if(number == 1){
                           $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                       }else {
                           $("#page").append('<li><a onclick="bills(1, 12)">&laquo;</a></li>');
                       }
                       var page_li;
                       for(var i = 1; i <= totalPage; i++){
                           if(number == i){
                               page_li = '<li class="am-active"><a onclick="bills(' + i + ',' + 12 + ')">' + i + '</a></li>';
                               $("#page").append(page_li);
                           }else {
                               page_li = '<li><a onclick="bills(' + i + ',' + 12 + ')">' + i + '</a></li>';
                               $("#page").append(page_li);
                           }
                       }
                       if(number == totalPage){
                           $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                       }else {
                           $("#page").append('<li><a onclick="bills(' + i + ',' + 12 + ')">&raquo;</a></li>');
                       }
                       $("#page").css("display", "block");
                   }else {
                       var start = number - 2;
                       var end = number + 3;
                       $("#page").append('<li><a onclick="bills(1, 12)">&laquo;</a></li>');
                       for(var x = start; i <= end; i++){
                           if(x == number){
                               page_li = '<li class="am-active"><a onclick="bills(' + x + ',' + 12 + ')">' + x + '</a></li>';
                               $("#page").append(page_li);
                           }else {
                               page_li = '<li><a onclick="bills(' + x + ',' + 12 + ')">' + x + '</a></li>';
                               $("#page").append(page_li);
                           }
                       }
                       $("#page").append('<li><a onclick="bills(' + totalPage + ',' + 12 + ')">&raquo;</a></li>');
                       $("#page").css("display", "block");
                   }
               }else {
                   $("#page").css("display", "none");
               }
           }
       },
       error : function () {
           console.log("分页无法读取");
       }
   });
});

function bills(number, size) {
    $.ajax({
        type : 'post',
        url : '/billDetails/' + number + '/' + size,
        success : function (data) {
            $("#tbody_tr").empty();
            var bills = data.data.content;
            for(key in bills){
                var bill = bills[key];
                var date = bill.payTime;
                var date_time = date.split('T');
                date = date_time[0];
                var time = date_time[1].substr(0, 8);
                var money = bill.money.toString();
                if(money.indexOf('.') != -1){
                    var point_number = money.length - (money.indexOf('.') + 1);
                    if(point_number == 1){
                        money += '0';
                    }
                }else {
                    money += '.00';
                }
                var tr = '<tr>' +
                    '<td class="img">' +
                    '<i><img src="' + bill.pic + '"></i>' +
                    '</td>' +
                    '<td class="time">' +
                    '<p>' + date +'</p><p class="text-muted">' + time + '</p>' +
                    '</td>' +
                    '<td class="time name">' +
                    '<p class="content">' + bill.goodName + '</p>' +
                    '</td>' +
                    '<td class="amount">' +
                    '<span class="amount-pay">-' + money + '</span>' +
                    '</td>' +
                    '<td class="amount">' +
                    '<span class="amount-pay">' + bill.bankName + '</span>' +
                    '</td>' +
                    '</tr>';
                $("#tbody_tr").append(tr);
                /**
                 * 加载页面后显示分页
                 * */
                var number = data.data.currentPage;
                var totalPage = data.data.totalPage;
                var rowsTotal = data.data.rowsTotal;
                if(rowsTotal >= 12){
                    $("#page").empty();
                    if(totalPage <= 3){
                        if(number == 1){
                            $("#page").append('<li class="am-disabled"><a href="#">&laquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="bills(1, 12)">&laquo;</a></li>');
                        }
                        var page_li;
                        for(var i = 1; i <= totalPage; i++){
                            if(number == i){
                                page_li = '<li class="am-active"><a onclick="bills(' + i + ',' + 12 + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="bills(' + i + ',' + 12 + ')">' + i + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        if(number == totalPage){
                            $("#page").append('<li class="am-disabled"><a href="#">&raquo;</a></li>');
                        }else {
                            $("#page").append('<li><a onclick="bills(' + i + ',' + 12 + ')">&raquo;</a></li>');
                        }
                        $("#page").css("display", "block");
                    }else {
                        var start = number - 2;
                        var end = number + 3;
                        $("#page").append('<li><a onclick="bills(1, 12)">&laquo;</a></li>');
                        for(var x = start; i <= end; i++){
                            if(x == number){
                                page_li = '<li class="am-active"><a onclick="bills(' + x + ',' + 12 + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }else {
                                page_li = '<li><a onclick="bills(' + x + ',' + 12 + ')">' + x + '</a></li>';
                                $("#page").append(page_li);
                            }
                        }
                        $("#page").append('<li><a onclick="bills(' + totalPage + ',' + 12 + ')">&raquo;</a></li>');
                        $("#page").css("display", "block");
                    }
                }else {
                    $("#page").css("display", "none");
                }
            }
        },
        error : function () {
            console.log("分页无法读取");
        }
    });
}