$("#repeatType").on("change", function () {
    var target = $(this);
    console.log(target.val());
    
    $("#repeatWeekdiv").css("display","none")
    $("#repeatMonthdiv").css("display","none")
    
    $("input[name=repeatWeek]").val("")
    $("#repeatMonth").val("")
    
    $("#"+target.val()+"div").css("display","")
})