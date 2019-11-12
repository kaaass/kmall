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
    let addPlugin = async (path) => {
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

    return {
        getPlugins: getPlugins,
        disablePlugin: disablePlugin,
        addPlugin: addPlugin
    };
});