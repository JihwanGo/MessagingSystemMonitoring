window.setInterval(getJVM, 1000);
    function getJVM() {
        $.ajax({
            //dataType: 'json',
            type: "GET",
            url: "http://localhost:8080/memory/",
            //data: {},
            success: function (mem) {

                let jvm = JSON.parse(mem);
                $('#total').text('Total : ' + jvm.total + 'MB');
                $('#free').text('Free : ' + jvm.free + 'MB');
                $('#max').text('Max : ' + jvm.max + 'MB');
            },
            error: function (mem) {

                if (mem.length == null) {
                    alert('JVM 데이터 가져오는데 오류가 발생하였습니다');
                }
            }
        });
    }