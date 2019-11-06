/**
 * 商品类函数
 */
define(['jquery', 'module/functions', 'module/auth'], function ($, functions, auth) {

    let request = auth.getAxiosInstance();

    /**
     * 获取可读的类型
     * @param type
     */
    let getTypeReadable = (type) => {
        if (type === 'CREATED') {
            return '待付款';
        } else if (type === 'PAID') {
            return '待发货';
        } else if (type === 'DELIVERED') {
            return '待评价';
        } else if (type === 'COMMENTED') {
            return '已完成';
        } else if (type === 'REFUNDED') {
            return '已退款';
        } else if (type === 'CANCELED') {
            return '已取消';
        } else if (type === 'ERROR') {
            return '创建错误';
        }
    };

    /**
     * 获取可读操作
     * @param type
     * @returns {string|null}
     */
    let getTypeAction = (type) => {
        if (type === 'CREATED') {
            return '付款';
        } else if (type === 'PAID') {
            return null;
        } else if (type === 'DELIVERED') {
            return '评价';
        } else {
            return null;
        }
    };

    /**
     * 处理请求得到的订单数据
     * @param orders
     */
    let processData = async (orders) => {
        for (const order of orders) {
            // 获取createTimeReadable
            order.createTimeReadable = functions.dateFormatTs(order.createTime);
            // 获取typeReadable
            order.typeReadable = getTypeReadable(order.type);
            // 获取actionReadable
            order.actionReadable = getTypeAction(order.type);
        }
        return orders;
    };

    /**
     * 由url渲染订单
     * @param url 获得url
     * @param $el 渲染dom
     * @param template 模板路径
     */
    let renderOrdersByUrl = (url, $el, template) => {
        request.get(url)
            .then((response) => {
                let data = response.data;
                if (data.status !== 200) {
                    console.error("获取订单数据错误：", url, response);
                    functions.modal("错误", data.message);
                    return;
                }
                let products = processData(data.data);
                products.then(value => {
                    functions.renderHbs($el, template, {
                        orders: value
                    });
                });
            })
            .catch((e) => {
                console.error("获取订单数据失败：", url, e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
    };

    return {
        getTypeReadable: getTypeReadable,
        getTypeAction: getTypeAction,
        processData: processData,
        renderOrdersByUrl: renderOrdersByUrl
    };
});