function getAllCPInfo(title, page) {
    

        $.ajax({
            url: "/member/getCPInfo?title="+title +"&page=" +page,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
            }
         }); // end of todayList get ajax

}