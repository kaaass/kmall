/**
 * 后台分类管理
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/product',
    'bootstrap'], function ($, functions, constants, auth, product, _) {

    let request = auth.getAxiosInstance(true);
    let $list = $('.table-responsive'),
        $add = $('#btn-add');

    const TEMPLATE_LIST = 'category_list';

    // 加载图标
    feather.replace();

    let loadParam = (category) => {
        $('#name').val(category ? category.name : "");
        $('#parentId').val(category ? category.parent.id : "");
    };

    let getParam = () => {
        // TODO 检查是否为空
        let parentId = $('#parentId').val();
        return {
            name: $('#name').val(),
            parentId: parentId.length > 0 ? parentId : null,
        };
    };

    // 加载图标
    feather.replace();

    // 渲染插件列表
    let render = async () => {
        let data = await product.getCategories();
        await functions.renderHbs($list, TEMPLATE_LIST, {
            categories: data
        });
        // 移除
        $('.btn-remove').click(function () {
            let id = $(this).attr('category-id');
            product.removeCategory(id)
                .then(result => {
                    if (result)
                        functions.modal("信息", "删除分类成功！");
                    render();
                });
        });
    };
    render();

    // 事件绑定
    $('#btn-create').click(() => {
        $('#categoryModal').modal('show');
        loadParam(null);
        // 添加
        $add.unbind('click');
        $add.click(() => {
            let param = getParam();
            product.addCategory(param)
                .then(result => {
                    if (result)
                        functions.modal("信息", "添加成功！");
                    render();
                });
        });
    });
});