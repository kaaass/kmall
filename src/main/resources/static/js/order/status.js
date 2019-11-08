/**
 * 订单状态检查页面
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/order',
        'bootstrap'],
    function ($, functions, constants, order, _) {

        let requestId = null;
        let created = false;

        // 解析Url
        if (functions.requestParams.has(constants.PARAM_ID)) {
            requestId = functions.requestParams.get(constants.PARAM_ID);
        } else {
            functions.modal("错误", "缺少请求参数！");
            return;
        }

        // 轮询情况
        setInterval(() => {
            // 检查是否已经创建
            if (created)
                return;
            // 请求
            order.check(requestId)
                .then(orderId => {
                    if (orderId !== null) {
                        if (orderId !== 'error') {
                            // 请求到订单
                            functions.modal("信息", "下单成功！正在跳转支付页面...");
                            functions.jumpTo(`pay.html?id=${orderId}`);
                        }
                        created = true;
                    }
                });
        }, 500);
    });