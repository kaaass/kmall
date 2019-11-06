/**
 * 商品详细页面
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'module/product',
        'bootstrap'],
    function ($, functions, constants, auth, product, _) {

        const TEMPLATE_DETAIL = "item_detail";

        let $detail = $('#detail');
        let requestParams = new URL(document.location.href).searchParams;
        let curProductId;

        // 解析Url
        if (requestParams.has(constants.PARAM_ID)) {
            curProductId = requestParams.get(constants.PARAM_ID);
        } else {
            curProductId = null;
        }

        // 获取物品详细信息
        product.getProduct(curProductId)
            .then(async (productData) => {
                // 获得extra数据
                productData.extra = await product.getExtra(curProductId);
                return productData;
            })
            .then(async (productData) => {
                // 获得评论数据
                productData.comments = await product.getComments(curProductId);
                return productData;
            })
            .then(productData => {
                // 渲染
                console.log(productData);
                functions.renderHbs($detail, TEMPLATE_DETAIL, productData);
            });
    });