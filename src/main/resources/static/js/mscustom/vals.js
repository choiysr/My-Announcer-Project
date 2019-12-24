var vals = (function () {
    let $listdiv = $("#listtable");
    let $totalListtable = $("#totalListtable");
    let $pagediv = $("#pagediv")
    
    let today = new Date();
    let week = today.getDay();
    let day = ((today.getDate()).toString().length == 1 ? "0" + (today.getDate()) : (today.getDate()));
    let month = ((today.getMonth() + 1).toString().length == 1 ? "0" + (today.getMonth() + 1) : (today.getMonth() + 1));
    let hour = today.getHours().toString().length == 1 ? "0" + today.getHours() : today.getHours();
    let minute = today.getMinutes().toString().length == 1 ? "0" + today.getMinutes() : today.getMinutes();
    let date = today.getFullYear() + "-" + month + "-" + day;
    let time = hour + ":" + minute  
    let todayListTh = '<th>제목</th><th>방송시간</th><th>재생</th><th>&nbsp</th><th>수정/삭제</th><th>'
    let totalListTh = '<th>날짜</th><th>시간</th><th>제목</th><th>수정/삭제</th><th>'
    
    return{
        $listdiv:$listdiv,
        $totalListtable:$totalListtable,
        $pagediv:$pagediv,
        date:date,
        time:time,
        todayListTh:todayListTh,
        totalListTh:totalListTh,
        week:week
    }
})()

//listdiv => vals.$listdiv 방식으로 모드 변경