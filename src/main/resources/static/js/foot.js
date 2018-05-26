$(function () {
   $(".goods-delete").click(function () {
      var footId = $(this).attr('value');
      $.ajax({
          type : 'post',
          url : '/delFoot',
          data : 'footId=' + footId,
          success : function (data) {
              if(data.code == 200){
                  location.reload();
              }else if(data.code == 400){
                  location.href = '/toLogin';
              }else if(code == 500){
                  location.reload();
              }
          },
          error : function (data) {
              alert("未知错误:{" + data + "}");
          }
      });
   });
});