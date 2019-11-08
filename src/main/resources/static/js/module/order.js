/**
 * 订单类函数
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
     * 获取可读的类型状态
     * @param type
     */
    let getTypeStatusReadable = (type) => {
        if (type === 'CREATED') {
            return '已创建';
        } else if (type === 'PAID') {
            return '已付款';
        } else if (type === 'DELIVERED') {
            return '已发货';
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
            // 添加格式化时间
            order.createTimeReadable = functions.dateFormatTs(order.createTime);
            let format = "Y年m月d日 H:i:s";
            order.createDateTimeReadable = functions.dateFormatTs(order.createTime, format);
            order.payTimeReadable = functions.dateFormatTs(order.payTime, format);
            order.deliverTimeReadable = functions.dateFormatTs(order.deliverTime, format);
            order.finishTimeReadable = functions.dateFormatTs(order.finishTime, format);
            order.refundTimeReadable = functions.dateFormatTs(order.refundTime, format);
            // 获取typeStatusReadable
            order.typeStatusReadable = getTypeStatusReadable(order.type);
            // 获取typeReadable
            order.typeReadable = getTypeReadable(order.type);
            // 获取actionReadable
            order.actionReadable = getTypeAction(order.type);
        }
        return orders;
    };

    /**
     * 获得订单信息
     * @param orderId
     * @returns {Promise<null|*>}
     */
    let getOrder = async (orderId) => {
        let response = await request.get(`/order/${orderId}/`)
            .catch((e) => {
                console.error("获取订单数据失败：", orderId, e);
                functions.modal("错误", "获取订单数据失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取订单数据错误：", orderId, data);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data;
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

    /**
     * 通过购物车项目下单
     * @param cartIds
     * @param addressId
     * @returns {Promise<string|null>}
     */
    let addOrderFromCart = async (cartIds, addressId) => {
        let cartItems = [];
        // 拼接合法cartItems格式
        for (const cartId of cartIds) {
            cartItems.push({
                id: cartId
            });
        }
        // 发送请求
        let response = await request.post('/order/', {
            cartItems: cartItems,
            addressId: addressId
        }).catch((e) => {
            console.error("下单失败：", cartIds, addressId, e);
            functions.modal("错误", "下单失败！请检查网络连接。");
        });
        let data = response.data;
        if (data.status !== 200) {
            console.error("下单错误：", url, response);
            functions.modal("错误", data.message);
            return null;
        }
        return data.data.requestId;
    };

    /**
     * 检查下单结果
     * @param requestId
     * @returns {Promise<string|*>}
     */
    let check = async (requestId) => {
        // 发送请求
        let response = await request.get(`/order/request/${requestId}/`,)
            .catch((e) => {
                console.error("检查错误：", requestId, e);
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("下单错误：", requestId, response);
            functions.modal("下单错误", data.message);
            functions.jumpTo('/my/cart.html', 5000); // 返回购物车
            return null;
        }
        return data.data.orderId;
    };

    /**
     * 支付
     * @param orderId
     * @returns {Promise<null|*>}
     */
    let payOrder = async (orderId) => {
        // 发送请求
        let response = await request.get(`/order/${orderId}/payCheck/?callback=`,)
            .catch((e) => {
                console.error("支付失败：", orderId, e);
                functions.modal("错误", "支付失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("支付错误：", orderId, response);
            functions.modal("支付错误", data.message);
            return null;
        }
        return data.data.id;
    };

    return {
        getTypeReadable: getTypeReadable,
        getTypeStatusReadable: getTypeStatusReadable,
        getTypeAction: getTypeAction,
        processData: processData,
        getOrder: getOrder,
        renderOrdersByUrl: renderOrdersByUrl,
        addOrderFromCart: addOrderFromCart,
        check: check,
        payOrder: payOrder
    };
});