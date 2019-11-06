jQuery(($) => {
    const PARAM_ID = "id";

    let $list = $('#product-list'),
        $categories = $('#categories');
    let listTemplate = $list.html(),
        categoriesTemplate = $categories.html();
    let request = window.getAxiosInstance();
    let categories = {};
    let curCatId;

    let modal = (title, body) => {
        $('#msgModalTitle').html(title);
        $('#msgModalBody').html(body);
        $('#msgModal').modal('show');
    };

    let render = ($el, renderStr, data) => {
        var template = Handlebars.compile(renderStr);
        var result = template(data);
        $el.html(result);
    };

    let requestParams = new URL(document.location.href).searchParams;

    /**
     * 获得商品分类
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

    // 分析url参数
    console.log(requestParams);
    if (requestParams.has(PARAM_ID)) {
        curCatId = requestParams.get(PARAM_ID);
    } else {
        curCatId = null;
    }

    // 获得分类并渲染
    getCategories().then(data => {
        render($categories, categoriesTemplate, {
            categories: data
        });
    }).then(value => {
        // 当前高亮
        if (curCatId == null) {
            $('#all').addClass('active');
        } else {
            var father = curCatId;
            if (categories[curCatId].parent != null) {
                father = categories[curCatId].parent.id;
            }
            $('#nav-' + father).addClass('active');
        }
    }).then(value => {
        console.log(curCatId);
        // 获取所有内容
        if (curCatId == null) {
            request.get("/product/")
                .then((response) => {
                    var data = response.data;
                    var products = processData(data.data);
                    products.then(value => {
                        render($list, listTemplate, {
                            products: value
                        });
                    });
                })
                .catch((e) => {
                    console.error("获取所有数据失败：", product.id, e);
                    modal("错误", "无法获取数据，请检查网络连接！");
                });
        }
    }).then(value => {
        console.log(curCatId);
        // 获取单分类
        if (curCatId != null) {
            request.get(`/product/category/${curCatId}/`)
                .then((response) => {
                    var data = response.data;
                    var products = processData(data.data);
                    products.then(value => {
                        render($list, listTemplate, {
                            products: value
                        });
                    });
                })
                .catch((e) => {
                    console.error("获取所有数据失败：", product.id, e);
                    modal("错误", "无法获取数据，请检查网络连接！");
                });
        }
    });
});