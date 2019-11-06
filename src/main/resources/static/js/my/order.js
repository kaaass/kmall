/**
 * 订单一览
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'module/order',
        'bootstrap'],
    function ($, functions, constants, auth, order, _) {

        const TEMPLATE_SUB_NAV = "all_order_sub_nav";
        const TEMPLATE_ORDER_LIST = "order_list";

        let $subNav = $('#sub-nav'),
            $orderList = $('#order-list');
        let request = auth.getAxiosInstance();
        let curTypeId;

        let requestParams = new URL(document.location.href).searchParams;

        // 分析url参数
        if (requestParams.has(constants.PARAM_TYPE)) {
            curTypeId = requestParams.get(constants.PARAM_TYPE);
            if (curTypeId === 'all') {
                curTypeId = null;
            }
        } else {
            curTypeId = null;
        }

        // 获取并渲染子导航栏
        request.get("/user/profile/")
            .then((response) => {
                let data = response.data;
                functions.renderHbs($subNav, TEMPLATE_SUB_NAV, data.data)
                    .then(() => {
                        // 高亮
                        if (curTypeId != null) {
                            let $nav = $(`#nav-${curTypeId}`);
                            $nav.addClass('active');
                            $('main h2').text($nav.text());
                        } else {
                            $('#nav-all').addClass('active');
                            $('main h2').text('所有订单');
                        }
                    });
            })
            .catch((e) => {
                console.error("获取订单数目数据失败：", e);
            });

        // 加载并渲染订单
        if (curTypeId != null) {
            // 对应类型
            order.renderOrdersByUrl(`/order/type/${curTypeId}/`, $orderList, TEMPLATE_ORDER_LIST);
        } else {
            // 全部订单
            order.renderOrdersByUrl('/order/', $orderList, TEMPLATE_ORDER_LIST);
        }

        // TODO 实现按钮操作
    });