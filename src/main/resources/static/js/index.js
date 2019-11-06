/**
 * 首页
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'module/product',
        'bootstrap'],
    function ($, functions, constants, auth, product, _) {

        const TEMPLATE_LIST = "product_lists";

        let $list = $('#product-list');
        let request = auth.getAxiosInstance();

        // 获取首页内容
        request.get("/product/index/")
            .then((response) => {
                let data = response.data;
                let products = product.processData(data.data);
                products.then(value => {
                    functions.renderHbs($list, TEMPLATE_LIST, {
                        products: value
                    });
                });
            })
            .catch((e) => {
                console.error("获取首页数据失败：", product.id, e);
                functions.modal("错误", "无法获取首页数据，请检查网络连接！");
            });
    });