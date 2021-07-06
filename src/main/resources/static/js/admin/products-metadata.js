/**
 * 商品插件
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/admin',
    'module/product',
    'bootstrap'], function ($, functions, constants, auth, admin, product, _) {

    const TEMPLATE_LIST = 'metadata_list';

    let curProductId, metadataCache;
    let $list = $('.table-responsive'),
        $add = $('#btn-add');

    let loadParam = (metadata) => {
        $('#key').val(metadata ? metadata.key : "");
        $('#value').val(metadata ? metadata.value : "");
    };

    let getParam = () => {
        // TODO 检查是否为空
        return {
            key: $('#key').val(),
            value: $('#value').val(),
        };
    };

    // 加载图标
    feather.replace();

    // 分析url参数
    let requestParams = new URL(document.location.href).searchParams;
    if (requestParams.has(constants.PARAM_ID)) {
        curProductId = requestParams.get(constants.PARAM_ID);
    } else {
        functions.modal("错误", "必须指定商品！");
        return;
    }

    // 加载商品信息
    product.getProduct(curProductId).then(async (productData) => {
        // 修改标题
        $('#title').text("元数据：" + productData.name);
    });

    // 渲染元数据列表
    let render = async () => {
        metadataCache = await product.getAllMetadata(curProductId);
        await functions.renderHbs($list, TEMPLATE_LIST, {
            metadata: metadataCache
        });
        // 编辑
        $('.btn-edit').click(function () {
            let id = $(this).attr('metadata-key');
            loadParam({
                key: id,
                value: metadataCache[id]
            });
            $('#metadataModal').modal('show');
            // 添加
            $add.unbind('click');
            $add.click(() => {
                let param = getParam();
                product.setMetadata(curProductId, param)
                    .then(result => {
                        if (result)
                            functions.modal("信息", "编辑成功！");
                        render();
                    });
            });
        });
        // 移除
        $('.btn-remove').click(function () {
            let id = $(this).attr('metadata-key');
            product.removeMetadata(curProductId, id)
                .then(result => {
                    if (result)
                        functions.modal("信息", "删除成功！");
                    render();
                });
        });
    };
    render();

    // 事件绑定
    $('#btn-create').click(() => {
        $('#metadataModal').modal('show');
        loadParam(null);
        // 添加
        $add.unbind('click');
        $add.click(() => {
            let param = getParam();
            product.setMetadata(curProductId, param)
                .then(result => {
                    if (result)
                        functions.modal("信息", "添加成功！");
                    render();
                });
        });
    });
});