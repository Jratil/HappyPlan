$(document).ready(function () {
    $('.logout').on('click', function() {
        $.ajax({
            url: '/logout.do',
            type: 'post',
            async: false,
            success: function () {
                console.log('注销成功');
                window.location.href = 'login';
            },
            error: function (XMLHttpRequest) {
                console.log(XMLHttpRequest.readyState + '状态: ' + XMLHttpRequest.status);
            }
        });
    });
});