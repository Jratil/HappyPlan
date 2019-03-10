$(document).ready(function () {

    layui.use(['layer'], function () {
        $.fn.form.settings.rules.timeEmpty = function () {
            var planStartTime = $('.plan-start-time');
            return planStartTime.val() === null ||
                planStartTime.val() === '';
        };

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
                , startTime: {
                    identifier: 'planStartTime',
                    rules: [
                        {
                            type: 'empty',
                            prompt: '请选择开始时间'
                        }
                    ]
                }
                , endTime: {
                    identifier: 'planEndTime',
                    rules: [
                        {
                            type: 'empty',
                            prompt: '请选择结束时间'
                        }
                    ]
                }
            }
            , onSuccess: function (e) {
                e.preventDefault();
                var planName = myForm.form('get value', 'planName');
                var planStartTime = myForm.form('get value', 'planStartTime');
                var planEndTime = myForm.form('get value', 'planEndTime');
                // console.log(planName + "," + planStartTime + "," + planEndTime);
                // console.log(packageJson(planName, planStartTime, planEndTime));
                addPlan(planName, planStartTime, planEndTime);
            }

        });


        function addPlan(planName, planStartTime, planEndTime) {
            $.ajax({
                url: '/addPlan.do',
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: packageJson(planName, planStartTime, planEndTime),
                success: function (data) {
                    if (data.code === 200) {
                        layer.msg('添加计划成功', {icon: 1});
                        console.log('添加计划成功');
                        setTimeout(function () {
                            window.location.href = 'addplan';
                        }, 500);
                    } else {
                        var message = data.message;
                        if (message.planName) {
                            myForm.form('add prompt', 'planName', message.planName);
                        }
                        if (message.planStartTime) {
                            myForm.form('add prompt', 'planStartTime', message.planStartTime);
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
            });
        }
    });
    // function getPlanId() {
    // }
    function packageJson(planName, planStartTime, planEndTime) {
        var str = {
            'planName': planName, 'planStartTime': planStartTime,
            'planEndTime': planEndTime
        };
        console.log(JSON.stringify(str));
        return JSON.stringify(str);
    }
});