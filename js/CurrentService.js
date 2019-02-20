$(document).ready(function () {
    window.setInterval(getThread, 10000);
});
function getThread () {
    $('#table').empty();
    $('#tableservice').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/status/",
        success: function (condition) {
            let con = JSON.parse(condition);
            for (let i = 0; i < con.thr.length; i++) {

                    let sa = con.thr[i].service + ":" + con.thr[i].status;
                    let time = con.thr[i].lasttime;
                    let dif = con.thr[i].difference;
                    $('#table').append("<tr><td>" + sa + "</td><td>" + time + "</td><td>" + dif + "</td></tr>");
                /////////////////////////////////


                if (con.thr[i].service == "SMS1" || con.thr[i].service == "MMS1" || con.thr[i].service == "KAKAO1" || con.thr[i].service == "GMS1") {
                    let service = con.thr[i].service;
                    $('#tableservice').append('<tr><td class="text-info">' + service + '</td></tr>');
                }
                if (con.thr[i].service == "SMS2" || con.thr[i].service == "MMS2" || con.thr[i].service == "KAKAO2" || con.thr[i].service == "GMS2") {
                    let service = con.thr[i].service;
                    $('#tableservice').append('<tr><td class="text-info">' + service + '</td></tr>');
                }
                if (con.thr[i].service == "SMS3" || con.thr[i].service == "MMS3" || con.thr[i].service == "KAKAO3" || con.thr[i].service == "GMS3") {
                    let service = con.thr[i].service;
                    $('#tableservice').append('<tr><td class="text-info">' + service + '</td></tr>');
                }
                if (con.thr[i].service == "SMS4" || con.thr[i].service == "MMS4" || con.thr[i].service == "KAKAO4" || con.thr[i].service == "GMS4") {
                    let service = con.thr[i].service;
                    $('#tableservice').append('<tr><td class="text-info">' + service + '</td></tr>');
                }
                if (con.thr[i].service == "SMS5" || con.thr[i].service == "MMS5" || con.thr[i].service == "KAKAO5" || con.thr[i].service == "GMS5") {
                    let service = con.thr[i].service;
                    $('#tableservice').append('<tr><td class="text-info">' + service + '</td></tr>');
                }

                ///////////////////////////////////
            }
        },
        error: function (condition) {
            if (condition.length == 0) {
                alert("데이터를 가져오는데 오류가 발생하였습니다.");
            }
        }
    })
}