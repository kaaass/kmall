/**
 * 确认订单页面
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/product',
        'module/cart',
        'module/user',
        'module/order',
        'bootstrap'],
    function ($, functions, constants, product, cart, user, order, _) {

        const TEMPLATE_ADDRESS_LIST = "address_list";
        const TEMPLATE_ITEM_LIST = "confirm_item_list";

        let $addressList = $('#address-list'),
            $list = $('#confirm-item-list');
        let curAddressId;

        // 解析Url并进行渲染
        if (functions.requestParams.has(constants.PARAM_ID)) {
            // 单商品购买
            let productId = functions.requestParams.get(constants.PARAM_ID);
            product.getProduct(productId)
                .then(product => {
                    let cartInfo = cart.generateCartInfo(product);
                    return functions.renderHbs($list, TEMPLATE_ITEM_LIST, cartInfo);
                })
                .then(() => {
                    // 绑定下单事件
                    $('.btn-submit').click(() => {
                        order.addOrderFromProduct(productId, curAddressId)
                            .then(requestId => {
                                functions.jumpTo(`status.html?id=${requestId}`, 0);
                            });
                    });
                });
        } else if (functions.requestParams.has(constants.PARAM_CART_IDS)) {
            // 购物车购买
            let cartIds = functions.requestParams.get(constants.PARAM_CART_IDS).split(",");
            cart.getCartInfo(cartIds)
                .then(cartInfo => {
                    return functions.renderHbs($list, TEMPLATE_ITEM_LIST, cartInfo);
                })
                .then(() => {
                    // 绑定下单事件
                    $('.btn-submit').click(() => {
                        order.addOrderFromCart(cartIds, curAddressId)
                            .then(requestId => {
                                functions.jumpTo(`status.html?id=${requestId}`, 0);
                            });
                    });
                });
        } else {
            functions.modal("错误", "缺少参数！");
            functions.jumpTo('/my/cart.html');
            return;
        }

        // 获取地址信息并渲染
        user.getAllAddress()
            .then(async (address) => {
                // 渲染
                await functions.renderHbs($addressList, TEMPLATE_ADDRESS_LIST, {
                    address: address
                });
                return address;
            })
            .then((address) => {
                // 绑定点击事件
                $('.address-link').click(function () {
                    let $parent = $(this).parents();
                    $('.address').removeClass('border-success');
                    $parent.addClass('border-success');
                    curAddressId = $parent.attr('adr-id');
                    console.log("选中地址", curAddressId);
                });
                // 选中默认
                let defId = "";
                for (const adr of address) {
                    if (adr.defaultAddress) {
                        defId = adr.id;
                        break;
                    }
                }
                $(`[adr-id=${defId}] .address-link`).click();
            });
    });