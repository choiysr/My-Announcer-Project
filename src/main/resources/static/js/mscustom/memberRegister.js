 $("#login").on("click", function name(e) {
    e.preventDefault()
    $("#loginBtn").click();
})

$('#registerBtn').on('shown.bs.modal', function () {
    $('#exampleModal').trigger('focus')
})

$("#logoutTag").css("display", "none")


$("#postRegister").on("click", function (e) {

    var id = $("#memberid").val() 
    var password = $("#memberpassword").val()
    var name = $("#name").val()
    var email = $("#email").val()
    var address = $("#address").val()

    if (!checkMinLength(id, password, email,name,address)) {
        return
    }
    


    jsonData = {
        memberid: id,
        memberpassword: password,
        email : email,
        name: name,
        address: address
    }

    $.ajax({
        url: "/member/register",
        data: JSON.stringify(jsonData),
        contentType: "application/json; charset=utf-8",
        type: "POST",
        success: function (result) {
        $("#memberid").val(""),
         $("#memberpassword").val(""),
        $("#name").val(""),
        $("#address").val("")
        $("#CloseRegisterForm").click();
        alert("회원가입이 완료되었습니다.")
        }
    })
})

$("#checkOverlap").on("click", function (e) {
    e.preventDefault();

    var id = $("#memberid").val();

    if (id.length < 5) {
        alert("id는 5자 이상!")
        return;
    }

    $.ajax({
        url: "/member/checkOverlap/"+$("#memberid").val(),
        type: "GET",
        success: function (result) {
            if(result === false){
                alert("사용가능한 아이디입니다.")
                $("#postRegister").attr("disabled", false)
                // $("#memberid").attr("readonly", true)
            }else{
                alert("중복된 아이디입니다.")
                $("#postRegister").attr("disabled", true)
            }
        }
    })
    
})


function checkMinLength(id, pw, em,name,addr) {
    if (id.length == 0 || pw.length == 0 || name.length == 0 || address.length == 0 || em.length == 0 || addr.length == 0 ) {
        alert("미기입한 항목이 있습니다.")
        return false  }
    if (pw.length < 5) {
        alert("패스워드는 5글자 이상")
        return  false  }
        return true
}

// $("#memberid").change( function () {
//     $("#postRegister").attr("disabled", true)
// })

var checkOverlapOldVal;
$("#memberid").on("propertychange change keyup paste", function() {
    var currentVal = $(this).val();
    if(currentVal == checkOverlapOldVal) {
        return;
    }
    
    checkOverlapOldVal = currentVal;
    $("#postRegister").attr("disabled", true);
});