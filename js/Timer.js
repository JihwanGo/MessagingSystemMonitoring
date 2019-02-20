window.setInterval(getTimer, 1000);
function getTimer() {

    var NT_date = new Date();
    var nt_year = NT_date.getFullYear();
    <!-- 로컬 현재 연도 가져옴 -->
    var nt_month = NT_date.getMonth() + 1;
    var nt_day = NT_date.getDate();
    var nt_hour = NT_date.getHours();
    var nt_min = NT_date.getMinutes();
    var nt_sec = NT_date.getSeconds();
    var time_str = nt_year + "년" + nt_month + "월" + nt_day + "일";
    $('#time').text(time_str);

}
window.setInterval(getMonth, 1000);
function getMonth() {

    var NT_date = new Date();
    var nt_year = NT_date.getFullYear();
    <!-- 로컬 현재 연도 가져옴 -->
    var nt_month = NT_date.getMonth() + 1;
    var nt_day = NT_date.getDate();
    var nt_hour = NT_date.getHours();
    var nt_min = NT_date.getMinutes();
    var nt_sec = NT_date.getSeconds();
    var time_str = nt_year + "년" + nt_month + "월";
    $('#timemonth').text(time_str);

}