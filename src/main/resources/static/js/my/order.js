/**
 * 订单一览
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/order',
    'bootstrap'], function ($, functions, constants, auth, order, _) {

    const TEMPLATE_SUB_NAV = "all_order_sub_nav";
    const TEMPLATE_ORDER_LIST = "order_list";
    const TEMPLATE_COMMENT = "product_comment";

    let $subNav = $('#sub-nav'),
        $orderList = $('#order-list'),
        $commentModal = $('#commentModal'),
        $commentModalContent = $('#commentModalBody');
    let request = auth.getAxiosInstance();
    let curTypeId;
    let commentOrderId;

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
    let renderPromise;
    if (curTypeId != null) {
        // 对应类型
        renderPromise = order.renderOrdersByUrl(`/order/type/${curTypeId}/`, $orderList, TEMPLATE_ORDER_LIST);
    } else {
        // 全部订单
        renderPromise = order.renderOrdersByUrl('/order/', $orderList, TEMPLATE_ORDER_LIST);
    }
    // 注册事件
    renderPromise.then(() => {
        // 付款
        $('.btn-action-CREATED').click(function () {
            let id = $(this).attr('id');
            functions.jumpTo(`/order/pay.html?id=${id}`, 0);
        });
        // 评价
        $('.btn-action-DELIVERED').click(function () {
            commentOrderId = $(this).attr('id');
            // 弹出评价框
            functions.renderHbs($commentModalContent, TEMPLATE_COMMENT, order.ordersMap[commentOrderId])
                .then(() => {
                    $commentModal.modal('show');
                });
        });
    });
    // 评价
    $('#btn-comment').click(() => {
        let comments = [];
        $('.comment-form').each(function () {
            let productId = $(this).attr('product-id'),
                rate = $(this).find('.product-rate').val(),
                comment = $(this).find('.product-comment').val();
            if (comment.length < 5) {
                functions.modal("错误", "评论长度必须大于5字符！");
                comments = null;
                return false; // break each
            }
            comments.push({
                productId: productId,
                rate: rate,
                content: comment
            });
        });
        if (comments == null) {
            return;
        }
        order.commentOrder(commentOrderId, comments)
            .then((result) => {
                if (result !== null) {
                    functions.modal("信息", "评论成功！");
                    functions.refresh();
                }
            });
    });
});