/**
 * 购物函数
 */
define([
    'module/functions',
    'module/auth'], function (functions, auth) {

    let request = auth.getAxiosInstance(true);

    /**
     * 获得所有插件
     */
    let getPlugins = async () => {
        let response = await request.get('/plugin/')
            .catch((e) => {
                console.error("获取插件数据失败：", e);
                functions.modal("错误", "获取插件数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取插件数据错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        // 处理结果
        for (const plugin of data.data) {
            plugin.enableTimeReadable = functions.dateFormatTs(plugin.enableTime, 'Y-m-d H:i:s');
        }
        return data.data;
    };

    /**
     * 卸载插件
     * @param pluginId
     * @returns {Promise<void>}
     */
    let disablePlugin = async (pluginId) => {
        let response = await request.post(`/plugin/${pluginId}/disable/`)
            .catch((e) => {
                console.error("卸载插件失败：", e);
                functions.modal("错误", "卸载插件失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("卸载插件错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 添加插件
     * @param path
     * @returns {Promise<void>}
     */
    let enablePlugin = async (path) => {
        let response = await request.post(`/plugin/?path=${path}`)
            .catch((e) => {
                console.error("添加插件失败：", e);
                functions.modal("错误", "添加插件失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("添加插件错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 删除插件
     * @param pluginId
     * @returns {Promise<void>}
     */
    let removePlugin = async (pluginId) => {
        let response = await request.remove(`/plugin/${pluginId}/`)
            .catch((e) => {
                console.error("删除插件失败：", e);
                functions.modal("错误", "删除插件失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除插件错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 获得所有销售策略
     */
    let getPromotes = async () => {
        let response = await request.get('/promote/')
            .catch((e) => {
                console.error("获取销售策略数据失败：", e);
                functions.modal("错误", "获取销售策略数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取销售策略数据错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 获得一销售策略
     */
    let getPromoteById = async (promoteId) => {
        let response = await request.get(`/promote/${promoteId}/`)
            .catch((e) => {
                console.error("获取销售策略数据失败：", e);
                functions.modal("错误", "获取销售策略数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取销售策略数据错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 编辑销售策略
     */
    let modifyPromote = async (promoteData) => {
        let response = await request.post('/promote/', promoteData)
            .catch((e) => {
                console.error("更改销售策略数据失败：", e);
                functions.modal("错误", "更改销售策略数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("更改销售策略数据错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 检查配置
     * @param promoteId
     * @returns {Promise<null|*>}
     */
    let checkPromote = async (promoteId) => {
        let response = await request.get(`/promote/${promoteId}/check/`)
            .catch((e) => {
                console.error("检查销售策略数据失败：", e);
                functions.modal("错误", "检查销售策略数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("检查销售策略数据错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    /**
     * 删除促销配置
     * @param promoteId
     * @returns {Promise<null|*>}
     */
    let removePromote = async (promoteId) => {
        let response = await request.delete(`/promote/${promoteId}/`)
            .catch((e) => {
                console.error("删除销售策略数据失败：", e);
                functions.modal("错误", "删除销售策略数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除销售策略数据错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    return {
        getPlugins: getPlugins,
        disablePlugin: disablePlugin,
        enablePlugin: enablePlugin,
        removePlugin: removePlugin,

        getPromotes: getPromotes,
        getPromoteById: getPromoteById,
        modifyPromote: modifyPromote,
        checkPromote: checkPromote,
        removePromote: removePromote
    };
});