// 조회/수정창 


// [삭제]버튼 이벤트 
$("#deleteBCBoard").on("click", function () {
    var result;
    result = confirm("삭제하시겠습니까?");
    if (!result) {
        return;
    } else {
        var bno = $("#RUDbno").val();
        $.ajax({
            url: 'http://localhost:8080/rbcboard/'+bno,
            data: bno,
            type: 'DELETE'
        }).done(function(){
            getTodayList();
            alert("삭제가 완료되었습니다.");
            $("#closeReadModal").trigger('click');
        })
    }
});

// [수정]버튼 이벤트
$("#modifyBCBoard").on("click" ,function () {

    var $rudForm =  $("#rudFormTable");
    $rudForm.find('input').prop('readonly', false);
    $rudForm.find('textarea').prop('readonly', false);
    $rudForm.find('select').attr('disabled',false);

}