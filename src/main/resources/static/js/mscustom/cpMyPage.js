var tmpFormData = new FormData();
var finalFormData = new FormData();

function openModal() {
    $(".addFileModal").modal();
}

function showFiles() {
    var uploadedFileArr = $(this)[0].files;
    var str = "";
    for (let i = 0; i < uploadedFileArr.length; i++) {
        str += '<tr>'
        str += '<td> <input class="custom-select mb-15 bcDate" type="date"' + '</td>'
        str += '<td> <input class="audioTitle" type="text" placeholder="방송제목을 입력하세요.">' + '</td>'
        str += '<td> <input class="fileName" type="text" disabled value="' + uploadedFileArr[i].name + '"></td>'
        str += '<td> <input name="deleteCheck" type="checkbox"> </td>'
        str += '</tr>'
        tmpFormData.append(uploadedFileArr[i].name,uploadedFileArr[i]);
    }
    $('#addFileTable > tbody:last').append(str);
}

function deleteFiles() {
    var $targets =$("input:checkbox[name='deleteCheck']:checked").parent().parent();
    $targets.find(".fileName").each(function(index,target) {
        var fileKey = target.value;
        tmpFormData.delete(fileKey);
    });
    $targets.html("");
     for(var pair of tmpFormData.entries()) {
        console.log(pair[0]+ ', '+ pair[1]); 
        console.log($(pair[1])[0]);
     } 
}

function registerFiles() {
    
    for(var fileData of tmpFormData.entries()){
        finalFormData.append('files',fileData[1]);
    }

    $.ajax({
        url: 'http://localhost:8080/rcpboard/registerFiles',
        processData: false,
        contentType: false,
        data: finalFormData,
        type: 'POST',
        dataType: 'json',
        success: function(result) {
            console.log("ajax return value");
            for(let i =0;i<result.length;i++) {
                console.log(result[i]);
            }
        }
    })
}


// 파일 성공쓰
// 파일패스랑 이름 분리해서 제목이랑 매핑하고 register해주면 됨!



$("#modifyInfo").on("click",function () {
    var title =$("#cpInfoTitle").val()
    var introduce = $("cpInfoIntroduce").val()

    jsondata =({
      CPInfo:  {title:title,
        introduce:introduce},
        memberid:getCookie("userName")
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


function loadPage() {
    var title =$("#title")
    var id =$("#memberid")
    var introduce =$("#introduce")

    $.ajax({
        url: "/rcpboard/getUserInfo",
        data: {userName:getCookie("userName")},
        contentType: "application/json; charset=utf-8",
        type: "GET",
        success: function (result) {
           console.log(result.cpInfo)
           id.text(result.memberid)
           title.text(result.cpInfo.title)
           introduce.text(result.cpInfo.introduce)

           
        }
    })
    
}

var getCookie = function (name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? value[2] : null;
};
