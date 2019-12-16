// todaylist.js의 getTodayList() function호출
// 이거 main으로 옮기는게 좋을듯 
getTodayList();

///////////////////// modal event area ////////////////////
var startdate, starttime, title, content, gender, alarmBell, jsonData, alarmObj, sourceObj, introObj, endingObj;
var audioObjects;

// dto 요소들을 현재 설정되어 있는 값으로 설정해주는 함수(startdate,starttime제외)
// 
function setAllElements($targetForm) {
    title = $targetForm.find(".title").val();
    content = $targetForm.find(".content").val();
    gender = $targetForm.find(".voiceGender option:selected").val();
    alarmBell = $targetForm.find(".alarmBell option:selected").val();
    setAudioElements();
}

function setAudioElements() {
    alarmObj = $("#hiddenAlarm" + alarmBell)[0];
    sourceObj = $("#sourcePlayer")[0];
    introObj = $("#introPlayer")[0];
    endingObj = $("#endingPlayer")[0];
    audioObjects = [alarmObj, introObj, sourceObj, endingObj];
}

// ===============================================================================
// 띄어쓰기 교정  ==>  API이상함. 다른걸로 수정하던지 없애던지 할 것 
// rud적용 완료.
$(".spaceChecker").on("click", function (e) {
    e.preventDefault();
    var key = "3439797815659168424";
    var $contentArea = $(this).parent().parent().parent().find('.content')
    var sentence = $contentArea.val();
    var jsonData = {
        key: key,
    };
    $.getJSON("http://api.adams.ai/datamixiApi/autospacing?sentence=" + sentence, jsonData, function (data) {
        var resultText = data.return_object.answer;
        $contentArea.val(resultText);
    })
})
// ===============================================================================

// 긴급공지 체크 이벤트 - 비활성화시켜야할 요소들 disabled
// register only 수정 필요 없음. 
$(".urgentCheck").change(function (e) {
    var isChecked = $(".urgentCheck").is(":checked");
    $("#ymdSet, #timeSet").prop("disabled", isChecked);
    $("#repeat").prop("disabled", isChecked);
});
// ===============================================================================

// 등록버튼클릭시 인트로와/엔딩오디오가 업로드 되어있는지 확인 및 controller로 데이터날린 후 저장하고
// uuid를 포함한 파일명을 return하는 함수
// just function 수정 필요 없음. 
function checkadditionalAudioExist(object) {
    var formData = new FormData();
    var wholeFileName = "";
    formData.append("additionalAudio", object[0]);
    $.ajax({
        url: 'http://localhost:8080/rbcboard/registerFiles',
        processData: false,
        contentType: false,
        async: false,
        data: formData,
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            wholeFileName = result[0].substring(result[0].lastIndexOf("*") + 1, result[0].length);
        }
    });
    return wholeFileName;
} // end of checkadditionalAudioExist function

// ===============================================================================

// 파일 유효성 검사 function (파일크기, 파일형식)
// just function 수정 필요 없음. 
// 파일명에 특문(특히 *, /, -) 걸러주기! 
function validateFile(fileName, fileSize) {
    var regex = new RegExp("(.*?)\.(wav|mp3|wma|aac|flac|mmf)$");
    var maxSize = 10485760;
    if (fileSize > maxSize) {
        alert("업로드 가능한 파일 크기를 초과했습니다.")
        return false;
    };
    if (!regex.test(fileName)) {
        alert("해당 형식의 파일은 업로드 할 수 없습니다.")
        return false;
    };
    return true;
}

// ===============================================================================

// 오디오객체배열을 파라미터로 받아 재생시켜주는 function
// just function 수정 필요 없음. 
function playAllAudios(audios) {
    for (let j = audios.length - 1; j > 0; j--) {
        // 이거 나중에 수정해주자 : 페이지경로로되어있는 이유는 초기화해주어서 그런듯 
        if (audios[j].src == "http://localhost:8080/bcboard/todayList") {
            audios.splice(j, 1);
        } else {
            audios[j].load();
        }
    }
    audios[0].play();
    for (let i = 0; i < audios.length - 1; i++) {
        audios[i].addEventListener("ended", function (e) {
            audios[i + 1].play();
            this.removeEventListener("ended", arguments.callee);
        });
    }
}

// ===============================================================================

// 파일 업로드되면 취소하는 버튼 만드는 function
// rud적용 완료.
function makeCancelBtn(targetAdditionalAudio) {
    console.log(targetAdditionalAudio);
    var cancelBtnArea = targetAdditionalAudio.parent().parent().find('.uploadCancelBtn');
    cancelBtnArea[0].innerHTML = "<i class='material-icons cancelBtn'>cancel</i>";
}

// ===============================================================================

// 파일업로드 취소 버튼 event (업로드된 파일reset)
// rud적용 완료.
$(".uploadCancelBtn").on("click", function () {
    $(this).parent().find($("input[type='file']").val(""));
    this.innerHTML = ""; 
});

