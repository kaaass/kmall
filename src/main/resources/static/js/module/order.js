/**
 * 订单类函数
 */
define([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth'], function ($, functions, constants, auth) {

    let request = auth.getAxiosInstance(),
        adminRequest = auth.getAxiosInstance(true);

    /**
     * 缓存的订单
     * @type {{}}
     */
    let ordersMap = {};

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
     * 获取管理员操作
     * @param type
     * @returns {string|null}
     */
    let getAdminAction = (type) => {
        if (type === 'CREATED') {
            return '确认付款';
        } else if (type === 'PAID') {
            return '发货';
        } else if (type === 'COMMENTED') {
            return '退款';
        } else {
            return null;
        }
    };

    /**
     * 处理请求得到的订单数据
     * @param orders
     * @param isAdmin
     */
    let processData = async (orders, isAdmin = false) => {
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
            order.action = order.type;
            order.actionReadable = getTypeAction(order.type);
            // 管理相关信息
            if (isAdmin) {
                order.adminActionReadable = getAdminAction(order.type);
            }
            // 缓存Map
            ordersMap[order.id] = order;
        }
        return orders;
    };

    /**
     * 获得订单信息
     * @param orderId
     * @param isAdmin
     * @returns {Promise<null|*>}
     */
    let getOrder = async (orderId, isAdmin = false) => {
        let promise;
        if (isAdmin) {
            promise = adminRequest.get(`/order/admin/${orderId}/`);
        } else {
            promise = request.get(`/order/${orderId}/`);
        }
        let response = await promise
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
    let renderOrdersByUrl = async (url, $el, template) => {
        let response = await request.get(url)
            .catch((e) => {
                console.error("获取订单数据失败：", url, e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
        // 处理返回
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取订单数据错误：", url, response);
            functions.modal("错误", data.message);
            return null;
        }
        let orders = await processData(data.data);
        return await functions.renderHbs($el, template, {
            orders: orders
        });
    };

    /**
     * 由url渲染管理员订单
     * @param url 获得url
     * @param $el 渲染dom
     * @param template 模板路径
     */
    let renderAdminOrdersByUrl = async (url, $el, template) => {
        let response = await adminRequest.get(url)
            .catch((e) => {
                console.error("获取订单数据失败：", url, e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
        // 处理返回
        let data = response.data;
        if (data.status !== 200) {
            console.error("获取订单数据错误：", url, response);
            functions.modal("错误", data.message);
            return null;
        }
        let orders = await processData(data.data, true);
        return await functions.renderHbs($el, template, {
            orders: orders
        });
    };

    /**
     * 通过商品下单
     * @param productId
     * @param addressId
     * @returns {Promise<string|null>}
     */
    let addOrderFromProduct = async (productId, addressId) => {
        // 发送请求
        console.log(productId, addressId);
        let response = await request.post('/order/', {
            type: constants.orderRequestType.SINGLE,
            productId: productId,
            addressId: addressId
        }).catch((e) => {
            console.error("下单失败：", productId, addressId, e);
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
            type: constants.orderRequestType.MULTI,
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
            return "error";
        }
        return data.data.orderId;
    };

    /**
     * 支付
     * @param orderId
     * @returns {Promise<null|*>}
     */
    let payOrder = async (orderId, isAdmin = false) => {
        // 发送请求
        let promise;
        if (isAdmin) {
            promise = adminRequest.post(`/order/${orderId}/pay/`);
        } else {
            promise = request.get(`/order/${orderId}/payCheck/?callback=`);
        }
        let response = await promise
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

    /**
     * 评价订单
     * @param orderId
     * @param comments
     * @returns {Promise<boolean|*>}
     */
    let commentOrder = async (orderId, comments) => {
        // 发送请求
        let response = await request.post(`/order/${orderId}/comment/`, {
            comments: comments
        }).catch((e) => {
            console.error("评论失败：", orderId, e);
            functions.modal("错误", "评论失败！请检查网络连接。");
        });
        let data = response.data;
        if (data.status !== 200) {
            console.error("评论错误：", orderId, response);
            functions.modal("评论错误", data.message);
            return false;
        }
        return data.data.id;
    };

    /**
     * 订单发货
     * @param orderId
     * @param deliverCode
     * @returns {Promise<boolean|*>}
     */
    let deliverOrder = async (orderId, deliverCode) => {
        // 发送请求
        let response = await adminRequest.post(`/order/${orderId}/deliver/?deliverCode=${deliverCode}`)
            .catch((e) => {
                console.error("发货失败：", orderId, e);
                functions.modal("错误", "发货失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("发货错误：", orderId, response);
            functions.modal("发货错误", data.message);
            return false;
        }
        return data.data.id;
    };

    /**
     * 订单退款
     * @param orderId
     * @returns {Promise<boolean|*>}
     */
    let refundOrder = async (orderId) => {
        // 发送请求
        let response = await adminRequest.post(`/order/${orderId}/refund/`)
            .catch((e) => {
                console.error("退款失败：", orderId, e);
                functions.modal("错误", "退款失败！请检查网络连接。");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("退款错误：", orderId, response);
            functions.modal("退款错误", data.message);
            return false;
        }
        return data.data.id;
    };

    return {
        ordersMap: ordersMap,

        getTypeReadable: getTypeReadable,
        getTypeStatusReadable: getTypeStatusReadable,
        getTypeAction: getTypeAction,
        processData: processData,
        getOrder: getOrder,
        renderOrdersByUrl: renderOrdersByUrl,
        renderAdminOrdersByUrl: renderAdminOrdersByUrl,
        addOrderFromProduct: addOrderFromProduct,
        addOrderFromCart: addOrderFromCart,
        check: check,
        payOrder: payOrder,
        commentOrder: commentOrder,
        deliverOrder: deliverOrder,
        refundOrder: refundOrder
    };
});