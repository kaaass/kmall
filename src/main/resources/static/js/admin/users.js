/**
 * 后台用户一览
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/user',
    'bootstrap'], function ($, functions, constants, user, _) {

    const TEMPLATE_LIST = 'user_list';

    let $list = $('.table-responsive');

    // 加载图标
    feather.replace();

    // 渲染插件列表
    let render = () => {
        user.getAllUser()
            .then(data => {
                return functions.renderHbs($list, TEMPLATE_LIST, {
                    users: data
                });
            });
    };
    render();

});