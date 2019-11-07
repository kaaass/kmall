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

        /**
         * 渲染
         */
        let render = async () => {
            // 获取购物车内容
            let cartInfo = await cart.getCartInfo();
            await functions.renderHbs($list, TEMPLATE_LIST, cartInfo);
        };

        render().then(() => {
            // 删除操作
            $('.btn-remove').click(function () {
                let cartId = $(this).parents(".cart-item").attr("cart-id");
                if (confirm("是否确认删除该项目？")) {
                    cart.deleteItem(cartId).then(() => {
                        render();
                    });
                }
            });
        });

        // TODO 购买
        // TODO 数量更改
    });