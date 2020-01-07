var cpvals = (function () {
    let $cpListTable = $("#cpboardList");
    let $cpListDiv = $("#cpListDiv");
    let listHead = '<th>방송일자</th><th>제목</th><th>파일명</th><th>수정/삭제</th>';
    let $filelistArea = $("#filelistArea");
    let $oneBoardReceived = $("#oneBoardReceived");
    let $orgCPFileName = $("#orgCPFileName");
    let $fileNameViewer = $("#fileNameViewer");
    let $modifyConfirmBtn = $("#modifyConfirmBtn");
    let $modifyCPBoardBtn =  $("#modifyCPBoardBtn");

    
    return{
        '$cpListTable':$cpListTable,
        '$cpListDiv' : $cpListDiv,
        '$filelistArea' : $filelistArea,
        '$oneBoardReceived' : $oneBoardReceived,
        'listHead' : listHead,
        '$orgCPFileName' : $orgCPFileName,
        '$fileNameViewer' : $fileNameViewer,
        '$modifyConfirmBtn' : $modifyConfirmBtn,
        '$modifyCPBoardBtn' : $modifyCPBoardBtn
    }
})()