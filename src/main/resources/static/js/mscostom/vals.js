var vals = (function () {
    let $listdiv = $("#listtable");
    let $totalListtable = $("#totalListtable");
    let $pagediv = $("#pagediv")
    
    let today = new Date();
    let day = ((today.getDate()).toString().length == 1 ? "0" + (today.getDate()) : (today.getDate()));
    let month = ((today.getMonth() + 1).toString().length == 1 ? "0" + (today.getMonth() + 1) : (today.getMonth() + 1));
    let hour = today.getHours().toString().length == 1 ? "0" + today.getHours() : today.getHours();
    let minute = today.getMinutes().toString().length == 1 ? "0" + today.getMinutes() : today.getMinutes();
    let page = 1;
    let date = today.getFullYear() + "-" + month + "-" + day;
    let time = hour + ":" + minute  
    
    
    
    
    
    
    
    
    
    
    return{
        $listdiv:$listdiv,
        $totalListtable:$totalListtable,
        $pagediv:$pagediv,
        date:date,
        time:time




    
    
    }
})()

//listdiv => vals.$listdiv 방식으로 모드 변경