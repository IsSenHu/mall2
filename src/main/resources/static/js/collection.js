function cancelCollect(goodId) {
    $.ajax({
        type : 'post',
        url : '/cancelCollect',
        data : 'goodId=' + goodId,
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
}