var tmpFormData = new FormData();
var finalFormData = new FormData();

// list spread
function getCPBoardList() {
    $.ajax({
        url: "/rcpboard/getCPBoardList/" + getCookie("userName"),
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log("확인");
            console.log(result);
        }
    }); // end of todayList get ajax
}


function openModal() {
    $(".addFileModal").modal();
}

// 파일업로드하면 화면에 띄워주는 function 
function showFiles() {
    var uploadedFileArr = $(this)[0].files;
    var str = "";
    for (let i = 0; i < uploadedFileArr.length; i++) {
        //유효성 검사. check된 파일 이전까지의 파일만 업로드 된다.
        //continue인데 왜 다음꺼는 안될까? 
        if (!validateFile(uploadedFileArr[i].name, uploadedFileArr[i].size)) {
            $(this).val("");
            continue;
        }
        //기존파일과 파일명이 같은 파일이 있는지 검사
        if (tmpFormData.has(uploadedFileArr[i].name)) {
            alert("파일명이 중복되거나 이미 업로드 된 파일이 있습니다.")
            $(this).val("");
            return false;
        }
        str += '<tr class="boards">'
        str += '<td> <input class="custom-select mb-15 bcDate" type="date"' + '</td>'
        str += '<td> <input class="audioTitle" type="text" placeholder="방송제목을 입력하세요.">' + '</td>'
        str += '<td> <input class="fileName" type="text" disabled value="' + uploadedFileArr[i].name + '"></td>'
        str += '<td> <input name="deleteCheck" type="checkbox"> </td>'
        str += '</tr>'
        tmpFormData.append(uploadedFileArr[i].name, uploadedFileArr[i]);
    }
    $('#addFileTable > tbody:last').append(str);
}

// 파일업로드화면에서 파일을 선택하여 삭제하는 경우 삭제시키는 function 
function deleteFiles() {
    var $targets = $("input:checkbox[name='deleteCheck']:checked").parent().parent();
    $targets.find(".fileName").each(function (index, target) {
        var fileKey = target.value;
        tmpFormData.delete(fileKey);
    });
    $targets.html("");
}


// 최종 등록 function 
function registerFiles() {

    var $dates = $(".bcDate");
    var $titles = $(".audioTitle");
    var inputValidated = true;
    $dates.each(function (i) {
        if ($dates[i].value == "" || $titles[i].value == "")
            inputValidated = false;
    });

    if (!inputValidated) {
        alert("방송 송출일자와 제목을 입력해주세요.")
        return;
    }

    for (var fileData of tmpFormData.entries()) {
        finalFormData.append('files', fileData[1]);
    }

    $.ajax({
        url: 'http://localhost:8080/rcpboard/registerFiles',
        processData: false,
        contentType: false,
        data: finalFormData,
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            var jsonArr = [];
            var $boards = $(".boards");
            $boards.each(function (i, e) {
                var $currBoard = $($boards[i]);
                var json = {
                    'member' : {'id':getCookie("userName")},
                    'title': $currBoard.find(".audioTitle").val(),
                    'file_path': result[i].substring(0, result[i].lastIndexOf("\\") + 1),
                    'file_name': result[i].substring(result[i].lastIndexOf("\\") + 1, result[i].length),
                    'bcdate': $currBoard.find(".bcDate").val()
                };
                jsonArr.push(json);
            });
            registerBoards(jsonArr);
            alert("등록이 완료되었습니다.")
        } // end of success 
    })

}

function registerBoards(jsonArr) {
    $.ajax({
        url: 'http://localhost:8080/rcpboard/register',
        data: JSON.stringify(jsonArr),
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        success: function () {
            $("#closeCPRegisterModal").trigger("click");
            resetCPUploadElements();
        }
    });
}

function resetCPUploadElements() {
    tmpFormData = new FormData();
    finalFormData = new FormData();
    $('#filelistArea').html("");
    $('#addFiles').val("");


    $("#modifyInfo").on("click", function () {
        var modifytitle = $("#cpInfoTitle").val()
        var modifyintroduce = $("cpInfoIntroduce").val()

        jsondata = ({
            id: getCookie("userName"),
            CPInfo: {
                title: modifytitle,
                introduce: modifyintroduce
            }
        })

        $.ajax({
            url: "/rcpboard/modifyInfo",
            data: JSON.stringify(jsonData),
            contentType: "application/json; charset=utf-8",
            type: "POST",
            success: function (result) {

            }
        })
    })

};


function loadPage() {
    var title = $("#title")
    var id = $("#id")
    var introduce = $("#introduce")

    $.ajax({
        url: "/rcpboard/getUserInfo",
        data: { userName: getCookie("userName") },
        contentType: "application/json; charset=utf-8",
        type: "GET",
        success: function (result) {
            console.log(result.cpInfo)
            id.text(result.id)
            title.text(result.cpInfo.title)
            introduce.text(result.cpInfo.introduce)
        }
    })

}


$("#modifyInfo").on("click", function (e) {
    e.preventDefault()
    var title = $("#cpInfoTitle");
    var introduce = $("#cpInfoIntroduce")

    jsonData = {
        id: getCookie("userName"),
        cpInfo: {
            title: title.val(),
            introduce: introduce.val()
        }
    }

    $.ajax({
        url: "/member/modifyCPInfo",
        data: JSON.stringify(jsonData),
        contentType: "application/json; charset=utf-8",
        type: "POST",
        success: function (result) {
        }
    })
})


function loadPage() {
    var title =$("#title")
    var id =$("#id")
    var introduce =$("#introduce")

    $.ajax({
        url: "/rcpboard/getUserInfo",
        data: {userName:getCookie("userName")},
        contentType: "application/json; charset=utf-8",
        type: "GET",
        success: function (result) {
           console.log(result.cpInfo)
           id.text(result.id)
           title.text(result.cpInfo.title)
           introduce.text(result.cpInfo.introduce)
        }
    })
}