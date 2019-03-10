$(document).ready(function () {

    var myForm = $('.my-form');
    myForm.form({
        on: 'blur',
        inline: true,
        fields: {
            email: {
                identifier: 'userEmail',
                rules: [
                    {
                        type: 'email',
                        prompt: '请输入正确的邮箱!'
                    }
                ]
            },
            password: {
                identifier: 'userPassword',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请输入密码!'
                    }
                ]
            }
        },
        onSuccess: function (e) {
            // console.log(myForm.form('get values'));
            e.preventDefault();
            var email = myForm.form('get value', 'userEmail');
            var password = myForm.form('get value', 'userPassword');
            ajaxValidator(email, password);
        }
    });

    function ajaxValidator(email, password) {
        layui.use(['layer'], function () {
            var layer = layui.layer;
            $.ajax({
                url: '/login.do',
                type: 'post',
                dataType: 'json',
                data: {
                    'userEmail': email,
                    'userPassword': password
                },
                success: function (data) {
                    if (data.code === 200) {
                        console.log("成功啦啦啦啦!");
                        layer.msg('登录成功', {icon: 1});
                        setTimeout(function () {
                            layer.open({
                                type: 3
                            });
                            window.location.href = '/plan';
                        }, 1000);
                    } else if(data.code === 400){
                        var message = data.message;
                        if (message.userEmail) {
                            myForm.form('add prompt', 'userEmail', message.userEmail);
                        } else if (message.userPassword) {
                            myForm.form('add prompt', 'userPassword', message.userPassword);
                            console.log("fuck");
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.msg('出错!!!', {icon: 2});
                    console.log("错误!!: " + XMLHttpRequest.readyState + "  错误代码: " + XMLHttpRequest.status);
                }
            });
        });
    }
});