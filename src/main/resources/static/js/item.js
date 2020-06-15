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
        let productDataCache = {};

        // 解析Url
        if (requestParams.has(constants.PARAM_ID)) {
            curProductId = requestParams.get(constants.PARAM_ID);
        } else {
            curProductId = null;
        }

        // 获取物品详细信息
        let update = () => {
            product.getProduct(curProductId)
                .then(async (productData) => {
                    // 修改标题
                    $('title').text(productData.name + constants.TITLE_SUFFIX);
                    // 获得评论数据
                    productData.comments = await product.getComments(curProductId);
                    // 判断缓存
                    if (JSON.stringify(productDataCache) === JSON.stringify(productData)) {
                        console.log("命中缓存");
                        return null;
                    }
                    // 渲染
                    await functions.renderHbs($detail, TEMPLATE_DETAIL, productData);
                    $('.not-yet-sale').click(() => {
                        functions.modal('信息', '商品还未开卖！');
                        return false;
                    });
                    // 缓存
                    productDataCache = productData;
                });
        };
        update();
        // 5s 检查更新
        setInterval(update, 5000);
    });