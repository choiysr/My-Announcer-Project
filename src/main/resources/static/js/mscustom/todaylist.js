//반복코드
playAlert = setInterval(function () {
   var cT = new Date()
   var hour = cT.getHours() < 10 ? "0" + cT.getHours() : cT.getHours()
   var Minutes = cT.getMinutes() < 10 ? "0" + cT.getMinutes() : cT.getMinutes()
   var now = hour + "" + Minutes
   var target = $("#icon" + now);
   console.log(now);
   
   if ($(target).length == 1) {
      target.click();
   }
}, 60000);


//리스트 뿌려주는 코드
function appendlist(list) {
   var str = ""
   list.content.forEach(list => {
      var time = list.starttime.substring(0, 5).replace(":", "")
      var wholePathOfFile = list.audioVO.audioPath.replace(/\\/gi, "-") + list.audioVO.audioName;
      var wholePathOfIntroFile = list.audioVO.audioPath.replace(/\\/gi, "-") + list.audioVO.intro;
      var wholePathOfedningFile = list.audioVO.audioPath.replace(/\\/gi, "-") + list.audioVO.ending;
      //현재시간 >  지정시간 == 색변경
      if (((vals.time).replace(":", "")) > time) {
         str += '<tr style="background-color: #EEEEEE" class="listTable">'
      } else {
         str += '<tr class="listTable">'
      }

      str +=
         '<td style="padding-top: 40px; height:70px; width:10vw;">' + '<a class="listTitle" href="#" id="' + list.bno + '"><h4>' + list.title + '</h4></a>' + '</td>' +
         '<td style="padding-top: 40px;"">' + '<h4>' + list.starttime.substring(0, 5) + '</h4>' + '</td>' +
         '<td  style="padding-top: 40px;">' + '<a  href="#"><i id="icon' + time + '" class="far fa-play-circle playBtn" style="font-size:30px" data-time = "' + time + '"></a></i>' + '</td>' +
         '<td class="test" style="padding-top: 25px; padding-left: 5px; padding-right: 5px; height:70px;">' +
         '<img class="playImg"  src="../../img/mscustom/audioLine.jpg"  alt="" style=" width: 100vw; min-width: 1cm; height: 4vw;">'

      if (list.audioVO.intro.length !== 0) {
         str += '<audio style=" width: 35vw; min-width: 1cm; display:none;" " id="audio' + time + '" controls class="playerInList" data-alarmBell="' + list.audioVO.alarmBell + '">' +
            '<source src="http://localhost:8080/rbcboard/' + wholePathOfIntroFile + '" ></source>' +
            '</audio>'
      }

      str += '<audio style=" width: 35vw; min-width: 1cm; display:none;" id="audio' + time + '" controls class="playerInList" data-alarmBell="' + list.audioVO.alarmBell + '"' +
         '<source src="http://localhost:8080/rbcboard/' + wholePathOfFile + '" ></source>' +
         '</audio>'

      if (list.audioVO.ending.length !== 0) {
         str += '<audio style=" width: 35vw; min-width: 1cm; display:none;" id="audio' + time + '" controls class="playerInList" data-alarmBell="' + list.audioVO.alarmBell + '"' +
            '<source src="http://localhost:8080/rbcboard/' + wholePathOfedningFile + '" ></source>' +
            '</audio>'
      }

      str += '</td>' +
         '<td style=" padding-top: 25px;"" >' + '<a href="#"><p class="modifyBtnOutside" style="padding-left: 5px; min-width: 2cm;" >수정</p></a>' +
         '<a href="#"><p class="deleteBtnOutside" style="padding-left: 5px; min-width: 0.8cm;" >삭제</p></a>' + '</td>' +
         '</tr>'
   });

   vals.$listdiv.html(vals.todayListTh)
   vals.$listdiv.append(str)

   $(".playerInList")[0].load();
}

