/**
 * 购物车
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/cart',
        'bootstrap'],
    function ($, functions, constants, cart, _) {

        const TEMPLATE_SUMMARY = "cart_summary";
        const TEMPLATE_LIST = "cart_item_list";

        let $list = $('#cart-item-list'),
            $summary = $('#cart-summary');
        let selectedIds = [];

        /**
         * 物品价格统计
         */
        let updateSummary = async () => {
            let summary = {
                summary: {
                    totalCount: 0,
                    totalPrice: .0
                }
            };
            selectedIds = [];
            $('.item-select:checked').each(function () {
                let cartId = $(this).parents(".cart-item").attr("cart-id");
                // 增加id到选择表
                selectedIds.push(cartId);
                // 计算总价
                summary.summary.totalPrice += cart.cartItemMap[cartId].totalPrice;
                summary.summary.totalCount += 1;
            });
            await functions.renderHbs($summary, TEMPLATE_SUMMARY, summary);
            // 购买
            $('#btn-confirm').click(() => {
                if (selectedIds.length <= 0) {
                    functions.modal("错误", "请至少选择一个商品下单！");
                    return;
                }
                let param = new URLSearchParams();
                param.append(constants.PARAM_CART_IDS, selectedIds);
                console.log("当前订单数据：", param);
                functions.jumpTo(`/order/confirm.html?${param.toString()}`, 0);
            });
            return summary;
        };

        /**
         * 渲染
         */
        let render = async () => {
            // 获取购物车内容
            let cartInfo = await cart.getCartInfo();
            await functions.renderHbs($list, TEMPLATE_LIST, cartInfo);
            // 删除操作
            $('.btn-remove').click(function () {
                let cartId = $(this).parents(".cart-item").attr("cart-id");
                if (confirm("是否确认删除该项目？")) {
                    cart.deleteItem(cartId).then(() => {
                        render();
                    });
                }
            });
            // 数量更改
            $('.spinner button').click(function () {
                let cartId = $(this).parents(".cart-item").attr("cart-id");
                let offset = $(this).attr('direct') === 'up' ? 1: -1;
                let org = cart.cartItemMap[cartId].count;

                cart.modifyCount(cartId, org + offset).then(() => {
                    render();
                })
            });
            // 物品选择
            let $itemSelect = $('.item-select');
            // 勾选已经选择的物品
            $itemSelect.each(function () {
                let cartId = $(this).parents(".cart-item").attr("cart-id");
                if (selectedIds.indexOf(cartId) >= 0) { // 已经被选中
                    $(this).prop("checked", true);
                }
            });
            // 监听器
            $itemSelect.click(function () {
                // 取消全选
                if (!$(this).prop("checked")) {
                    $('#select-all').prop("checked", false);
                }
                updateSummary();
            });
            $('#select-all').click(function () {
                let check = $(this).prop("checked");
                $('.item-select').prop("checked", check);
                updateSummary();
            });
            await updateSummary();
        };

        // URL解析判断是否添加购物车
        if (functions.requestParams.has(constants.PARAM_ID)) {
            let productId = functions.requestParams.get(constants.PARAM_ID);
            cart.addToCart(productId).then((data) => {
                if (data !== null) {
                    functions.modal("信息", "添加购物车成功！");
                }
                functions.jumpTo('?'); // 去除参数
            });
            return;
        }

        // 开始渲染流程
        render();
    });