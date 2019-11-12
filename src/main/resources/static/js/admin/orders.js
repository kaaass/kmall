/**
 * 后台订单
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/order',
    'bootstrap'], function ($, functions, constants, auth, order, _) {

    let request = auth.getAxiosInstance(true);

    // 加载图标
    feather.replace();

    const TEMPLATE_ORDER_LIST = "admin_order_list";

    let $orderList = $('.table-responsive');
    let curTypeId;

    let requestParams = new URL(document.location.href).searchParams;

    // 分析url参数
    if (requestParams.has(constants.PARAM_TYPE)) {
        curTypeId = requestParams.get(constants.PARAM_TYPE);
        if (curTypeId === 'all') {
            curTypeId = null;
        } else {
            $('#orderTypeDropdown').text($(`[order-type=${curTypeId}]`).text());
        }
    } else {
        curTypeId = null;
    }

    // 加载并渲染订单
    let renderPromise;
    if (curTypeId != null) {
        // 对应类型
        renderPromise = order.renderAdminOrdersByUrl(`/order/admin/type/${curTypeId}/`, $orderList, TEMPLATE_ORDER_LIST);
    } else {
        // 全部订单
        renderPromise = order.renderAdminOrdersByUrl('/order/admin/', $orderList, TEMPLATE_ORDER_LIST);
    }
});