//반복코드
playAlert = setInterval(function () {
   var hour = new Date().getHours() < 10 ? "0" + new Date().getHours() : new Date().getHours()
   var Minutes = new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes()
   var seconds = new Date().getSeconds() < 10 ? "0" + new Date().getSeconds() : new Date().getSeconds();
   var now = hour + "" + Minutes
   var target = "#" + now;
   if ($(target).length == 1) {
      $(target)[0].play()
   }
}, 60000);


//리스트 뿌려주는 코드
function appendlist(list) {
   var str = ""
   var th = '<th>제목</th><th>재생시간</th><th>재생</th><th>&nbsp</th><th>수정/삭제</th><th>'
   list.content.forEach(list => {
      var time = list.starttime.substring(0, 5).replace(":", "")
      var wholePathOfFile = list.audioVO.audioPath.replace(/\\/gi, "-") + list.audioVO.audioName;
      str +=
         '<tr class="listTable">' +
         '<td style="padding-top: 40px; height:70px; width:10vw;">' + '<h4>' + list.title + '</h4>' + '</td>' +
         '<td style="padding-top: 40px;"">' + '<h4>' + list.starttime.substring(0, 5) + '</h4>' + '</td>' +
         '<td  style="padding-top: 40px;">' + '<a href="#">'
         + '<i class="far fa-play-circle playBtn" style="font-size:30px" data-time = "' + time + '"></a></i>' + '</td>' +
         '<td class="test" style="padding-top: 25px; padding-left: 5px; padding-right: 5px; height:70px;">' +
         '<audio style=" width: 35vw; min-width: 1cm; display:none;" id="audio' + time + '" controls class="playerInList" data-alarmBell="'
         + list.audioVO.alarmBell + '" source id="sourcelink" src="http://localhost:8080/rbcboard/' + wholePathOfFile + '"> </audio>' +
         '<img class="playImg"  src="../../img/mscustom/audioLine.jpg"  alt="" style=" width: 100vw; min-width: 1cm; height: 4vw;">' +
         '</td>' +
         '<td style=" padding-top: 25px;"" >' + '<p  style="padding-left: 5px; min-width: 2cm;" >수정</p>' +
         '<p  style="padding-left: 5px; min-width: 0.8cm;" >삭제</p>' + '</td>' +
         '</tr>'
   });

   vals.$listdiv.html(th)
   vals.$listdiv.append(str)

   $(".playerInList")[0].load();
}

vals.$listdiv.on('click', '.playBtn', function (e) {
   e.preventDefault();
   var btn = $(this)
   var target = $(this).attr('data-time');
   var audio = $("#audio" + target)

   var img = btn.closest("tr").children(".test").children(".playImg")
   var targetAlarm = $("#hiddenAlarm" + audio.attr("data-alarmBell"));

   if (btn.attr('class') === 'far fa-stop-circle playBtn') {
      targetAlarm[0].pause();
      targetAlarm[0].currentTime = 0;
      audio[0].pause();
      audio[0].currentTime = 0;
      btn.attr('class', 'far fa-play-circle playBtn')
      img.attr("src", "../../img/mscustom/audioLine.jpg")
      return;
   }

   img.attr("src", "../../img/mscustom/soundwave.gif")
   btn.attr('class', 'far fa-stop-circle playBtn')
   targetAlarm[0].play();

   targetAlarm[0].addEventListener("ended", function (e) {
      audio[0].play()
      this.removeEventListener("ended", arguments.callee);
      audio[0].addEventListener("ended", function (e) {
         btn.attr('class', 'far fa-play-circle playBtn')
         img.attr("src", "../../img/mscustom/audioLine.jpg")
         this.removeEventListener("ended", arguments.callee);
      });
   });
}) // end of playBtn event in list 

var json = {
   startdate: vals.date
}

function getTodayList() {
   $.ajax({
      url: "/rbcboard/todayList/" + vals.date,
      type: "GET",
      async : false,
      contentType: "application/json; charset=utf-8",
      success: function (result) {
         appendlist(result)
      }
   });
}// end of todayList get ajax