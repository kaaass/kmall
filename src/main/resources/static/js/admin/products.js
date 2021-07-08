/**
 * 商品
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/admin',
    'module/product',
    'bootstrap'], function ($, functions, constants, auth, admin, product, _) {

    const TEMPLATE_LIST = 'product_list';

    let $list = $('.table-responsive'),
        $add = $('#btn-add'),
        // Form
        $name = $('#name'),
        $thumbnailId = $('#thumbnailId'),
        $price = $('#price'),
        $mailPrice = $('#mailPrice'),
        $buyLimit = $('#buyLimit'),
        $categoryId = $('#categoryId'),
        $startSellTime = $('#startSellTime'),
        $rest = $('#rest');

    let loadParam = (product) => {
        $('#name').val(product ? product.name : "");
        $('#thumbnailId').val(product && product.thumbnail ? product.thumbnail.id : "");
        $('#price').val(product ? product.price : "");
        $('#mailPrice').val(product ? product.mailPrice : "");
        $('#buyLimit').val(product ? product.buyLimit : "");
        $('#categoryId').val(product && product.category ? product.category.id : "");
        $('#startSellTime').val(product ? product.startSellTimeReadable : "");
        $('#rest').val(product && product.storage ? product.storage.rest : "");
        $('#indexOrder').val(product ? product.indexOrder : "");
    };

    let getParam = () => {
        // TODO 检查是否为空
        return {
            name: $name.val(),
            thumbnailId: $thumbnailId.val(),
            price: $price.val(),
            mailPrice: $mailPrice.val(),
            buyLimit: $buyLimit.val(),
            categoryId: $categoryId.val(),
            startSellTime: functions.dateToTs($startSellTime.val()),
            rest: $rest.val(),
            indexOrder: $('#indexOrder').val()
        };
    };

    // 加载图标
    feather.replace();

    // 渲染分类列表
    product.getHierarchyCategories().then(categories => {
        let add = (name, id) => {
            $(`<option value="${id}">${name}</option>`).appendTo($('#categoryId'));
        };
        for (const category of categories) {
            add(category.name, category.id);
            for (const sub of category.subs) {
                add(sub.name, sub.id);
            }
        }
    });

    // 渲染商品列表
    let render = async () => {
        await product.renderProductsByUrl('/product/', $list, TEMPLATE_LIST, false, true);
        // 编辑
        $('.btn-edit').click(function () {
            let id = $(this).attr('product-id');
            loadParam(product.productCache[id]);
            $('#productModal').modal('show');
            // 添加
            $add.unbind('click');
            $add.click(() => {
                let param = getParam();
                product.editProduct(id, param)
                    .then(result => {
                        if (result)
                            functions.modal("信息", "编辑商品成功！");
                        render();
                    });
            });
        });
        // 移除
        $('.btn-remove').click(function () {
            let id = $(this).attr('product-id');
            product.removeProduct(id)
                .then(result => {
                    if (result)
                        functions.modal("信息", "删除商品成功！");
                    render();
                });
        });
        // 关联订单
        $('.btn-order').click(function () {
            let id = $(this).attr('product-id');
            functions.jumpTo(`orders-product.html?id=${id}`);
        });
        // 元数据
        $('.btn-meta').click(function () {
            let id = $(this).attr('product-id');
            functions.jumpTo(`products-metadata.html?id=${id}`);
        });
    };
    render();

    // 事件绑定
    let createCache = null;

    $('#btn-create').click(() => {
        $('#productModal').modal('show');
        loadParam({
            ...createCache,
            thumbnail: {
                id: createCache ? createCache.thumbnailId : ""
            },
            category: {
                id: createCache ? createCache.categoryId : ""
            },
            storage: {
                rest: createCache ? createCache.rest : ""
            },
            startSellTimeReadable: createCache ? functions.dateFormatTs(createCache.startSellTime) : "",
        });
        // 添加商品
        $add.unbind('click');
        $add.click(() => {
            let param = getParam();
            createCache = null;
            product.addProduct(param)
                .then(result => {
                    if (result)
                        functions.modal("信息", "添加商品成功！");
                    renderCache();
                })
                .catch(e => {
                    // 失败则记录表单内容
                    createCache = param;
                });
        });
    });
});