var tmpFormData = new FormData();
var finalFormData = new FormData();
var isFileChanged = 0;


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

// list spread ajax 
function getCPBoardList() {
    console.log("getCPBoardList function에 들어왔나요")
    $.ajax({
        url: "/rcpboard/getCPBoardList/" + getCookie("userName"),
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            appendCPBoardList(result);
        }
    }); // end of todayList get ajax
}


// 화면에 list뿌리는 function 
// event.preventDefault(); scrollupTrigger();
function appendCPBoardList(list) {
    var str = "";
    list.forEach(board => {
        str += '<tr class="oneCPBoard">';
        str += '<td style="padding-top: 40px;"">' + '<h4>' + board.bcdate + '</h4>' + '</td>';
        str += '<td style="padding-top: 40px;">' + '<a href="#"><h4 class= "CPBTitle" '
        str += 'onclick="event.preventDefault(); scrollupTrigger(); openModal(\'readBoardModal_' + board.bno + '\')">' + board.title + '</h4></a>' + '</td>';
        str += '<td style="padding-top: 40px;">' + '<h4>' + board.file_name.substring(board.file_name.lastIndexOf('_') + 1, board.file_name.length) + '</h4>' + '</td>';
        str += '<td style="padding-top: 40px;">' + '<h4>' + '수정/삭제' + '</h4>' + '</td>';
        str += '</tr>';
    });
    cpvals.$cpListTable.html(cpvals.listHead);
    cpvals.$cpListTable.append(str);
}

$("#modifyInfo").on("click", function (e) {
    e.preventDefault()
    var title = $("#cpInfoTitle");
    var introduce = $("#cpInfoIntroduce")
    title.val() == "" ? title.val($("#title").text()) : null;
    introduce.val() == "" ? introduce.val($("#introduce").text()) : null;
    var modifiedImgFile = $("#imgUpload");

    if (!modifiedImgFile.val() == "") {
        if (!validateImg(modifiedImgFile[0].files[0].name, modifiedImgFile[0].files[0].size)) {
            return
        }
        var formData = new FormData();
        formData.append("uploadFile", modifiedImgFile[0].files[0])
        var fileName = uploadImg(formData, fileName)

        jsonData = {
            id: getCookie("userName"),
            cpInfo: {
                imgFile: fileName,
                title: title.val(),
                introduce: introduce.val()
            }
        }
    } else {
        jsonData = {
            id: getCookie("userName"),
            cpInfo: {
                title: title.val(),
                introduce: introduce.val()
            }
        }
    }
    $.ajax({
        url: "/member/modifyCPInfo",
        data: JSON.stringify(jsonData),
        contentType: "application/json; charset=utf-8",
        type: "POST",
        success: function (result) {
            loadPage()
            title.val("")
            introduce.val("")
            modifiedImgFile.val("")
            title.css("display", "none");
            introduce.css("display", "none");
            modifiedImgFile.css("display", "none");
        }
    })
    $(this).css("display", "none");
    $("#modifyInfoBtn").css("display", "inline-block")
})


$("#modifyInfoBtn").on("click", function (e) {
    e.preventDefault()
    $(this).css("display", "none");
    $("#modifyInfo").css("display", "inline-block")

    $("#cpInfoTitle").css("display", "block");
    $("#cpInfoIntroduce").css("display", "block")
    $("#imgUpload").css("display", "block")

})


// 모달창 띄우기 
function openModal(targetModal) {
    let realTargetString = targetModal;
    if (targetModal.startsWith("readBoardModal")) {
        let readTargetBno = targetModal.substring(targetModal.lastIndexOf('_') + 1, targetModal.length);
        realTargetString = targetModal.substring(0, targetModal.lastIndexOf('_'));
        getOneCPBoard(readTargetBno);
    }
    $("." + realTargetString).modal();
}


