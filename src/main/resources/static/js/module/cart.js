/**
 * 购物函数
 */
define([
    'jquery',
    'module/functions',
    'module/auth',
    'module/product'], function ($, functions, auth, product) {

    let request = auth.getAxiosInstance();

    /**
     * 处理请求得到的购物车数据
     * @param cartItems
     */
    let processData = async (cartItems) => {
        for (const cartItem of cartItems) {
            let count = cartItem.count;
            // 获得extra数据
            cartItem.product.extra = await product.getExtra(cartItem.product.id, count);
            // 计算总价
            cartItem.totalPrice = cartItem.product.extra.promotes.price;
        }
        return cartItems;
    };

    /**
     * 获得购物车汇总信息
     * @param cartItems
     */
    let getSummary = (cartItems) => {
        let result = {};
        result.totalCount = cartItems.length;
        result.totalPrice = .0;
        for (const cartItem of cartItems) {
            result.totalPrice += cartItem.totalPrice;
        }
        return result;
    };

    /**
     * 获得购物车数据
     * @returns {Promise<{summary: *, items: *}>} 总价、购物车项
     */
    let getCartInfo = async () => {
        // 获取购物车内容
        let response = await request.get('/user/cart/')
            .catch((e) => {
                console.error("获取购物车数据失败：", url, e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
        // 处理返回
        let data = response.data;
        // 增加详细数据
        let cartItems = await processData(data.data);
        // 构造返回信息
        return {
            items: cartItems,
            summary: getSummary(cartItems)
        };
    };

    /**
     * 删除购物车项目
     * @param cartItemId
     * @returns {Promise<boolean>} 是否成功
     */
    let deleteItem = async (cartItemId) => {
        let response = await request.delete(`/user/cart/${cartItemId}/`)
            .catch((e) => {
                console.error("删除购物车项目失败：", url, e);
                functions.modal("错误", "无法删除购物车项目，请检查网络连接！");
            });
        return response.data.status === 200;
    };

    return {
        getCartInfo: getCartInfo,
        processData: processData,
        deleteItem: deleteItem
    };
});