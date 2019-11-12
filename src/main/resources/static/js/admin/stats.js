/**
 * 后台销售情况
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'bootstrap'], function ($, functions, constants, auth, _) {

    let request = auth.getAxiosInstance(true);

    // 加载图标
    feather.replace();
});