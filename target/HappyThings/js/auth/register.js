$(document).ready(function () {

    var myForm = $('.my-form');
    myForm.form({
        on: 'blur',
        inline: true,
        fields: {
            username: {
                identifier: 'userName',
                rules: [
                    {
                        type: 'regExp[[\u4e00-\u9fa5a-zA-Z0-9_]{4,10}]',
                        prompt: '请输入4-10位的中文,英文,数字,_ 组成!'
                    }
                ]
            },
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
            },
            repeat_password: {
                identifier: 'repeatPassword',
                rules: [
                    {
                        type: 'match[userPassword]',
                        prompt: '两次输入的密码不相同!'
                    }
                ]
            }
        },
        onSuccess: function (e) {
            e.preventDefault();
            var username = myForm.form('get value', 'userName');
            var email = myForm.form('get value', 'userEmail');
            var password = myForm.form('get value', 'userPassword');
            var verifyCode = myForm.form('get value', 'verifyCode');
            ajaxValidator(username, email, password, verifyCode);
        }
    });

    function ajaxValidator(username, email, password, verifyCode) {
        layui.use(['layer'], function () {
            var layer = layui.layer;
            $.ajax({
                url: '/register.do',
                type: 'post',
                content: 'application/json',
                dataType: 'json',
                data: {
                    'userName': username,
                    'userEmail': email,
                    'userPassword': password,
                    'verifyCode': verifyCode
                },
                success: function (data) {
                    if (data.code === 200) {
                        layer.msg('注册成功!', {icon: 1});
                        window.location.href = 'login';
                        console.log("注册成功哦!");
                    } else {
                        console.log('注册失败');
                        var message = data.message;
                        if (message.verifyCode) {
                            myForm.form('add prompt', 'verifyCode', message.verifyCode);
                        } else if (message.userName) {
                            myForm.form('add prompt', 'userName', message.userName);
                        } else if (message.userEmail) {
                            myForm.form('add prompt', 'userEmail', message.userEmail);
                        } else if (message.userPassword) {
                            myForm.form('add prompt', 'userPassword', message.userPassword);
                        } else if (message.error) {
                            console.log('邮箱已存在');
                            myForm.form('add prompt', 'userEmail', message.error);
                        }
                    }
                }
                ,
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("错误!!: " + XMLHttpRequest.readyState + "  错误代码: " + XMLHttpRequest.status);
                }
            });
        });
    }

    $('.send-verify-code').on('click', function sendVerifyCode() {
        var time = 60;
        var sendBtn = $('.send-verify-code');
        var email = $('.register-userEmail').val();
        if (email === '' || email === null) {
            myForm.form('add prompt', 'userEmail', '请输入邮箱!');
            return;
        }
        var reg =/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if (!reg.test(email)) {
            myForm.form('add prompt', 'userEmail', '邮箱不正确!');
            return;
        }
        var loop = setInterval(function () {
            sendBtn.html(time);
            if (time === 0) {
                clearInterval(loop);
                sendBtn.html('获取验证码');
                sendBtn.removeClass('disabled');
            } else {
                sendBtn.addClass('disabled');
                time--;
            }
        }, 1000);
        layui.use('layer', function () {
            $.ajax({
                url: 'verifyCode.do',
                type: 'post',
                dataType: 'json',
                data: {'email': email},
                success: function (data) {
                    if (data.code === 200) {
                        layui.layer.msg('发送验证码成功!', {icon: 1});
                    } else {
                        layui.layer.msg('发送验证码失败!', {icon: 2});
                        console.log('发送验证码失败!');
                    }
                },
                error: function (XMLHttpRequest) {
                    console.log("错误!!: " + XMLHttpRequest.readyState + "  错误代码: " + XMLHttpRequest.status);
                }
            });
        });
    });
});