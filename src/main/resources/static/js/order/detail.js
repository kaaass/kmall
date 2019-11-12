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
        let curOrderId;

        // 解析Url
        if (functions.requestParams.has(constants.PARAM_ID)) {
            curOrderId = functions.requestParams.get(constants.PARAM_ID);
        } else {
            functions.modal("错误", "缺少订单id！");
            return;
        }

        // 判断Admin登录
        let storage = window.localStorage;
        let admin = storage.getItem(constants.KEY_ADMIN_AUTH) != null;

        // 获取物品详细信息
        order.getOrder(curOrderId, admin)
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