// 조회 
function getOneCPBoard(bno) {
    $.ajax({
        'url': "/rcpboard/read/" + bno,
        'type': 'GET',
        'contentType': "application/json; charset=utf-8",
        success: function (result) {
            var str = "";
            str += '<tr class="oneBoard" id='+result.bno+'>'
            str += '<td> <input class="custom-select mb-15" id="modBcDate" type="date" value=' + result.bcdate + ' disabled></td>'
            str += '<td> <input id="modTitle" type="text" value=' + result.title + ' disabled>' + '</td>'
            str += '<td> <input id="fileNameViewer" type="text" value=' + result.file_name.substring(result.file_name.lastIndexOf('_') + 1, result.file_name.length) + ' disabled>'
                + '<input style="display:none;" id="orgCPFileName" type="text" value=' + result.file_name.substring(result.file_name.lastIndexOf('_') + 1, result.file_name.length) + ' disabled>' + '</td>'
            str += '<td id="modUploadBtnArea" style="display:none;"> <button id="modFakeFileUpload">파일수정</button></td>'
            str += '<td id="modfileArea" style="display:none;"> <input id="modFileUpload" type="file"> </td>'
            str += '</tr>'
            $("#oneBoardReceived").html(str);
        }
    });
}

// 수정
function modifyCPBoard() {
    let $oneBoard = $("#oneBoardReceived");
    $("#modifyCPBoardBtn").css('display', 'none');
    $("#modifyConfirmBtn").css('display', 'inline-block');
    $("#modBcDate, #modTitle").attr('disabled', false);

    let $uploadBtn = $("#modUploadBtnArea")
    $uploadBtn.css('display', 'inline-block');
    $uploadBtn.on("click", function (e) {
        e.preventDefault();
        $("#modFileUpload").trigger("click");
    });


    // 파일이 수정되면
    $("input[type='file']").change(function () {
        var currFile = $("#modFileUpload")[0].files[0]
        if (!validateFile(currFile.name, currFile.size)) { // 유효성검사
            $(this).val("");
            return false;
        }
        $("#fileNameViewer").val(currFile.name);
        console.log("파일이 변경됨")
        isFileChanged = 1;

    });


    $("#modifyConfirmBtn").on("click", function () {
        let modBcDate = document.getElementById('modBcDate').value
        let modTitle = document.getElementById('modTitle').value
        let json = "";

        //파일명이 기존 파일명과 같은지 체크
        if (isFileChanged == 0) {
            console.log("파일 변경이 없는 경우임")
            // 기존파일을 유지시켜야함 
            // js단에서 처리해줘야 하는 이유(무조건 다시 저장해야하는이유) 
            // : 파일은 다른 파일인데 이름이 같을경우 파일명 가지고만 그 둘을 구분할 수 없기 때문
            // 단순히 org와 curr파일명이 같은지로 조건을 주면 안되고
            // 변경이 있는지 없는지를 체크해서 변경이 없는 경우에 처리해줘야 함.
            // 기존파일을 갖고오고 자시고 할 거 없고 controller에 넘겨줄때
            // 기존파일을 유지시키는 경우 filepath를 keepPrev 이런식으로 주면 될듯
            json = {
                'bno' : $(".oneBoard").attr('id'),
                'member': { 'id': getCookie("userName") },
                'title': modTitle,
                'file_path': 'KEEP',
                'file_name': 'KEEP',
                'bcdate': modBcDate
            };
        } else {
            // 파일 변경이 있는경우
            // fileSave() 에 formData넣어주기 
            console.log("파일 변경이 있는 경우!!")
            let newFile = $("#modFileUpload")[0].files;
            finalFormData.append('files', newFile);
            let fileResult = fileSave(finalFormData);
            json = {
                'bno' : $(".oneBoard").attr('id'),
                'member': { 'id': getCookie("userName") },
                'title': modTitle,
                'file_path': fileResult[0].substring(0, fileResult[0].lastIndexOf("\\") + 1),
                'file_name': fileResult[0].substring(fileResult[0].lastIndexOf("\\") + 1, fileResult[0].length),
                'bcdate': modBcDate
            };
        } // end of else 

        $.ajax({
            url: '/rcpboard/modify',
            data: JSON.stringify(json),
            type: 'PUT',
            contentType: "application/json; charset=utf-8",
            success: function () {
                $("#closeCPModifyModal").trigger("click");
            }
        });

    }) // end of modifyConfirmBtn event 
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
        str += '<td> <input class="custom-select mb-15 bcDate" type="date">' + '</td>'
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

    var fileResultArr = fileSave(finalFormData);
    console.log("-----------------")
    console.log(fileResultArr);
    var jsonArr = [];
    var $boards = $(".boards");
    $boards.each(function (i, e) {
        var $currBoard = $($boards[i]);
        var json = {
            'member': { 'id': getCookie("userName") },
            'title': $currBoard.find(".audioTitle").val(),
            'file_path': fileResultArr[i].substring(0, fileResultArr[i].lastIndexOf("\\") + 1),
            'file_name': fileResultArr[i].substring(fileResultArr[i].lastIndexOf("\\") + 1, fileResultArr[i].length),
            'bcdate': $currBoard.find(".bcDate").val()
        };
        jsonArr.push(json);
    });
    registerBoards(jsonArr);
    alert("등록이 완료되었습니다.");
    getCPBoardList();

    /*     $.ajax({
          url: '/rcpboard/registerFiles',
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
                      'member': { 'id': getCookie("userName") },
                      'title': $currBoard.find(".audioTitle").val(),
                      'file_path': result[i].substring(0, result[i].lastIndexOf("\\") + 1),
                      'file_name': result[i].substring(result[i].lastIndexOf("\\") + 1, result[i].length),
                      'bcdate': $currBoard.find(".bcDate").val()
                  };
                  jsonArr.push(json);
              });
              registerBoards(jsonArr);
              alert("등록이 완료되었습니다.");
              getCPBoardList();
          } // end of success 
      }) */
} // end of register 


