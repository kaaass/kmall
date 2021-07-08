/**
 * 商品模板信息
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/admin',
    'module/product',
    'module/template',
    'bootstrap'], function ($, functions, constants, auth, admin, product, template, _) {

    const TEMPLATE_LIST = 'product_template_list';

    let curProductId, curTemplateId;
    let $list = $('.table-responsive');

    let getParam = () => {
        let groupMap = {};
        $(".entry").each(function (){
            let $this = $(this);
            let group = $this.attr('group'),
                key = $this.attr('key'),
                value = $this.val();
            if (groupMap[group] === undefined) {
                groupMap[group] = {
                    group,
                    columns: {}
                };
            }
            groupMap[group].columns[key] = value;
        });
        return Object.values(groupMap);
    };

    // 加载图标
    feather.replace();

    // 分析url参数
    let requestParams = new URL(document.location.href).searchParams;
    if (requestParams.has(constants.PARAM_ID)) {
        curProductId = requestParams.get(constants.PARAM_ID);
    } else {
        functions.modal("错误", "必须指定商品！");
        return;
    }

    // 渲染模板信息列表
    let render = async () => {
        let data = await template.getByProduct(curProductId, curTemplateId);
        await functions.renderHbs($list, TEMPLATE_LIST, {
            template: data
        });
    };

    // 加载商品信息
    product.getProduct(curProductId).then(async (productData) => {
        // 修改标题
        $('#title').text("模板信息：" + productData.name);
        curTemplateId = productData.category.templateId;
        render();
    });

    // 事件绑定
    $('#btn-create').click(() => {
        let param = getParam();
        template.setByProduct(curProductId, param)
            .then(result => {
                functions.modal("信息", "保存成功！");
                render();
            });
    });
});