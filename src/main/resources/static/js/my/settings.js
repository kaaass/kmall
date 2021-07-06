/**
 * 设置页面
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/user',
    'bootstrap'], function ($, functions, constants, auth, user, _) {

    const TEMPLATE_LIST = 'user_address';

    let $list = $('.table-responsive'),
        $add = $('#btn-add');

    let loadParam = (address) => {
        $('#area').val(address ? address.area : "");
        $('#detailAddress').val(address ? address.detailAddress : "");
        $('#mailCode').val(address ? address.mailCode : "");
        $('#phone').val(address ? address.phone : "");
        $('#fname').val(address ? address.name : "");
    };

    let getParam = () => {
        // TODO 检查是否为空
        return {
            area: $('#area').val(),
            detailAddress: $('#detailAddress').val(),
            mailCode: $('#mailCode').val(),
            phone: $('#phone').val(),
            name: $('#fname').val(),
        };
    };

    // 渲染商品列表
    let render = async () => {
        // 读取地址数据
        let data = await user.getAllAddress(),
            addressMap = {};
        await functions.renderHbs($list, TEMPLATE_LIST, {
            addresses: data
        });
        for (const addr of data) {
            addressMap[addr.id] = addr;
        }
        // 设为默认
        $('.btn-set-default').click(function () {
            let id = $(this).attr('address-id');
            user.setDefaultAddress(id)
                .then(result => {
                    if (result)
                        functions.modal("信息", "成功设为默认地址！");
                    render();
                });
        });
        // 编辑
        $('.btn-edit').click(function () {
            let id = $(this).attr('address-id');
            loadParam(addressMap[id]);
            $('#addressModal').modal('show');
            $add.unbind('click');
            $add.click(() => {
                let param = getParam();
                user.editAddress(id, param)
                    .then(result => {
                        if (result)
                            functions.modal("信息", "编辑地址成功！");
                        render();
                    });
            });
        });
        // 移除
        $('.btn-remove').click(function () {
            let id = $(this).attr('address-id');
            user.removeAddress(id)
                .then(result => {
                    if (result)
                        functions.modal("信息", "删除地址成功！");
                    render();
                });
        });
    };
    render();

    // 事件绑定
    $('#btn-create').click(() => {
        $('#addressModal').modal('show');
        loadParam(null);
        // 添加地址
        $add.unbind('click');
        $add.click(() => {
            let param = getParam();
            user.addAddress(param)
                .then(result => {
                    if (result)
                        functions.modal("信息", "添加地址成功！");
                    render();
                });
        });
    });
});