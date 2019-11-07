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

        // 获取购物车内容
        let cartInfoPromise = cart.getCartInfo();
        cartInfoPromise.then(cartInfo => {
            functions.renderHbs($list, TEMPLATE_LIST, cartInfo);
            functions.renderHbs($summary, TEMPLATE_SUMMARY, cartInfo);
        });

        // TODO 购买
        // TODO 数量更改
        // TODO 删除
    });