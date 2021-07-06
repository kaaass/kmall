/**
 * 其他设置
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/resource',
    'bootstrap'], function ($, functions, constants, auth, resource, _) {

    let $list = $('.table-responsive'),
        $add = $('#btn-add');

    const TEMPLATE_LIST = 'resource_list';

    // 加载图标
    feather.replace();

    let loadParam = (resource) => {
        $('#url').val(resource ? resource.url : "");
        $('#type').val(resource ? resource.type : "image");
    };

    let getParam = () => {
        // TODO 检查是否为空
        return {
            url: $('#url').val(),
            type: $('#type').val(),
        };
    };

    // 加载图标
    feather.replace();

    // 渲染插件列表
    let render = async () => {
        let data = await resource.getResources();
        await functions.renderHbs($list, TEMPLATE_LIST, {
            resources: data
        });
        // 移除
        $('.btn-remove').click(function () {
            let id = $(this).attr('resource-id');
            resource.removeResource(id)
                .then(result => {
                    if (result)
                        functions.modal("信息", "删除资源成功！");
                    render();
                });
        });
    };
    render();

    // 事件绑定
    $('#btn-create-url').click(() => {
        $('#createUrlModal').modal('show');
        loadParam(null);
        // 添加
        $add.unbind('click');
        $add.click(() => {
            let param = getParam();
            resource.addResource(param)
                .then(result => {
                    if (result)
                        functions.modal("信息", "添加成功！");
                    render();
                });
        });
    });
});