// ===============================================================================

// 파일 업로드 감지(업로드~미리듣기 전까지의 과정)
// rud적용 완료.
$("input[type='file']").change(function () {
    var formData = new FormData();
    var $currFile =$(this);
    var additionalAudio = $currFile[0].files;

    var fileTagName = this.name;
    if (!validateFile(additionalAudio[0].name, additionalAudio[0].size)) { // 유효성검사
        $(this).val("");
        return false;
    }
    formData.append("additionalAudio", additionalAudio[0]);
    $.ajax({
        url: 'http://localhost:8080/rbcboard/fileUpload',
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            $("#" + fileTagName + "Player").attr('src', "http://localhost:8080/rbcboard/" + result[0])
            makeCancelBtn($currFile);
        }
    });
});

// ===============================================================================
// 미리듣기 버튼 이벤트
// 수정이 제대로 된것일까.
$(".preListen").on("click", function (e) {
    e.preventDefault();
    $(this).parent().parent().find(".submitBtn").prop("disabled",false);
    var $forsending = $(this).parent().parent().parent();
    console.log($forsending);
    setAllElements($forsending);
    jsonData = {
        title: title,
        content: content,
        gender: gender
    };
    $.ajax({
        url: 'http://localhost:8080/rbcboard/prelisten',
        data: jsonData,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (result) {
            $("#sourcePlayer").attr('src', "http://localhost:8080/rbcboard/" + result[0]);
            setAudioElements();
            playAllAudios(audioObjects);
        } // end of success function
    });  // end of ajax
}); //end of preListen button event


// ===============================================================================
// 저장버튼 클릭 이벤트
$("#submitBtn").on("click", function (e) {
    e.preventDefault();
    setAllElements();
    var result;

    if ($(".urgentCheck").is(":checked")) {
        result = confirm("확인 버튼을 누르면 곧바로 방송이 송출됩니다. 정말 등록하시겠습니까?");
        if (!result) {
            return; // confirm창에서 취소버튼을 누른경우.
        } else { // 긴급공지
            // 메서드로 뺼 수 있나 고민해볼것
            var uToday = new Date();
            var uDay = ((uToday.getDate()).toString().length == 1 ? "0" + (uToday.getDate()) : (uToday.getDate()));
            var uMonth = ((uToday.getMonth() + 1).toString().length == 1 ? "0" + (uToday.getMonth() + 1) : (uToday.getMonth() + 1));
            var uHour = uToday.getHours().toString().length == 1 ? "0" + uToday.getHours() : uToday.getHours();
            var uMinute = uToday.getMinutes().toString().length == 1 ? "0" + uToday.getMinutes() : uToday.getMinutes();
            startdate = uToday.getFullYear() + "-" + uMonth + "-" + uDay;
            starttime = uHour + ":" + uMinute;

            var intro = "";
            var ending = "";

            // if intro exists
            if ($("#introPlayer").attr('src') !== "") {
                intro = checkadditionalAudioExist($("input[name='intro']")[0].files);
            }
            // if ending exists
            if ($("#endingPlayer").attr('src') !== "") {
                ending = checkadditionalAudioExist($("input[name='ending']")[0].files);
            }
            // return으로 파일명(with UUID)받아서 jsonData로는 only 파일명만 세팅해주기

            jsonData = {
                content: content,
                title: title,
                gender: gender,
                startdate: startdate,
                starttime: starttime,
                audioVO: { alarmBell: alarmBell, intro: intro, ending: ending }
            };

            setAudioElements();
            playAllAudios(audioObjects);
        }
    } else { // 긴급방송이 아닌경우 
        var ymdSet = $("#ymdSet")[0];
        var timeSet = $("#timeSet")[0];
        if (ymdSet.value == "" || timeSet.value == "") {
            alert("방송 일자와 시간을 설정해주세요.")
            return;
        }
        result = confirm("방송을 등록하시겠습니까?");
        if (!result) {
            return;
        } else {
            startdate = ymdSet.value;
            starttime = timeSet.value;
            var intro = "";
            var ending = "";
            if ($("#introPlayer").attr('src') !== "") {
                intro = checkadditionalAudioExist($("input[name='intro']")[0].files);
            } // end of if intro exists
            if ($("#endingPlayer").attr('src') !== "") {
                ending = checkadditionalAudioExist($("input[name='ending']")[0].files);
            } // end of if ending exists

            jsonData = {
                content: content,
                title: title,
                gender: gender,
                startdate: startdate,
                starttime: starttime,
                audioVO: { alarmBell: alarmBell, intro: intro, ending: ending }
            };
        }
    } // end of else(긴급방송이 아닌경우)

    $.ajax({
        url: 'http://localhost:8080/rbcboard/register',
        data: JSON.stringify(jsonData),
        type: 'POST',
        contentType: "application/json; charset=utf-8",
    }).done(function (data) {
        getTodayList();
        $("#todayListBtn").trigger('click');
    }) // end of ajax
}); // end of submitbutton event

