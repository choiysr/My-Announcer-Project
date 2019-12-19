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
            url: 'http://localhost:8080/rbcboard/' + bno,
            data: bno,
            type: 'DELETE'
        }).done(function () {
            getTodayList();
            alert("삭제가 완료되었습니다.");
            $("#closeReadModal").trigger('click');
        })
    }
});

// [수정]버튼 이벤트
$("#modifyBCBoard").on("click", function (e) {
    var $rudForm = $("#rudFormTable");
    $rudForm.find('input').prop('readonly', false);
    $rudForm.find('textarea').prop('readonly', false);
    $rudForm.find('select').attr('disabled', false);

    $("#fakeBtnForIntro, #fakeBtnForEnding").css('display', 'block');
    // 일단 그냥
    // $("#modifyBCBoard").css('display','none');
    $("#updateBCBoard").css('display', 'block');
    if ($("#RUDoriginalIntro").val() !== "") {
        makeCancelBtn($("input[name='RUDintro']"));
    }
    if ($("#RUDoriginalEnding").val() !== "") {
        makeCancelBtn($("input[name='RUDending']"));
    }

    // 파일업로드가 변경되지 않으면 기존파일을 그대로 유지해야한다


});



$(".fakeBtnForAdditional").on("click", function (e) {
    // fakeBtn을 클릭하면 input=file 파일첨부가 클릭되도록 해야함. 
    $(this).parent().find($("input[type='file']")).click();
});

// 기존파일을 유지시키는 경우, 대상 파일을 다시 저장시키는 function 
function keepPrevFile(prevPath) {
    console.log("function안 파라미터 : " + prevPath)
    var wholeFileName;
    $.ajax({
        url: 'http://localhost:8080/rbcboard/getPrevFile/'+prevPath,
        async: false,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        dataType : 'json',
        success: function (result) {
          wholeFileName = result[0].substring(result[0].lastIndexOf("*") + 1, result[0].length);
          console.log("기존파일유지 - 파일패스나와야함")
          console.log(wholeFileName);
        }
    });
    return wholeFileName;
}


$("#updateBCBoard").on("click", function (e) {
    var $targetForm = $(this).parent().parent().parent();
    setAllElements($targetForm);
    console.log($targetForm);
    startdate = $targetForm.find('.ymdSet').val();

    starttime = $targetForm.find('.timeSet').val();

    console.log(startdate);
    console.log(starttime);


    if (startdate == "" || starttime == "") {
        alert("방송 일자와 시간을 설정해주세요.")
        return;
    }
    result = confirm("방송을 등록하시겠습니까?");
    if (!result) {
        return;
    } else {
        intro = "";
        ending = "";
        e.preventDefault();
        // introPlayer에 셋팅이 되어있고,
        // 1. 파일을 덮어쓴경우(기존 코드쓰면됨)
        //   intro = checkadditionalAudioExist($("input[name='RUDintro']")[0].files);
        // 2. 기존파일을 유지시키는 경우 

        if ($("#introPlayer").attr('src') !== "") {
            // 이 조건의 문제는 무조건 미리듣기를 한 이후여야함.
            // textarea변경감지해서 등록버튼 비활성화 시키는 function을
            // 목요일까지 만들자
            if ($("input[name='RUDintro']")[0].files.length == 0) {
                // 기존파일을 유지시키는 경우 
                // 파일명 받아와서 intro 세팅해줘야함.
                intro = keepPrevFile($("#RUDoriginalIntro").val());
            } else {
                intro = checkadditionalAudioExist($("input[name='RUDintro']")[0].files);
            }
        }
        if ($("#endingPlayer").attr('src') !== "") {
            if ($("input[name='RUDending']")[0].files.length == 0) {
                console.log("넌또 왜 안돼")
                ending = keepPrevFile($("#RUDoriginalEnding").val());
                console.log("return한 ending 확인 : "+ending)
            } else {
                ending = checkadditionalAudioExist($("input[name='RUDending']")[0].files);
            }           
        }

        setAllElements($targetForm);
        var bno = $("#RUDbno").val();
        console.log("bno : " + bno + ", content : "+ content +", title : " + title);
        console.log("인트로 파일명 잘 셋팅되어있는지 확인 : " + intro)
        var jsonData = {
            'bno': bno,
            'content': content,
            'title': title,
            'gender': gender,
            'startdate': startdate,
            'starttime': starttime,
            'audioVO': { 'alarmBell': alarmBell, 'intro': intro, 'ending': ending }
        };

        $.ajax({
            url: 'http://localhost:8080/rbcboard/modify',
            data: JSON.stringify(jsonData),
            type: 'PUT',
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            getTodayList();
            alert("수정이 완료되었습니다.");
            $("#closeReadModal").trigger('click');
        })

    } // 
})


// 오디오파일을 업로드했을때 바로 intro에 넣어준다 -> register에 function있음
// (미리듣기/등록시에) 수정화면에 파일셋팅 안되어있으면 기존파일을 가져와서 introPlayer에 셋팅해주는 메서드
function getOriginalAudios(targetAudio) {
    // targetaudio는 intro or ending으로 들어온다.  
    // input file이 비어있는지 체크  >> 이건 function불러오기전에 체크
    var target = $("#RUDoriginal" + targetAudio).val();
    var player = $("#" + targetAudio.toLowerCase() + "Player").attr('src', target);
}