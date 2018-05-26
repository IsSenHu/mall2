$(function () {
    $("#login").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        $.ajax({
            type : 'post',
            url : '/login',
            data : 'username=' + username + '&password=' + password,
            success : function (data) {
                if(data.code == 200){
                    location.href = '/home';
                }else if(data.code == 400){
                    alert("用户名不存在或密码错误")
                }
            },
            error : function (data) {
                alert("未知错误:{" + data + "}");
            }
        });
    });
});