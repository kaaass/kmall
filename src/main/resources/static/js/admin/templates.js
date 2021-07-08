/**
 * 商品模板
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/admin',
    'module/template',
    'bootstrap'], function ($, functions, constants, auth, admin, template, _) {

    const TEMPLATE_LIST = 'template_list';

    let $list = $('.table-responsive'),
        $add = $('#btn-add');

    let loadParam = (object) => {
        $('#name').val(object ? object.name : "");
        $('#template').val(object ? template.jsonToDsl(object.schema) : "");
    };

    let getParam = () => {
        // TODO 检查是否为空
        return {
            name: $('#name').val(),
            schema: template.dslToJson($('#template').val()),
        };
    };

    // 加载图标
    feather.replace();

    // 渲染列表
    let render = async () => {
        let data = await template.getAll();
        await functions.renderHbs($list, TEMPLATE_LIST, {
            template: data
        });
        // 编辑
        $('.btn-edit').click(function () {
            let id = $(this).attr('key');
            loadParam(template.cache[id]);
            $('#metadataModal').modal('show');
            // 添加
            $add.unbind('click');
            $add.click(() => {
                let param = getParam();
                template.edit(id, param)
                    .then(result => {
                        if (result)
                            functions.modal("信息", "编辑成功！");
                        render();
                    });
            });
        });
        // 移除
        $('.btn-remove').click(function () {
            let id = $(this).attr('key');
            template.remove(id)
                .then(result => {
                    if (result)
                        functions.modal("信息", "删除成功！");
                    render();
                });
        });
    };
    render();

    // 事件绑定
    $('#btn-create').click(() => {
        $('#metadataModal').modal('show');
        loadParam(null);
        // 添加
        $add.unbind('click');
        $add.click(() => {
            let param = getParam();
            template.add(param)
                .then(result => {
                    if (result)
                        functions.modal("信息", "添加成功！");
                    render();
                });
        });
    });
});