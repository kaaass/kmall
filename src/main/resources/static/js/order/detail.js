/**
 * 订单详细页面
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'module/order',
        'bootstrap'],
    function ($, functions, constants, auth, order, _) {

        const TEMPLATE_DETAIL = "order_detail";

        let $detail = $('main');
        let requestParams = new URL(document.location.href).searchParams;
        let curOrderId;

        // 解析Url
        if (requestParams.has(constants.PARAM_ID)) {
            curOrderId = requestParams.get(constants.PARAM_ID);
        } else {
            functions.modal("错误", "缺少订单id！");
            return;
        }

        // 获取物品详细信息
        order.getOrder(curOrderId)
            .then(async (orderData) => {
                // 处理返回
                return (await order.processData([orderData]))[0];
            })
            .then(orderData => {
                // 渲染
                console.log(orderData);
                functions.renderHbs($detail, TEMPLATE_DETAIL, orderData);
            });
    });