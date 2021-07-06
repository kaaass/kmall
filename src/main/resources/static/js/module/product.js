/**
 * 商品类函数
 */
define(['jquery', 'module/functions', 'module/auth'], function ($, functions, auth) {

    /**
     * 商品分类信息
     *
     * 仅当调用getCategories后有效
     * @type {{}}
     */
    let categories = {},
        productCache = {};

    let request = auth.getAxiosInstance(),
        adminRequest = auth.getAxiosInstance(true);

    /**
     * 获得层次化商品分类
     * @returns {Promise<[]>} 商品分类数据
     */
    let getHierarchyCategories = async () => {
        let response = await request.get('/category/');
        let result = [];
        for (const category of response.data.data) {
            categories[category.id] = category;
            if (category.parent == null) {
                category.subs = [];
                result.push(category);
            }
        }
        for (const category of response.data.data) {
            if (category.parent !== null) {
                let father = category.parent.id;
                for (const item of result) {
                    if (item.id === father) {
                        item.subs.push(category);
                        break;
                    }
                }
            }
        }
        return result;
    };

    /**
     * 获得商品信息
     * @param productId
     * @returns {Promise<null|*>}
     */
    let getProduct = async (productId) => {
        let response = await request.get(`/product/${productId}/`)
            .catch((e) => {
                console.error("获取商品数据失败：", productId, e);
                functions.modal("错误", "获取商品数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取商品数据错误：", productId, data);
            functions.modal("错误", data.message);
            return null;
        }
        data = (await processData([data.data]))[0]; // silly
        return data;
    };

    /**
     * 获得extra数据
     * @param productId 商品id
     * @param count 商品数量
     * @returns {Promise<void>}
     */
    let getExtra = async (productId, count = -1) => {
        let response = await request.get(`/product/${productId}/extra/?count=${count}`)
            .catch((e) => {
                console.error("获取详细数据失败：", productId, e);
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取详细数据错误：", productId, data);
            return null;
        }
        return data.data;
    };

    /**
     * 获得评论数据
     * @param productId
     * @returns {Promise<void>}
     */
    let getComments = async (productId) => {
        let response = await request.get(`/product/${productId}/comments/`)
            .catch((e) => {
                console.error("获取评论数据失败：", productId, e);
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取评论数据错误：", productId, data);
            return null;
        }
        return data.data;
    };

    /**
     * 处理请求得到的商品数据
     * @param products
     */
    let processData = async (products, fetchExtra = true) => {
        let now = new Date().getTime() / 1000;
        for (const product of products) {
            // 获取extra数据
            if (fetchExtra) {
                product.extra = await getExtra(product.id);
                // 销售提示
                if (product.startSellTime > now) {
                    let time = functions.dateFormatTs(product.startSellTime, 'm月d日 H:i:s');
                    product.buyTips = `${time} 开售`;
                    product.quickBuy = true;
                } else {
                    product.buyTips = `¥ ${product.extra.promotes.price} 购买`;
                    product.quickBuy = false;
                }
            }
            // 时间格式化
            product.startSellTimeReadable = functions.dateFormatTs(product.startSellTime, 'Y-m-d H:i:s');
            product.publishDateReadable = functions.dateFormatTs(product.publishDate, 'Y-m-d H:i:s');
            // 缓存
            productCache[product.id] = product;
        }
        return products;
    };

    /**
     * 由url渲染商品
     * @param url 获得url
     * @param $el 渲染dom
     * @param template 模板路径
     */
    let renderProductsByUrl = async (url, $el, template, fetchExtra=true, admin=false) => {
        let response = await (admin ? adminRequest : request).get(url)
            .catch((e) => {
                console.error("获取所有数据失败：", url, e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
        let data = response.data;
        let products = await processData(data.data, fetchExtra);
        return await functions.renderHbs($el, template, {
            products: products
        });
    };

    /**
     * 增加商品
     * @param param
     * @returns {Promise<null|*>}
     */
    let addProduct = async (param) => {
        let response = await adminRequest.post('/product/', param)
            .catch((e) => {
                console.error("增加商品失败：", param, e);
                functions.modal("错误", "增加商品失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("增加商品错误：", param, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.id;
    };

    /**
     * 修改商品数据
     * @param productId
     * @param param
     * @returns {Promise<null|*>}
     */
    let editProduct = async (productId, param) => {
        let response = await adminRequest.post(`/product/${productId}/`, param)
            .catch((e) => {
                console.error("修改商品失败：", productId, e);
                functions.modal("错误", "修改商品失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("修改商品错误：", productId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.id;
    };

    /**
     * 删除商品
     * @param productId
     * @returns {Promise<null|boolean>}
     */
    let removeProduct = async (productId) => {
        let response = await adminRequest.delete(`/product/${productId}/`)
            .catch((e) => {
                console.error("删除商品失败：", productId, e);
                functions.modal("错误", "删除商品失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除商品错误：", productId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    /**
     * 检查 ISBN 格式
     * @param isbn
     * @returns {Promise<void>}
     */
    let checkIsbn = async (isbn) => {
        let response = await adminRequest.get(`/validate/isbn?id=${isbn}`)
            .catch((e) => {
                console.error("检查ISBN格式失败：", isbn, e);
                functions.modal("错误", "检查ISBN格式失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("ISBN格式错误：", isbn, data);
            alert(data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 获得商品分类
     * @returns {Promise<[]>} 商品分类数据
     */
    let getCategories = async () => {
        let response = await request.get('/category/')
            .catch((e) => {
                console.error("获得商品分类失败：", e);
                functions.modal("错误", "获得商品分类失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取商品分类错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 增加商品分类
     * @param param
     * @returns {Promise<null|*>}
     */
    let addCategory = async (param) => {
        let response = await adminRequest.post('/category/', param)
            .catch((e) => {
                console.error("增加商品分类失败：", param, e);
                functions.modal("错误", "增加商品分类失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("增加商品分类错误：", param, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.id;
    };

    /**
     * 删除商品分类
     * @param categoryId
     * @returns {Promise<null|boolean>}
     */
    let removeCategory = async (categoryId) => {
        let response = await adminRequest.delete(`/category/${categoryId}/`)
            .catch((e) => {
                console.error("删除商品分类失败：", categoryId, e);
                functions.modal("错误", "删除商品分类失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除商品分类错误：", categoryId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    /**
     * 获得商品元数据
     * @param productId
     * @returns {Promise<null|*>}
     */
    let getAllMetadata = async (productId) => {
        let response = await adminRequest.get(`/product/${productId}/metadata/`)
            .catch((e) => {
                console.error("获得元数据失败：", productId, e);
                functions.modal("错误", "获得元数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取元数据错误：", productId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    let setMetadata = async (productId, param) => {
        let response = await adminRequest.post(`/product/${productId}/metadata/`, param)
            .catch((e) => {
                console.error("设置元数据失败：", productId, e);
                functions.modal("错误", "设置元数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("设置元数据错误：", productId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    let removeMetadata = async (productId, key) => {
        let response = await adminRequest.delete(`/product/${productId}/metadata/${key}/`)
            .catch((e) => {
                console.error("删除元数据失败：", productId, key, e);
                functions.modal("错误", "删除元数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除元数据错误：", productId, key, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    /**
     * 缓存增加商品
     * @param param
     * @returns {Promise<null|*>}
     */
    let addProductCache = async (param) => {
        let response = await adminRequest.put('/product/cache/', param)
            .catch((e) => {
                console.error("增加商品失败：", param, e);
                functions.modal("错误", "增加商品失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("增加商品错误：", param, data);
            functions.modal("错误", data.message);
            throw Error("接口错误");
        }
        return data.id;
    };

    /**
     * 缓存修改商品数据
     * @param productId
     * @param param
     * @returns {Promise<null|*>}
     */
    let editProductCache = async (productId, param) => {
        let response = await adminRequest.post(`/product/cache/${productId}/`, param)
            .catch((e) => {
                console.error("修改商品失败：", productId, e);
                functions.modal("错误", "修改商品失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("修改商品错误：", productId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.id;
    };

    /**
     * 删除缓存商品
     * @param productId
     * @returns {Promise<null|boolean>}
     */
    let removeProductCache = async (productId) => {
        let response = await adminRequest.delete(`/product/cache/${productId}/`)
            .catch((e) => {
                console.error("删除商品失败：", productId, e);
                functions.modal("错误", "删除商品失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("删除商品错误：", productId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    /**
     * 提交商品缓冲到数据库
     * @returns {Promise<null|boolean>}
     */
    let commitProductCache = async () => {
        let response = await adminRequest.post('/product/cache/')
            .catch((e) => {
                console.error("提交商品缓冲失败：", e);
                functions.modal("错误", "提交商品缓冲失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("提交商品缓冲错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
    };

    /**
     * 清除商品缓冲
     * @returns {Promise<null|boolean>}
     */
    let clearProductCache = async () => {
        let response = await adminRequest.delete('/product/cache/')
            .catch((e) => {
                console.error("清除商品缓冲失败：", e);
                functions.modal("错误", "清除商品缓冲失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("清除商品缓冲错误：", data);
            functions.modal("错误", data.message);
            return null;
        }
        return true;
    };

    return {
        categories: categories,
        productCache: productCache,

        getHierarchyCategories: getHierarchyCategories,
        processData: processData,
        renderProductsByUrl: renderProductsByUrl,
        getProduct: getProduct,
        getExtra: getExtra,
        getComments: getComments,
        addProduct: addProduct,
        editProduct: editProduct,
        removeProduct: removeProduct,
        checkIsbn: checkIsbn,

        getCategories: getCategories,
        addCategory: addCategory,
        removeCategory: removeCategory,

        getAllMetadata,
        setMetadata,
        removeMetadata,

        addProductCache,
        editProductCache,
        removeProductCache,
        commitProductCache,
        clearProductCache,
    };
});