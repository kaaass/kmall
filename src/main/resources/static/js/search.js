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

        // 按关键词搜索
        $('#keyword').keydown(function (e) {
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

        // 按isbn搜索
        $('#isbn').keydown(function (e) {
            let curKey = e.which;
            if (curKey === 13) {
                // 回车
                let keyword = $(this).val();
                if (keyword.length <= 0) {
                    functions.modal("提示", "ISBN 不能为空！");
                    return false;
                }
                $('h1').text('搜索结果 - ISBN ' + keyword);
                request.get("/product/search/isbn/", {
                    params: {
                        isbn: keyword
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

        // 按作者搜索
        $('#author').keydown(function (e) {
            let curKey = e.which;
            if (curKey === 13) {
                // 回车
                let keyword = $(this).val();
                if (keyword.length <= 0) {
                    functions.modal("提示", "作者不能为空！");
                    return false;
                }
                $('h1').text('搜索结果 - 作者：' + keyword);
                request.get("/product/search/author/", {
                    params: {
                        author: keyword
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

        // 按日期范围搜索
        $('#pubend').keydown(function (e) {
            let curKey = e.which;
            if (curKey === 13) {
                // 回车
                let start = $('#pubstart').val();
                let end = $(this).val();
                if (start.length <= 0) {
                    start = '2021-01-01 00:00:00';
                }
                if (end.length <= 0) {
                    end = '2021-01-01 00:00:00';
                }
                $('h1').text('搜索结果 - ' + start + " ~ " + end);
                request.get("/product/search/date/", {
                    params: {
                        start: functions.dateToTs(start),
                        end: functions.dateToTs(end)
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

        // 按价格范围搜索
        $('#pricehigh').keydown(function (e) {
            let curKey = e.which;
            if (curKey === 13) {
                // 回车
                let low = $('#pricelow').val();
                let high = $(this).val();
                if (low.length <= 0) {
                    low = '0.00';
                }
                if (high.length <= 0) {
                    high = '0.00';
                }
                $('h1').text('搜索结果 - ¥' + low + " ~ ¥" + high);
                request.get("/product/search/price/", {
                    params: {
                        low: low,
                        high: high
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