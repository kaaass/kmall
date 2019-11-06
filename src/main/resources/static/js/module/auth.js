/**
 * 验证模块
 */
define(['jquery', 'module/functions', 'module/constants', 'axios'], function ($, functions, constants, axios) {

    let storage = window.localStorage;

    let forbiddenHandler = (data) => {
        let json = JSON.parse(data);
        if (json.status === 403) {
            functions.modal("错误", "您的登录已过期！正在跳转至登录页面");
            storage.removeItem(constants.KEY_AUTH);
            functions.jumpTo("/auth/login.html", 3000);
        }
        return json;
    };

    // 导航栏变更
    if (storage.getItem(constants.KEY_AUTH) != null) {
        // 已经登录
        $('#name').text(storage.getItem(constants.KEY_NAME));
        $('#register').remove();
        $('#my-dropdown').removeClass('invisible');
    } else {
        // 未登录
        $('#register').removeClass('invisible');
        $('#my-dropdown').remove();
    }

    /**
     * 获得请求用Axios实例
     * @returns {Axios|*}
     */
    let getAxiosInstance = () => {
        let auth = storage.getItem(constants.KEY_AUTH);
        return axios.create({
            headers: {'Authorization': auth},
            transformResponse: [forbiddenHandler]
        });
    };

    return {
        getAxiosInstance: getAxiosInstance
    };
});