// 리스트내 수정 버튼 function 통합할것.(모달창 내부 버튼과) 
vals.$listdiv.on("click",'.modifyBtnOutside', function (e) { 
   $(this).parent().parent().parent().find(".listTitle h4").click();
});



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
      targetAlarm[0].removeEventListener("ended", arguments.callee)

      audio[0].pause();
      audio[0].currentTime = 0;
      audio[0].removeEventListener("ended", arguments.callee);
      
      for (let index = 1; index < audio.siblings().length; index++) {
         audio.siblings()[index].pause();
         audio.siblings()[index].currentTime = 0;
         audio.siblings()[index].removeEventListener("ended", arguments.callee)
      }

      btn.attr('class', 'far fa-play-circle playBtn')
      img.attr("src", "../../img/mscustom/audioLine.jpg")

      return;
   } else {
      img.attr("src", "../../img/mscustom/soundwave.gif")
      btn.attr('class', 'far fa-stop-circle playBtn')

      targetAlarm[0].play();


      targetAlarm[0].addEventListener("ended", function (e) {


         audio[0].play();
         this.removeEventListener("ended", arguments.callee);


         audio[0].addEventListener("ended", function (e) {

            if (!audio.siblings()[1]) {
               this.removeEventListener("ended", arguments.callee);
               btn.attr('class', 'far fa-play-circle playBtn')
               img.attr("src", "../../img/mscustom/audioLine.jpg")
               return
            }


            audio.siblings()[1].play()
            this.removeEventListener("ended", arguments.callee);

            audio.siblings()[1].addEventListener("ended", function (e) {

               if (!audio.siblings()[2]) {
                  this.removeEventListener("ended", arguments.callee);
                  btn.attr('class', 'far fa-play-circle playBtn')
                  img.attr("src", "../../img/mscustom/audioLine.jpg")
                  return
               }


               audio.siblings()[2].play()
               this.removeEventListener("ended", arguments.callee);

               audio.siblings()[2].addEventListener("ended", function (e) {
                  btn.attr('class', 'far fa-play-circle playBtn')
                  img.attr("src", "../../img/mscustom/audioLine.jpg")
                  this.removeEventListener("ended", arguments.callee);
               });
            });


         });

      });

   }

   //만약 시간이 지났거나 같으면 tr의 style을 변경
   var cT = new Date()
   var hour = cT.getHours() < 10 ? "0" + cT.getHours() : cT.getHours()
   var Minutes = cT.getMinutes() < 10 ? "0" + cT.getMinutes() : cT.getMinutes()
   var now = hour + "" + Minutes
   //현재시간  >= 지정시간 =>색 변경

   if (now >= target) {
      $(this).parents("tr").css("background-color", "#EEEEEE")
   }
}) // end of playBtn event in list 

// 제목클릭하면 모달로 조회창 띄우기
vals.$listdiv.on('click', '.listTitle', function (e) {
   $('div.readModal').modal();
   var targetBno = this.id;
   $.ajax({
      url: "/rbcboard/read/" + targetBno,
      type: "GET",
      contentType: "application/json; charset=utf-8",
      success: function (result) {
         $("#RUDbno").val(result.bno);
         $("#RUDtitle").val(result.title);
         $("#RUDcontent").val(result.content);
         $("#RUDymdSet").val(result.startdate);
         $("#RUDtimeSet").val(result.starttime);
         $("#RUDvoiceGender").val(result.gender);
         $("#RUDalarmBell").val(result.audioVO.alarmBell);

         var tmpStr;
         if (result.audioVO.intro !== "") {
            tmpStr = result.audioVO.intro;
            $("#RUDintroFileName").val(tmpStr.substring(tmpStr.lastIndexOf('_')+1, tmpStr.length));
            $("#RUDoriginalIntro").val(result.audioVO.audioPath.replace(/\\/gi, "-") + result.audioVO.intro);
         }
         if (result.audioVO.ending !== "") {
            tmpStr = result.audioVO.ending;
            $("#RUDendingFileName").val(tmpStr.substring(tmpStr.lastIndexOf('_')+1, tmpStr.length));
            $("#RUDoriginalEnding").val(result.audioVO.audioPath.replace(/\\/gi, "-") + result.audioVO.ending);
         }

         // 반복설정 되어있는 방송일때 
         if(result.repeatVO !== null) {
            var $orgRepeatText = $("#RUDrepeat");
            var $ymdSet = $("#RUDymdSet");
            $orgRepeatText.val(result.repeatVO.repeatWeek == null ? result.repeatVO.repeatMonth : result.repeatVO.repeatWeek);
            $("#RUDrepeatView").val(getRepeatViewText($orgRepeatText.val()));
            $ymdSet.attr("placeholder", "반복설정된 방송입니다.");
            $ymdSet.attr("disabled",true);
         }
      }
   })
});

function getTodayList() {
   $.ajax({
      url: "/rbcboard/todayList/" + vals.date + "/" + vals.week,
      type: "GET",
      contentType: "application/json; charset=utf-8",
      success: function (result) {
         appendlist(result)
      }
   }); // end of todayList get ajax
}

