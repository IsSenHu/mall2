$(function () {
    $("#save").click(function () {
        var answer1 = $("#user-answer1").val().trim();
        var answer2 = $("#user-answer2").val().trim();
        var answer3 = $("#user-answer3").val().trim();
        var q1 = $("select[name='question1']").val();
        var q2 = $("select[name='question2']").val();
        var q3 = $("select[name='question3']").val();
        var data = 'answer1=' + answer1 + '&answer2=' + answer2 + '&answer3=' + answer3 + '&q1=' + q1 + '&q2=' + q2 + '&q3=' + q3;
        $.ajax({
            type : 'post',
            url : "/setSafeQuestion",
            data : data,
            success : function (data) {
                var x = data.code;
                if(x == 200){
                    $("#form").empty();
                    $("#finish").removeClass("step-2").addClass("step-1");
                }else if(x == 400){
                    location.href = '/toLogin';
                }else if(x == 401){
                    var errors = data.data;
                    for(key in errors){
                        var error = errors[key];
                        if(error.indexOf("1") != -1){
                            $("#user-answer1").css("color", "red").val(error.substr(1, error.length)).prop("name", "error");
                        }else if(error.indexOf("2") != -1){
                            $("#user-answer2").css("color", "red").val(error.substr(1, error.length)).prop("name", "error");
                        }else if(error.indexOf("3") != -1){
                            $("#user-answer3").css("color", "red").val(error.substr(1, error.length)).prop("name", "error");
                        }
                    }
                }else if(x == 500){
                    console.log("未知错误:{" + data + "}");
                }
            },
            error : function (data) {
                console.log("请求出错:{" + data + "}");
            }
        });
    });
});
function reset() {
    if($(this).prop("name") == "error"){
        $(this).css("color", "black").val("").prop("name", "");
    }
}