/**
 * 验证模块
 */
define(['jquery', 'module/functions', 'module/constants', 'axios'], function ($, functions, constants, axios) {

    let storage = window.localStorage;

    let forbiddenHandler = (data) => {
        let json = JSON.parse(data);
        if (json.status === 403) {
            functions.modal("错误", "该功能需要登录！正在跳转至登录页面");
            storage.removeItem(constants.KEY_AUTH);
            functions.jumpTo("/auth/login.html", 3000);
        }
        return json;
    };

    let forbiddenAdminHandler = (data) => {
        let json = JSON.parse(data);
        if (json.status === 403) {
            functions.modal("错误", "该功能需要登录！正在跳转至登录页面");
            storage.removeItem(constants.KEY_ADMIN_AUTH);
            functions.jumpTo("/admin/login.html", 3000);
        }
        return json;
    };

    /**
     * 获得请求用Axios实例
     * @returns {Axios|*}
     */
    let getAxiosInstance = (isAdmin = false) => {
        let auth = storage.getItem(isAdmin ? constants.KEY_ADMIN_AUTH : constants.KEY_AUTH);
        return axios.create({
            baseURL: constants.API_BASE_URL,
            headers: {'Authorization': auth},
            transformResponse: [isAdmin ? forbiddenAdminHandler : forbiddenHandler]
        });
    };

    /**
     * 用户登录
     * @param phone
     * @param password
     * @param isAdmin
     * @returns {Promise<void|*>}
     */
    let login = async (phone, password, isAdmin = false) => {
        // 登录请求
        let response = await axios({
            method: 'post',
            url: constants.API_BASE_URL + '/auth/login',
            params: {
                phone: phone,
                password: password
            }
        }).catch((e) => {
            console.error("登录失败：", e);
            functions.modal("错误", "登录失败！请检查网络后再试");
        });
        let data = response.data;
        if (data.status !== 200) {
            functions.modal("错误", data.message);
            return;
        }
        if (isAdmin && !data.data.admin) {
            functions.modal("错误", "权限不足！");
            return;
        }
        // 保存登录凭据
        storage.setItem(isAdmin ? constants.KEY_ADMIN_AUTH : constants.KEY_AUTH,
            data.data.authToken.token);
        if (!isAdmin)
            storage.setItem(constants.KEY_NAME, data.data.phone);
        functions.modal("成功", "登录成功！正在跳转至首页");
        functions.jumpTo(isAdmin ? "/admin/index.html" : "/index.html");
    };

    /**
     * 退出登录
     * @param isAdmin
     */
    let exit = (isAdmin = false) => {
        storage.removeItem(isAdmin ? constants.KEY_ADMIN_AUTH : constants.KEY_AUTH);
        functions.modal("信息", "您已退出登录！");
        functions.jumpTo(isAdmin ? '/admin/login.html' : '/');
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

    // 控制台导航栏
    if (storage.getItem(constants.KEY_ADMIN_AUTH) != null) {
        // 登出
        $('#signOut').click(() => {
            exit(true);
        });
    }

    return {
        getAxiosInstance: getAxiosInstance,

        login: login,
        exit: exit,
    };
});