$.ajax({
    url: '/getUserId.do',
    type:'post',
    dataType: 'json',
    async: false,
    success: function (data) {
        if (data.status === '404') {
            console.log("也要返回");
            window.location.href = 'login';
        }
    },
    error: function () {
        console.log("返回");
        window.location.href = 'login';
    }
});