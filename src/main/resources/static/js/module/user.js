/**
 * 用户类函数
 */
define(['module/functions', 'module/auth'], function (functions, auth) {

    let request = auth.getAxiosInstance(),
        adminRequest = auth.getAxiosInstance(true);

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

    /**
     * 获得所有用户
     * @returns {Promise<*>}
     */
    let getAllUser = async () => {
        let response = await adminRequest.get('/user/')
            .catch((e) => {
                console.error("获取用户数据失败：", e);
                functions.modal("错误", "无法获取用户数据，请检查网络连接！");
            });
        return response.data.data;
    };

    /**
     * 增加地址
     * @param param
     * @returns {Promise<*>}
     */
    let addAddress = async (param) => {
        let response = await request.put('/user/profile/address/', param)
            .catch((e) => {
                console.error("增加地址失败：", param, e);
                functions.modal("错误", "增加地址失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("增加地址错误：", param, data);
            functions.modal("错误", data.message);
            throw Error("接口错误");
        }
        return data.id;
    };

    /**
     * 编辑地址
     * @param id
     * @param param
     * @returns {Promise<null|*>}
     */
    let editAddress = async (id, param) => {
        let response = await request.put(`/user/profile/address/${id}/`, param)
            .catch((e) => {
                console.error("修改地址失败：", id, e);
                functions.modal("错误", "修改地址失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("修改地址错误：", id, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.id;
    };

    /**
     * 删除地址
     * @param id
     * @returns {Promise<null|boolean>}
     */
    let removeAddress = async (id) => {
        let response = await request.delete(`/user/profile/address/${id}/`)
            .catch((e) => {
                console.error("删除地址失败：", id, e);
                functions.modal("错误", "删除地址失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除地址错误：", id, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    /**
     * 设置默认地址
     * @param id
     * @returns {Promise<null|boolean>}
     */
    let setDefaultAddress = async (id) => {
        let response = await request.post(`/user/profile/address/${id}/default/`)
            .catch((e) => {
                console.error("设置默认地址失败：", id, e);
                functions.modal("错误", "设置默认地址失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("设置默认地址错误：", id, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    return {
        getAllAddress: getAllAddress,

        getAllUser: getAllUser,

        addAddress,
        editAddress,
        removeAddress,
        setDefaultAddress,
    };
});