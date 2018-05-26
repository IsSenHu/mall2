$(function () {
    $("#save").click(function () {
        $(".nickNameInfo").css("color", "black").html('');
        $(".phoneInfo").css("color", "black").html('');
        $(".emailInfo").css("color", "black").html('');
        $("#userInfo").ajaxSubmit({
            type : 'post',
            url : '/updateInformation',
            success : function (data) {
                if(data.code == 200){
                    location.reload();
                    $(".nickNameInfo").css("color", "red").html(x);
                }else if(data.code == 400 || data.code == 401){
                    var error = data.data;
                    for(key in error){
                        var x = error[key];
                        if(x.indexOf("昵称") != -1){
                            $(".nickNameInfo").css("color", "red").html(x);
                        }else if(x.indexOf("手机") != -1){
                            $(".phoneInfo").css("color", "red").html(x);
                        }else if(x.indexOf("邮箱") != -1){
                            $(".emailInfo").css("color", "red").html(x);
                        }
                    }
                }else if(data.code == 500){
                    Location.reload();
                }
            },
            error : function (data) {
                console.log(data);
                Location.reload();
            }
        });
    });
    $("select[name='month']").change(changeMonth);
    $("select[name='year']").change(changeYear);
});

function changeMonth() {
    var month = $(this).val();
    var year = $("select[name='year']").val();
    changeDay(year, month);
}
function changeYear() {
    var year = $(this).val();
    var month = $("select[name='month']").val();
    changeDay(year, month);
}
function changeDay(year, month) {
    var data = 'year=' + year + '&month=' + month;
    $.ajax({
        type : 'post',
        url : '/changeDate',
        data : data,
        success : function (data) {
            $("select[name='day']").empty();
            $("select[name='day']").append(data.data);
        },
        error : function (data) {
            console.log("请求失败");
        }
    });
}