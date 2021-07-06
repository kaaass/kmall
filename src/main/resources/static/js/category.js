/**
 * 分类
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'module/product',
        'bootstrap'],
    function ($, functions, constants, auth, product, _) {

        const TEMPLATE_LIST = "product_lists";
        const TEMPLATE_CATEGORY = "categories";

        let $list = $('#product-list'),
            $categories = $('#categories');
        let curCatId;

        // 分析url参数
        if (functions.requestParams.has(constants.PARAM_ID)) {
            curCatId = functions.requestParams.get(constants.PARAM_ID);
        } else {
            curCatId = null;
        }

        // 获得分类并渲染
        product.getHierarchyCategories().then(data => {
            return functions.renderHbs($categories, TEMPLATE_CATEGORY, {
                categories: data
            });
        }).then(() => {
            // 当前高亮
            if (curCatId == null) {
                $('#all').addClass('active');
            } else {
                // 判断存在
                if (product.categories[curCatId] === undefined) {
                    functions.modal("错误", "该分类不存在！");
                    functions.jumpTo('?');
                    return;
                }
                // 设置高亮与页头
                $('h1').text(product.categories[curCatId].name);
                let father = curCatId;
                if (product.categories[curCatId].parent != null) {
                    father = product.categories[curCatId].parent.id;
                }
                $('#nav-' + father).addClass('active');
            }
        }).then(() => {
            // 获取所有内容
            if (curCatId == null) {
                product.renderProductsByUrl("/product/", $list, TEMPLATE_LIST);
            }
        }).then(() => {
            // 获取单分类
            if (curCatId != null) {
                product.renderProductsByUrl(`/product/category/${curCatId}/`, $list, TEMPLATE_LIST);
            }
        });
    });