function fileSave(fileFormDatas) {
    
    console.log("in fileSave function")
    console.log(fileFormDatas);

    let result = "";
    $.ajax({
        url: '/rcpboard/registerFiles',
        processData: false,
        contentType: false,
        data: fileFormDatas,
        async:false,
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            console.log("성공..했니...")
            result = data;
        }
    })
    return result;

}




function registerBoards(jsonArr) {
    $.ajax({
        url: '/rcpboard/register',
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
    isFileChanged = 0;
    cpvals.$filelistArea.html("");
    cpvals.$modifyConfirmBtn.css('display','none');
    cpvals.$modifyCPBoardBtn.css('display','inline-block');
    $('#addFiles').val("");
};



$("#modifyInfo").on("click", function (e) {
    e.preventDefault()
    var title = $("#cpInfoTitle");
    var introduce = $("#cpInfoIntroduce")
    title.val() == "" ? title.val($("#title").text()) : null;
    introduce.val() == "" ? introduce.val($("#introduce").text()) : null;
    var modifiedImgFile = $("#imgUpload");

    if (!modifiedImgFile.val() == "") {
        if (!validateImg(modifiedImgFile[0].files[0].name, modifiedImgFile[0].files[0].size)) {
            return
        }
        var formData = new FormData();
        formData.append("uploadFile", modifiedImgFile[0].files[0])
        var fileName = uploadImg(formData, fileName)

        jsonData = {
            id: getCookie("userName"),
            cpInfo: {
                imgFile: fileName,
                title: title.val(),
                introduce: introduce.val()
            }
        }
    } else {
        jsonData = {
            id: getCookie("userName"),
            cpInfo: {
                title: title.val(),
                introduce: introduce.val()
            }
        }
    }
    $.ajax({
        url: "/member/modifyCPInfo",
        data: JSON.stringify(jsonData),
        contentType: "application/json; charset=utf-8",
        type: "POST",
        success: function (result) {
            loadPage()
            title.val("")
            introduce.val("")
            modifiedImgFile.val("")
        }
    })
})

function uploadImg(formData) {
    var fileName
    $.ajax({
        url: "/member/uploadImg?userName=" + getCookie("userName"),
        processData: false,
        contentType: false,
        async: false,
        data: formData,
        type: "POST",
        success: function (result) {
            fileName = result
        }
    })
    return fileName
}

function loadPage() {
    var title = $("#title")
    var id = $("#id")
    var introduce = $("#introduce")
    var thumnail = $("#CPThumnail")

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
            console.log(result);

            thumnail.attr("src", "/rcpboard/getImg?fileName=C:/CpImg/" + result.cpInfo.imgFile)
        }
    })
}


// 동작안함. 수정할것 
/* $('.oneCPBoard').hover(function () {
    $(this).css("backgroundColor", "black");
 }, function () {
    $(this).css("backgroundColor", "white");
 });
 */