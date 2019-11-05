jQuery(($) => {
    let $list = $('#product-list');
    let listTemplate = $list.html();
    let request = window.getAxiosInstance();

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

    /**
     * 处理请求得到的商品数据
     * @param products
     */
    let processData = async (products) => {
        for (const product of products) {
            // 获取extra数据
            await request.get(`/product/${product.id}/extra/?count=1`)
                .then((response) => {
                    var data = response.data;
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

    // 获取首页内容
    request.get("/product/index/")
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
            console.error("获取首页数据失败：", product.id, e);
            modal("错误", "无法获取首页数据，请检查网络连接！");
        });
});