/**
 * 个人中心
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'bootstrap'],
    function ($, functions, constants, auth, _) {

        const TEMPLATE_PROFILE = "profile";

        let $profile = $('#profile');
        let request = auth.getAxiosInstance();

        // 获取首页内容
        request.get("/user/profile/")
            .then((response) => {
                let data = response.data;
                functions.renderHbs($profile, TEMPLATE_PROFILE, data.data);
            })
            .catch((e) => {
                console.error("获取个人数据失败：", e);
                functions.modal("错误", "无法个人中心数据，请检查网络连接！");
            });
    });