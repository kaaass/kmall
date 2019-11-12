/**
 * 后台登录
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'bootstrap'],
    function ($, functions, constants, auth, _) {

        let $username = $('#username'),
            $passwd = $('#passwd'),
            $submit = $('[type=submit]');

        // 绑定登录事件
        $submit.click(() => {
            let username = $username.val(),
                passwd = $passwd.val();
            // 参数校验
            if (username.length <= 0) {
                functions.modal("错误", "用户名不能为空！");
                return;
            }
            if (passwd.length <= 0) {
                functions.modal("错误", "密码不能为空！");
                return;
            }
            // 提交请求
            auth.login(username, passwd, true);
            return false;
        });
    });