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
    let categories = {};

    let request = auth.getAxiosInstance();

    /**
     * 获得商品分类
     * @returns {Promise<[]>} 商品分类数据
     */
    let getCategories = async () => {
        let response = await request.get('/category/');
        let result = [];
        for (const category of response.data.data) {
            categories[category.id] = category;
            if (category.parent == null) {
                category.subs = [];
                result.push(category);
            } else {
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
        return data.data;
    };

    /**
     * 获得extra数据
     * @param productId 商品id
     * @param count 商品数量
     * @returns {Promise<void>}
     */
    let getExtra = async (productId, count = 1) => {
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
    let processData = async (products) => {
        for (const product of products) {
            // 获取extra数据
            product.extra = await getExtra(product.id);
        }
        return products;
    };

    /**
     * 由url渲染商品
     * @param url 获得url
     * @param $el 渲染dom
     * @param template 模板路径
     */
    let renderProductsByUrl = (url, $el, template) => {
        request.get(url)
            .then((response) => {
                let data = response.data;
                let products = processData(data.data);
                products.then(value => {
                    functions.renderHbs($el, template, {
                        products: value
                    });
                });
            })
            .catch((e) => {
                console.error("获取所有数据失败：", url, e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
    };

    return {
        categories: categories,

        getCategories: getCategories,
        processData: processData,
        renderProductsByUrl: renderProductsByUrl,
        getProduct: getProduct,
        getExtra: getExtra,
        getComments: getComments
    };
});