/**
 * 分类
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'module/product',
        'module/template',
        'bootstrap'],
    function ($, functions, constants, auth, product, template, _) {

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
        product.getHierarchyCategories().then(async (data) => {
            // 渲染分类
            functions.renderHbs($categories, TEMPLATE_CATEGORY, {
                categories: data
            });
            // 设置按模板搜索
            let templateId = product.categories[curCatId];
            templateId = templateId === undefined ? null : templateId.templateId;
            if (templateId == null) {
                $('#search').remove();
                return;
            }
            let $select = $('#template-key');
            let templates = await template.get(templateId);
            for (const schema of templates.schema) {
                schema.columns.forEach(el => {
                    $(`<option value="${el}">${schema.group}：${el}</option>`).appendTo($select);
                });
            }
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

        // 搜索
        $('#template-value').keydown(function (e) {
            let curKey = e.which;
            if (curKey === 13) {
                // 回车
                let key = $('#template-key').val(),
                    value = $(this).val();
                product.renderProductsByUrl(`/product/search/${key}/?value=${value}`, $list, TEMPLATE_LIST);
            }
        });
    });