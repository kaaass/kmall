/**
 * 用户类函数
 */
define(['module/functions', 'module/auth'], function (functions, auth) {

    let request = auth.getAxiosInstance();

    /**
     * 获取用户所有地址
     * @returns {Promise<*>}
     */
    let getAllAddress = async () => {
        let response = await request.get('/user/profile/address/')
            .catch((e) => {
                console.error("获取地址数据失败：", e);
                functions.modal("错误", "无法获取地址数据，请检查网络连接！");
            });
        return response.data.data;
    };

    return {
        getAllAddress: getAllAddress
    };
});