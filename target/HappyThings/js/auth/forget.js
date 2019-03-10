$(document).ready(function () {

    var myForm = $('.my-form');
    myForm.form({
        on: 'blur',
        inline: true,
        fields: {
            email: {
                identifier: 'email',
                rules: [
                    {
                        type: 'email',
                        prompt: '请输入正确的邮箱!'
                    }
                ]
            },
            password: {
                identifier: 'password',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请输入密码!'
                    }
                ]
            },
            repeat_password: {
                identifier: 'repeat-password',
                rules: [
                    {
                        type: 'match[password]',
                        prompt: '两次输入的密码不相同!'
                    }
                ]
            },
            verify_code: {
                identifier: 'verify-code',
                rules: [
                    {
                        type: 'empty',
                        prompt: '请输入验证码!'
                    }
                ]
            }
        },
        onSuccess: function (e) {
            e.preventDefault();
            var email = myForm.form('get value', 'email');
            var password = myForm.form('get value', 'password');
            var verifyCode = myForm.form('get value', 'verify-code');
            ajaxValidator(email, password, verifyCode);
        }
    });

    function ajaxValidator(email, password, verifyCode) {
        $.ajax({
            url: '/forget.do',
            type: 'post',
            dataType: 'json',
            data: {
                'userEmail': email,
                'userPassword': password,
                'verifyCode': verifyCode
            },
            success: function (data) {
                if (data.code === 200) {
                    console.log("修改成功哦!");
                } else {
                    console.log('修改失败');
                    var message = data.message;
                    if (message.verifyCode) {
                        myForm.form('add prompt', 'verify-code', message.verifyCode);
                    } else if (message.userEmail) {
                        myForm.form('add prompt', 'email', message.userEmail);
                    } else if (message.userPassword) {
                        myForm.form('add prompt', 'password', message.userPassword);
                    } else if (message.error) {
                        myForm.form('add prompt', 'email', message.error);
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("错误!!: " + XMLHttpRequest.readyState + "  错误代码: " + XMLHttpRequest.status);
            }
        })
    }

    $('.send-verify-code').on('click', function sendVerifyCode() {
        var time = 60;
        var sendBtn = $('.send-verify-code');
        var email = $('.forget-email').val();
        if (email === '' || email === null) {
            myForm.form('add prompt', 'email', '请输入邮箱!');
            return;
        }
        var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        if (!pattern.test(email)) {
            myForm.form('add prompt', 'email', '请输入正确的邮箱哦!');
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
                type: 'get',
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
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("错误!!: " + XMLHttpRequest.readyState + "  错误代码: " + XMLHttpRequest.status);
                }
            });
        });
    });


});