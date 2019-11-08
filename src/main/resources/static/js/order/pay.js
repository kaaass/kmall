/**
 * 订单支付页面
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/order',
        'bootstrap'],
    function ($, functions, constants, order, _) {

        const TEMPLATE_PAY_DETAIL = 'pay_detail';

        let $detail = $('#pay-detail');
        let orderId = null;

        // 解析Url
        if (functions.requestParams.has(constants.PARAM_ID)) {
            orderId = functions.requestParams.get(constants.PARAM_ID);
        } else {
            functions.modal("错误", "缺少请求参数！");
            return;
        }

        // 查询订单数据并渲染
        order.getOrder(orderId)
            .then(order => {
                // 检查支付状态
                if (order.type !== 'CREATED') {
                    functions.modal('错误', '该订单已完成支付或已被取消！');
                    functions.jumpTo('/my/order.html', 3000); // 返回订单浏览
                    return;
                }
                functions.renderHbs($detail, TEMPLATE_PAY_DETAIL, order);
        });

        // 绑定付款事件
        $('#btn-pay').click(() => {
            alert("跳转外部支付网站并支付...");
            order.payOrder(orderId)
                .then((result) => {
                    if (result != null)
                        functions.modal('信息', '支付成功！');
                    functions.jumpTo('/my/order.html', 3000); // 返回订单浏览
                });
        });
    });