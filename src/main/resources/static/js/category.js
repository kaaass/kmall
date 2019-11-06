/**
 * 分类
 */
require([
        'jquery',
        'module/functions',
        'module/constants',
        'module/auth',
        'bootstrap'],
    function ($, functions, constants, auth, _) {
        let $list = $('#product-list'),
            $categories = $('#categories');
        let listTemplate = $list.html(),
            categoriesTemplate = $categories.html();
        let request = auth.getAxiosInstance();
        let categories = {};
        let curCatId;

        let requestParams = new URL(document.location.href).searchParams;

        /**
         * 获得商品分类
         * @returns {Promise<[]>} 商品分类数据
         */
        let getCategories = async () => {
            var response = await request.get('/category/');
            var result = [];
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
         * @param template 模板
         */
        let renderProductsByUrl = (url, $el, template) => {
            request.get(url)
                .then((response) => {
                    var data = response.data;
                    var products = processData(data.data);
                    products.then(value => {
                        functions.render($el, template, {
                            products: value
                        });
                    });
                })
                .catch((e) => {
                    console.error("获取所有数据失败：", product.id, e);
                    functions.modal("错误", "无法获取数据，请检查网络连接！");
                });
        };

        // 分析url参数
        console.log(requestParams);
        if (requestParams.has(constants.PARAM_ID)) {
            curCatId = requestParams.get(constants.PARAM_ID);
        } else {
            curCatId = null;
        }

        // 获得分类并渲染
        getCategories().then(data => {
            functions.render($categories, categoriesTemplate, {
                categories: data
            });
        }).then(value => {
            // 当前高亮
            if (curCatId == null) {
                $('#all').addClass('active');
            } else {
                // 判断存在
                if (categories[curCatId] === undefined) {
                    functions.modal("错误", "该分类不存在！");
                    functions.jumpTo('?');
                    return;
                }
                // 设置高亮与页头
                $('h1').text(categories[curCatId].name);
                let father = curCatId;
                if (categories[curCatId].parent != null) {
                    father = categories[curCatId].parent.id;
                }
                $('#nav-' + father).addClass('active');
            }
        }).then(value => {
            // 获取所有内容
            if (curCatId == null) {
                renderProductsByUrl("/product/", $list, listTemplate);
            }
        }).then(value => {
            // 获取单分类
            if (curCatId != null) {
                renderProductsByUrl(`/product/category/${curCatId}/`, $list, listTemplate);
            }
        });
    });