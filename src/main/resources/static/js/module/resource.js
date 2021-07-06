/**
 * 资源类函数
 */
define(['module/functions', 'module/auth'], function (functions, auth) {

    let request = auth.getAxiosInstance(),
        adminRequest = auth.getAxiosInstance(true);

    /**
     * 获得资源
     */
    let getResources = async () => {
        let response = await adminRequest.get('/resource/')
            .catch((e) => {
                console.error("获得资源失败：", e);
                functions.modal("错误", "获得资源失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取资源错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 增加资源
     */
    let addResource = async (param) => {
        let response = await adminRequest.post('/resource/', param).catch((e) => {
            console.error("增加资源失败：", param, e);
            functions.modal("错误", "增加资源失败！请检查网络连接。");
        });
        let data = response.data;
        if (data.status !== 200) {
            console.error("增加资源错误：", param, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.id;
    };

    /**
     * 删除资源
     */
    let removeResource = async (resourceId) => {
        let response = await adminRequest.delete(`/resource/${resourceId}/`)
            .catch((e) => {
                console.error("删除资源失败：", resourceId, e);
                functions.modal("错误", "删除资源失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除资源错误：", resourceId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    return {
        getResources,
        addResource,
        removeResource,
    };
});