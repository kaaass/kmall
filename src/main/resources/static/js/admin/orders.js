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
    let render = () => {
        let renderPromise;
        if (curTypeId != null) {
            // 对应类型
            renderPromise = order.renderAdminOrdersByUrl(`/order/admin/type/${curTypeId}/`, $orderList, TEMPLATE_ORDER_LIST);
        } else {
            // 全部订单
            renderPromise = order.renderAdminOrdersByUrl('/order/admin/', $orderList, TEMPLATE_ORDER_LIST);
        }
        // 注册事件
        renderPromise.then(() => {
            // 设置付款
            $('.btn-action-CREATED').click(function () {
                let id = $(this).attr('id');
                if (confirm("是否确认用户已经付款?")) {
                    order.payOrder(id, true)
                        .then((result) => {
                            if (result != null) {
                                functions.modal("成功", "确认付款成功！");
                                render();
                            }
                        });
                }
            });
            // 发货
            $('.btn-action-PAID').click(function () {
                let id = $(this).attr('id');
                // 获取运单号
                let deliverCode = prompt("请输入运单号");
                if (deliverCode.length <= 0) {
                    functions.modal("错误", "运单号不能为空！");
                    return;
                }
                // 请求
                order.deliverOrder(id, deliverCode).then((result) => {
                    if (result != null) {
                        functions.modal("成功", "发货成功！");
                        render();
                    }
                });
            });
            // 设置付款
            $('.btn-action-COMMENTED').click(function () {
                let id = $(this).attr('id');
                if (confirm("是否确认已经退款?")) {
                    order.refundOrder(id)
                        .then((result) => {
                            if (result != null) {
                                functions.modal("成功", "退款成功！");
                                render();
                            }
                        });
                }
            });
        });
    };
    render();
});