// ===============================================================================

{/* <script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/1.4.5/socket.io.min.js"></script> */}
// 파일 유효성 검사 function (파일크기, 파일형식)
// just function 수정 필요 없음. 
// 파일명에 특문(특히 *, /, -) 걸러주기! 
function validateFile(fileName, fileSize) {
    var regex = new RegExp("(.*?)\.(wav|mp3|wma|aac|flac|mmf)$");
    var spcCheck = /[~!@#$%^&*()\-_+|<>?:{}]/;
    var maxSize = 10485760;
    if (fileSize > maxSize) {
        alert("업로드 가능한 파일 크기를 초과했습니다.")
        return false;
    };
    if (!regex.test(fileName)) {
        alert("해당 형식의 파일은 업로드 할 수 없습니다.")
        return false;
    };
    if(spcCheck.test(fileName)){
        alert("파일명에 허용되지 않는 특수문자가 있습니다.")
        return false;
    }

    return true;
}

function validateImg(fileName, fileSize) {
    fileName=fileName.toLowerCase();
    var regex = new RegExp("(.*?)\.(jpg|png)$");
    var spcCheck = /[~!@#$%^&*()\-+|<>?:{}]/;
    var maxSize = 10485760;
    if (fileSize > maxSize) {
        alert("업로드 가능한 파일 크기를 초과했습니다.")
        return false;
    };
    if (!regex.test(fileName)) {
        alert("해당 형식의 파일은 업로드 할 수 없습니다.")
        return false;
    };
    if(spcCheck.test(fileName)){
        alert("파일명에 허용되지 않는 특수문자가 있습니다.")
        return false;
    }

    return true;
}

// ===============================================================================

var getCookie = function (name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? value[2] : null;
};

// $("#logoutTag").on("click", function (e) {
//     e.preventDefault();
//     console.log(socket.emit("logout", getCookie("userName")));
//     console.log("로그이웃함");
//     location.href = '/logout2';
    
    
// })