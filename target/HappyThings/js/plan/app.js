$(document).ready(function () {
    layui.use(['element', 'layer', 'laydate'], function () {
        var layer = layui.layer;
        var laydate = layui.laydate;

        $.ajaxSetup({
            complete: function (XMLHttpRequest, textStatus) {
                console.log('开始检验....');
                var sessionStatus = XMLHttpRequest.getResponseHeader('sessionStatus');
                if (sessionStatus === 'timeout') {
                    console.log('没有登录哦!');
                    // alert('没登陆哦');
                    window.location.href = 'login';
                }
            }
        });

        getPlans(getUserId());

        function getPlans(userId) {
            var timeline = $('.layui-timeline');
            $.ajax({
                url: '/getAllPlan.do',
                type: 'get',
                dataType: 'json',
                data: {'userId': userId},
                success: function (data) {
                    console.log(data);
                    //获取返回数据中的列表list
                    var plans = data.data.list;
                    console.log(plans);

                    for (var i = 0; i < plans.length; i++) {
                        console.log(plans[i]);
                        timeline.append(
                            "<li class=\"layui-timeline-item\" data-i =" + i + " tabindex=\"0\">" +
                            "    <i class=\"layui-icon layui-icon-home layui-timeline-axis\"></i>" +
                            "    <div class=\"plans-timeline layui-timeline-content layui-text\">" +
                            "        <h3 class=\"layui-timeline-title\">" + plans[i].planName + "</h3>" +
                            "        <div class=\"plan-short-time\">\n" +
                            "         " + timetrans(plans[i].planStartTime) +
                            "           ===<i class=\"layui-icon layui-icon-next\"></i> " +
                            "         " + timetrans(plans[i].planEndTime) +
                            "        </div>" +
                            "        <div class=\"plan-clear-time\">" +
                            "            <span>开始时间:&nbsp;</span>" +
                            "            <span>" + timetrans(plans[i].planStartTime) + "</span>" +
                            "            <br>" +
                            "            <span>结束时间:&nbsp;</span>" +
                            "            <span>" + timetrans(plans[i].planEndTime) + "</span>" +
                            "            <br><hr class='layui-bg-cyan'>" +
                            "            <span>总时间: " + plans[i].planDuration + "天</span>" +
                            "            <hr class='layui-bg-cyan'>" +
                            "            <div class=\"plan-operate\">" +
                            "                 <button class=\"ui mini brown button plan-edit\"><i class='write icon'></i>编辑</button>" +
                            "                 <button class=\"ui mini pink button plan-delete\"><i class='remove icon'></i> 删除</button>" +
                            "            </div>" +
                            "        </div>" +
                            "    </div>" +
                            "</li>"
                        );
                    }

                    //编辑
                    $('.plan-edit').on('click', function (obj) {
                        //获取点击得按钮对象
                        var timeline = $(obj.currentTarget).parents('.layui-timeline-item');
                        //获取点击得数据
                        var plan = plans[timeline.data('i')];
                        console.log("下面是打印的点击的PLan=======>");
                        console.log(plan);
                        $.ajax({
                            url: '/setPlanId.do',
                            type: 'post',
                            dataType: 'json',
                            data: {'planId': plan.planId},
                            success: function (data) {
                                if (data)
                                    window.location.href = 'editplan';
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                layer.msg('发生错误!', {icon: 2});
                                console.log(jqXHR);
                                console.log(textStatus);
                                console.log(errorThrown);
                            }

                        });
                    });

                    //删除
                    $('.plan-delete').on('click', function (obj) {
                        deletePlan(datas, obj, layer);
                    });

                    timeline.append(
                        "<li class=\"layui-timeline-item\">\n" +
                        "    <i class=\"layui-icon layui-icon-date layui-timeline-axis\"></i>" +
                        "    <div class=\"layui-timeline-content\">" +
                        "        <h4>没有更多了哦</h4>" +
                        "    </div>" +
                        "</li>"
                    );
                    showHide();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    layer.msg('发生错误!', {icon: 2});
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);

                    timeline.append(
                        "<li class=\"layui-timeline-item\">\n" +
                        "    <i class=\"layui-icon layui-icon-date layui-timeline-axis\"></i>" +
                        "    <div class=\"layui-timeline-content\">" +
                        "        <h4>没有更多了哦</h4>" +
                        "    </div>" +
                        "</li>"
                    );
                }
            });
        }

        function showHide() {

            //获取焦点
            $('.layui-timeline-item').on('click', function (obj) {

                var focusNode = $(obj.currentTarget);
                //console.log(focusNode);
                //获取要隐藏的节点
                var shortTime = focusNode.find('.plan-short-time');
                //获取要显示的节点
                var clearTime = focusNode.find('.plan-clear-time');

                //判断是否已经点击开
                shortTime.slideToggle('slow');
                clearTime.slideToggle('slow');
            });
            //失去焦点
            $('.layui-timeline-item').on('blur', function (obj) {

                var focusNode = $(obj.currentTarget);
                //获取要隐藏的节点
                var shortTime = focusNode.find('.plan-short-time');
                //获取要显示的节点
                var clearTime = focusNode.find('.plan-clear-time');

                shortTime.slideDown('slow');
                clearTime.slideUp('slow');
            });
        }

        function deletePlan(data, obj, layer) {

            layer.confirm('真的要删除吗?', function (index) {
                layer.close(index);
                //获取点击得按钮对象
                var timeline = $(obj.currentTarget).parents('.layui-timeline-item');
                var planId = data[timeline.data('i')].planId;
                console.log(planId);

                $.ajax({
                    url: '/deletePlan.do',
                    type: 'get',
                    dataType: 'json',
                    data: {'planId': planId},
                    success: function (data) {
                        if (data) {
                            layer.msg('删除成功!', {icon: 1});
                            setTimeout(function () {
                                layer.open({
                                    type: 3
                                });
                                setTimeout(function () {
                                    window.location.href = 'plan';
                                }, 1000);
                            }, 500);
                        } else {
                            layer.msg('删除失败!', {icon: 2});
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        layer.msg('发生错误!', {icon: 2});
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                    }
                });
            });
        }

        /**
         * 将编辑的字段转换为Json
         * @param planId
         * @param planName
         * @param startTime
         * @param endTime
         * @returns {string}
         */
        function packageJson(planId, planName, startTime, endTime) {

            var str = {
                'planId': planId, 'planName': planName,
                'planStartTime': startTime, 'planEndTime': endTime
            };

            var strJson = JSON.stringify(str);
            return strJson;
        }

        /**
         * 将后端返回的时间戳转换为时间格式: yyyy-MM-dd HH:mm:ss
         * @param date
         * @returns {string}
         */
        function timetrans(date) {
            var date = new Date(date);
            var Y = date.getFullYear() + '-';
            var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
            var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
            // var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
            // var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
            // var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
            return Y + M + D;
        }

        /**
         * 将时间转换为时间戳
         * @param date
         * @returns {number}
         */
        function toDateUnix(date) {
            var time = new Date(date.replace(/-/g, '/'));
            return Date.parse(time);
        }

    });

    function getUserId() {
        var userId = null;
        $.ajax({
            url: '/getUserId.do',
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (data) {
                userId = data;
                console.log(userId);
            },
            error: function (data, jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }
        });
        return userId;
    }
});