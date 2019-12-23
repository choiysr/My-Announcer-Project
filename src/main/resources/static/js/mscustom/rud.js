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

// 모달창 [수정]버튼 이벤트
$(".modifyBCBoard").on("click", function (e) {
    e.preventDefault();
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
});



$(".fakeBtnForAdditional").on("click", function () {
    // fakeBtn을 클릭하면 숨겨져있는 input=file 파일첨부가 클릭되도록 해야함. 
    $(this).parent().parent().prev().find($("input[type='file']")).click();
});

// 기존파일을 유지시키는 경우, 대상 파일을 다시 저장시키는 function 
function keepPrevFile(prevPath) {
    var wholeFileName;
    $.ajax({
        url: 'http://localhost:8080/rbcboard/getPrevFile/'+prevPath,
        async: false,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        dataType : 'json',
        success: function (result) {
          wholeFileName = result[0].substring(result[0].lastIndexOf("*") + 1, result[0].length);
        }
    });
    return wholeFileName;
}

// 최종등록버튼 클릭 이벤트 
$("#updateBCBoard").on("click", function (e) {
    var $targetForm = $(this).parent().parent().parent();
    setAllElements($targetForm);
    startdate = $targetForm.find('.ymdSet').val();
    starttime = $targetForm.find('.timeSet').val();
    
    console.log("왜 조건문에 만족하는가")
    console.log($("#RUDrepeat").val());
    console.log(startdate);
    console.log(starttime);

    if (($("#RUDrepeat").val() == "" && startdate == "") || (starttime == "")) {
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
                ending = keepPrevFile($("#RUDoriginalEnding").val());
            } else {
                ending = checkadditionalAudioExist($("input[name='RUDending']")[0].files);
            }           
        }

        setAllElements($targetForm);
        var bno = $("#RUDbno").val();
        var repeatSet = $("#RUDrepeat").val();
        var jsonData; 

        if (repeatSet.startsWith("week")) {
            jsonData = {
                'bno':bno,
                'content': content,
                'title': title,
                'gender': gender,
                'starttime': starttime,
                'audioVO': { 'alarmBell': alarmBell, 'intro': intro, 'ending': ending },
                'repeatVO': { 'repeatWeek': repeatSet }
            }; 
        } else if (repeatSet.startsWith("month")) {
            jsonData = {
                'bno': bno,
                'content': content,
                'title': title,
                'gender': gender,
                'starttime': starttime,
                'audioVO': { alarmBell: alarmBell, intro: intro, ending: ending },
                'repeatVO': { repeatMonth: repeatSet }
            };
        } else {
            jsonData = {
                'bno': bno,
                'content': content,
                'title': title,
                'gender': gender,
                'startdate': startdate,
                'starttime': starttime,
                'audioVO': { 'alarmBell': alarmBell, 'intro': intro, 'ending': ending }
            };
        }

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


// repeatview를 위한 문자열만 반환해주는 function / parameter : original repeat value
// // usecase 
// : 조회시 hidden되어있는 repeat에 값을 가져오면(모달조회창 띄우는 이벤트에) user가 보는 view에 띄워줌
// : 수정시 반복설정 값을 변경하면 로직은 hidden에전달 후 이 메서드를 호출하여 view에 띄워줌
function getRepeatViewText(orgValue) {
    var weeks = ["일","월","화","수","목","금","토"];
    var tmpStr, result;
    if(orgValue.startsWith("week")){
        result = "매주 ";
        tmpStr = orgValue.substring(orgValue.lastIndexOf("-")+1,orgValue.length-1).split(",");
        tmpStr.forEach(function(target){
            result += weeks[target]+",";
        })
        result = result.substring(0,result.lastIndexOf(','))+"요일 반복"
    } else {
        result = "매월 ";
        tmpStr = orgValue.substring(orgValue.lastIndexOf("-")+1,orgValue.length);
        result += tmpStr+"일 반복"
    }
    return result;
}


// 오디오파일을 업로드했을때 바로 player에 넣어준다 -> register에 function있음
// (미리듣기/등록시에) 수정화면에 파일셋팅 안되어있으면 기존파일명을 가져와서 player src에 셋팅해주는 메서드
function getOriginalAudios(targetAudio) {
    // targetaudio는 intro or ending으로 들어온다.  
    // input file이 비어있는지 체크  >> 이건 function불러오기전에 체크
    var target = $("#RUDoriginal" + targetAudio).val();
    var player = $("#" + targetAudio.toLowerCase() + "Player").attr('src', target);
}