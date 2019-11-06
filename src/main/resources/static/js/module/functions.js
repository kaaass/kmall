/**
 * 常用函数
 */
define(['jquery', 'handlebars', 'bootstrap'], function ($, Handlebars, _) {

    /**
     * 弹出模态框
     * @param title
     * @param body
     */
    let modal = (title, body) => {
        $('#msgModalTitle').html(title);
        $('#msgModalBody').html(body);
        $('#msgModal').modal('show');
    };

    /**
     * 渲染模板
     * @param $el
     * @param renderStr
     * @param data
     */
    let render = ($el, renderStr, data) => {
        var template = Handlebars.compile(renderStr);
        var result = template(data);
        $el.html(result);
    };

    /**
     * 页面跳转
     * @param dest
     * @param time
     */
    let jumpTo = (dest, time = 2000) => {
        setTimeout(() => {
            location.href = dest;
        }, time);
    };

    return {
        modal: modal,
        render: render,
        jumpTo: jumpTo
    };
});