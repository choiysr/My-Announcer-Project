$("#repeatType").on("change", function () {
    var target = $(this);
    console.log(target.val());
    
    $("#repeatWeekdiv").css("display","none")
    $("#repeatMonthdiv").css("display","none")
    

$("input:checkbox[name='repeatWeek']:checked").each(function(){

    console.log($(this).data("val"));

        $(this).attr('checked', false);
        
    });

    $("#"+target.val()+"div").css("display","")
})

$("#repeatSubmitBtn").on("click", function () {
    var str=''
     
     if ($("#repeatType").val() === "repeatWeek") {
         str+= "week-"
        $("input:checkbox[name='repeatWeek']:checked").each(function(){
            str+=$(this).data("val") + ","
            console.log($(this).data("val"));
            
            });
     }else{
        str += "month-"
        str += $("#repeatMonth").val().split("-")[2]
        
     }
    console.log(str);
   
    $("#ymdSet").attr("disabled", true);
    $("#repeat").val(str)
   



})


    