/**
 * 后台插件
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/admin',
    'bootstrap'], function ($, functions, constants, auth, admin, _) {

    const TEMPLATE_LIST = 'plugin_list';

    let $list = $('.table-responsive'),
        $pluginPath = $('#pluginPath');

    // 加载图标
    feather.replace();

    // 渲染插件列表
    let render = () => {
        admin.getPlugins()
            .then(data => {
                return functions.renderHbs($list, TEMPLATE_LIST, {
                    plugins: data
                });
            })
            .then(() => {
                // 删除
                $('.btn-remove').click(function () {
                    let id = $(this).attr('plugin-id');
                    admin.removePlugin(id)
                        .then(() => {
                            functions.modal("信息", "移除成功");
                            render();
                        });
                });
                // 启用
                $('.btn-enable').click(function () {
                    let path = $(this).attr('plugin-path');
                    admin.enablePlugin(path)
                        .then(() => {
                            functions.modal("信息", "启用成功");
                            render();
                        });
                });
                // 禁用
                $('.btn-disable').click(function () {
                    let id = $(this).attr('plugin-id');
                    admin.disablePlugin(id)
                        .then(() => {
                            functions.modal("信息", "禁用成功");
                            render();
                        });
                });
            });
    };
    render();

    // 事件绑定
    $('.btn-add').click(() => {
        $('#pluginModal').modal('show');
    });

    $('#btn-add').click(() => {
        let path = $pluginPath.val();
        if (path.length <= 0) {
            functions.modal("错误", "路径不能为空！");
            return;
        }
        $pluginPath.val("");
        admin.enablePlugin(path)
            .then(() => {
                functions.modal("信息", "添加插件成功！");
                render();
            });
    });
});