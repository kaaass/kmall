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
     * 处理请求得到的商品数据
     * @param products
     */
    let processData = async (products) => {
        for (const product of products) {
            // 获取extra数据
            await request.get(`/product/${product.id}/extra/?count=1`)
                .then((response) => {
                    let data = response.data;
                    if (data.status !== 200) {
                        console.error("获取详细数据错误：", product.id, data);
                        return;
                    }
                    product.extra = data.data;
                })
                .catch((e) => {
                    console.error("获取详细数据失败：", product.id, e);
                });
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
                var data = response.data;
                var products = processData(data.data);
                products.then(value => {
                    functions.renderHbs($el, template, {
                        products: value
                    });
                });
            })
            .catch((e) => {
                console.error("获取所有数据失败：", product.id, e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
    };

    return {
        categories: categories,

        getCategories: getCategories,
        processData: processData,
        renderProductsByUrl: renderProductsByUrl
    };
});