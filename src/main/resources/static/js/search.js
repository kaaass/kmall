/**
 * 搜索
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

        // 搜索侦听器
        $('[type=text]').keydown(function (e) {
            let curKey = e.which;
            if (curKey === 13) {
                // 回车
                let keyword = $(this).val();
                if (keyword.length <= 0) {
                    functions.modal("提示", "搜索内容不能为空！");
                    return false;
                }
                $('h1').text('搜索结果 - ' + keyword);
                request.get("/product/search/", {
                    params: {
                        keyword: keyword
                    }
                })
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
                        console.error("获取搜索数据失败：", product.id, e);
                        functions.modal("错误", "无法获取数据数据，请检查网络连接！");
                    });
            }
        });
    });