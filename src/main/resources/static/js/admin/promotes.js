/**
 * 后台折扣一览
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'module/admin',
    'bootstrap'], function ($, functions, constants, auth, admin, _) {

    const TEMPLATE_LIST = 'promote_list';

    let $list = $('.table-responsive'),
        $modal = $('#promoteModal'),
        $name = $('#name'),
        $hint = $('#hint'),
        $clazz = $('#clazz'),
        $param = $('#param'),
        $order = $('#order'),
        $style = $('#style'),
        $confirm = $('#btn-confirm');

    let curId;

    let setParam = (data) => {
        $name.val(data.name);
        $hint.val(data.hint);
        $clazz.val(data.clazz);
        $param.val(data.param);
        $order.val(data.order);
        $style.val(data.style);
    };

    /**
     * 获取模态框参数
     */
    let getParam = () => {
        let name = $name.val(),
            hint = $hint.val(),
            clazz = $clazz.val(),
            param = $param.val(),
            order = $order.val(),
            style = $style.val();
        // TODO 参数非空校验
        return {
            name: name,
            hint: hint,
            clazz: clazz,
            param: param,
            order: order,
            style: style
        };
    };

    // 加载图标
    feather.replace();

    // 渲染插件列表
    let render = () => {
        admin.getPromotes()
            .then(data => {
                return functions.renderHbs($list, TEMPLATE_LIST, {
                    promotes: data
                });
            })
            .then(() => {
                // 编辑
                $('.btn-edit').click(function () {
                    let id = $(this).attr('promote-id');
                    admin.getPromoteById(id).then((data) => {
                        setParam(data);
                        // 模态框点击事件
                        $confirm.unbind('click');
                        $confirm.click(() => {
                            let param = getParam();
                            param.id = data.id;
                            param.lastUpdateTime = data.lastUpdateTime;
                            admin.modifyPromote(param)
                                .then(() => {
                                    functions.modal("信息", "成功修改！");
                                    render();
                                });
                        });
                    });
                    $modal.modal('show');
                });
                // 检查
                $('.btn-check').click(function () {
                    let id = $(this).attr('promote-id');
                    admin.checkPromote(id)
                        .then(data => {
                            if (data) {
                                functions.modal("信息", "配置正确！");
                            }
                        });
                });
                // 删除
                $('.btn-remove').click(function () {
                    let id = $(this).attr('promote-id');
                    admin.removePromote(id)
                        .then(data => {
                            if (data) {
                                functions.modal("信息", "成功删除！");
                                render();
                            }
                        });
                });
            });
    };
    render();

    // 促销模态框
    $('.btn-new').click(() => {
        $modal.modal('show');
        setParam({
            name: "",
            hint: "",
            clazz: "",
            param: "",
            order: "",
            style: ""
        });
        // 新增监听
        $confirm.unbind('click');
        $confirm.click(() => {
            let param = getParam();
            admin.modifyPromote(param)
                .then(() => {
                    functions.modal("信息", "成功添加！");
                    render();
                });
        });
    });
});