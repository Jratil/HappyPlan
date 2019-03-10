$(document).ready(function () {
    getPlanId();
    layui.use(['layer'], function () {
        var layer = layui.layer;
        var myForm = $('.my-form');
        myForm.form({
            on: 'blur',
            inline: true,
            fields: {
                planName: {
                    identifier: 'planName',
                    rules: [
                        {
                            type: 'minLength[1]',
                            prompt: '计划名字至少1个字符'
                        },
                        {
                            type: 'maxLength[15]',
                            prompt: '计划名字最多15个字符'
                        }
                    ]
                }
            },
            onSuccess: function (e) {
                e.preventDefault();
                var planName = myForm.form('get value', 'planName');
                var planStartTime = myForm.form('get value', 'planStartTime');
                var planEndTime = myForm.form('get value', 'planEndTime');
                // console.log(planName + "," + planStartTime + "," + planEndTime);
                // console.log(packageJson(planName, planStartTime, planEndTime));
                editPlan(planName, planStartTime, planEndTime);
            }
        });

        function editPlan(planName, planStartTime, planEndTime) {
            $.ajax({
                url: '/editPlan.do',
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: packageJson(planName, planStartTime, planEndTime),
                success: function (data) {
                    if (data.code === 200) {
                        layer.msg('修改计划成功', {icon: 1});
                        console.log('修改计划成功');
                        setTimeout(function () {
                            window.location.href = 'plan';
                        }, 500);
                    } else {
                        var message = data.message;
                        if(message.planName) {
                            myForm.form('add prompt', 'planName', message.planName);
                        }
                        if (message.planEndTime) {
                            myForm.form('add prompt', 'planEndTime', message.planEndTime);
                        }
                        if (message.error) {
                            layer.msg('添加计划失败!', {icon: 2});
                        }
                    }
                },
                error: function (jqXHR, textStatus) {
                    layer.msg('发生错误!', {icon: 2});
                    console.log('出错!!');
                    console.log(jqXHR);
                    console.log(textStatus);
                }
            })
        }
    });
    function getPlanId() {
        $.ajax({
            url: '/getPlanId.do',
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if(!data) {
                    console.log('不要乱搞,兄弟!!');
                    window.location.href = 'plan';
                }
            },
            error: function (jqXHR, textStatus) {
                layer.msg('发生错误!', {icon: 2});
                console.log('出错!!');
                console.log(jqXHR);
                console.log(textStatus);
            }
        });
    }
    function packageJson(planName, planStartTime, planEndTime) {
        var str = {
            'planName': planName, 'planStartTime': planStartTime,
            'planEndTime': planEndTime
        };
        return JSON.stringify(str);
    }
});