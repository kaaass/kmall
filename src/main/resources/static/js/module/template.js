/**
 * 模板类函数
 */
define(['jquery', 'module/functions', 'module/auth', 'module/product'], function ($, functions, auth, product) {

    let request = auth.getAxiosInstance(),
        adminRequest = auth.getAxiosInstance(true);

    let cache = {};

    let jsonToDsl = (json) => {
        let result = "";
        for (const schema of json) {
            result += `${schema.group}: `;
            for (const column of schema.columns) {
                result += `${column}, `;
            }
            result = result.substr(0, result.length - 2) + "\n";
        }
        return result;
    };

    let dslToJson = (dsl) => {
        let lines = dsl.split("\n");
        let schemas = [];
        for (const line of lines) {
            let schema = line.split(":");
            if (schema.length !== 2) {
                continue;
            }
            // Group
            let group = schema[0].trim();
            // Columns
            let columns = schema[1]
                .split(",")
                .map(s => s.trim());
            // Result
            schemas.push({group, columns});
        }
        return JSON.stringify(schemas);
    };

    let getAll = async () => {
        let response = await adminRequest.get(`/template/`)
            .catch((e) => {
                console.error("获得模板失败：", e);
                functions.modal("错误", "获得模板失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取模板错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        // cache
        for (const template of data.data) {
            cache[template.id] = template;
        }
        return data.data;
    };

    let add = async (param) => {
        let response = await adminRequest.put('/template/', param)
            .catch((e) => {
                console.error("增加模板失败：", param, e);
                functions.modal("错误", "增加模板失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("增加模板错误：", param, data);
            functions.modal("错误", data.message);
            throw Error("接口错误");
        }
        return data.data.id;
    };

    let edit = async (id, param) => {
        let response = await adminRequest.put(`/template/${id}/`, param)
            .catch((e) => {
                console.error("修改模板失败：", id, e);
                functions.modal("错误", "修改模板失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("修改模板错误：", id, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data.id;
    };

    let remove = async (id) => {
        let response = await adminRequest.delete(`/template/${id}/`)
            .catch((e) => {
                console.error("删除模板失败：", id, e);
                functions.modal("错误", "删除模板失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除模板错误：", id, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    let get = async (id) => {
        let response = await adminRequest.get(`/template/${id}/`)
            .catch((e) => {
                console.error("获得模板失败：", id, e);
                functions.modal("错误", "获得模板失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取模板错误：", id, data);
            functions.modal("错误", data.message);
            return null;
        }
        // cache
        cache[id] = data.data;
        return data.data;
    };

    let getByProduct = async (id, templateId) => {
        let metadata = await product.getAllMetadata(id);
        if (metadata.template === undefined) {
            // 新建元数据
            let template = await get(templateId);
            let result = [];
            for (const schema of template.schema) {
                let columns = {};
                for (const col of schema.columns) {
                    columns[col] = "";
                }
                result.push({
                    group: schema.group,
                    columns
                });
            }
            return result;
        }
        return JSON.parse(metadata.template);
    };

    let setByProduct = async (id, param) => {
        return await product.setMetadata(id, {
            key: 'template',
            value: JSON.stringify(param)
        });
    };

    let setForCategory = async (cid, tid) => {
        let url = `/category/${cid}/template/`;
        if (tid) {
            url += `?tid=${tid}`;
        }
        let response = await adminRequest.post(url)
            .catch((e) => {
                console.error("设置模板失败：", arguments, e);
                functions.modal("错误", "设置模板失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("设置模板错误：", arguments, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    return {
        jsonToDsl,
        dslToJson,

        cache,
        getAll,
        get,
        add,
        edit,
        remove,

        getByProduct,
        setByProduct,

        setForCategory,
    };
});