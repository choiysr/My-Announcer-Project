 ///////totlaList code start
 
 function appendTotalList(list) {
    var str = ""
    list.data.forEach(list => {
       var time = list.starttime.substring(0, 5).replace(":", "")
       str +=
          '<tr>' 

          if (list.repeatVO !== null) {
             if (list.repeatVO.repeatWeek !== null ) {
               str+= '<td style="height:70px; padding-top: 25px;" >' + '<h4>매주 ' + list.repeatVO.repeatWeek + '반복</h4>' + '</td>' 
                
             }else{
               str+= '<td style="height:70px; padding-top: 25px;" >' + '<h4>매월 ' + list.repeatVO.repeatMonth + '일 반복</h4>' + '</td>' 
             }
          }else{
            str+= '<td style="height:70px; padding-top: 25px;" >' + '<h4>' + list.startdate + '</h4>' + '</td>' 
          }
          str+= '<td style="height:70px; padding-top: 25px;" >' + '<h4>' + list.starttime + '</h4>' + '</td>' +
          '<td style="height:70px; padding-top: 25px;" >' + '<h4>' + list.title +     '</h4>' + '</td>' +
          '<td style="height:70px; padding-top: 25px;">' + '<p  style="padding-left: 5px; display: inline-block;s min-width: 0.8cm;" >수정/</P>' +
          '<p  style=" padding-left: 5px; display: inline-block; min-width: 0.8cm;" >삭제</P>' +
          '</td>' +
          +'</tr>'
    });

    var page = ''
    page += "<ul >"
    page +=
       '<li>' + '<a href="#" onclick="loadpage(' + 1 + ',\'' + list.category + '\',\'' + list.search + '\',event)" > <i class="fas fa-less-than"> </a></i></li>'
    for (let start = list.start; start <= list.end; start++) {
       if (start == list.now) {
          page +=
             '<li class="active">' + '<a href="#" onclick="loadpage(' + start + ',\'' + list.category + '\',' + list.search + ' ,event)" >' + start + '</a></li>'
       } else {
          page +=
             '<li>' + '<a href="#" onclick="loadpage(' + start + ',\'' + list.category + '\',\'' + list.search + '\',event)">' + start + '</a></li>'
       }
    }
    page +=
       '<li>' + '<a href="#" onclick="loadpage(' + list.totalpages + ',\'' + list.category + '\',\'' + list.search + '\',event)" > <i class="fas fa-greater-than"> </a></i></li>'
    page += "</ul>"
    vals.$totalListtable.html(vals.totalListTh)
    vals.$totalListtable.append(str)
    vals.$pagediv.html(page)
 } // end of append totallist 

 //totalList에 대한 페이지 로딩 ajax
 function loadpage(page, category, search, event) {

   if (event) {
      event.preventDefault();
   }

   $.ajax({
      url: "/rbcboard/totalList?page=" + page + "&category=" + category + "&search=" + search,
      type: "GET",
      contentType: "application/json; charset=utf-8",
      success: function (result) {
         appendTotalList(result)
      }
   }); // end of ajax 
} // end of loadpage function 


//검색 버튼 누를때
$("#searchbtn").on("click", function (e) {
   e.preventDefault();
   var cate = $("#category").val();
   if (cate == "title") {
      loadpage(1, cate, $("#searchText").val())
   } else if (cate == "year-month") {
      loadpage(1, cate, $("#searchMonth").val())
   } else if (cate == "year-month-day") {
      loadpage(1, cate, $("#searchDate").val())
   }

}); // searchBtn click event 

$("#category").on("change", function (params) {
   $(this).parent("td").siblings("td").first().children('div > input').css("display", "none");
   var cate = $(this).val()
   if (cate == "title") {
      $("#searchText").css("display", "inline-block")
   } else if (cate == "year-month") {
      $("#searchMonth").css("display", "inline-block")
   } else {
      $("#searchDate").css("display", "inline-block")
   }
})