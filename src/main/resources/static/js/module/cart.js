/**
 * 购物函数
 */
define([
    'module/functions',
    'module/auth',
    'module/product'], function (functions, auth, product) {

    let request = auth.getAxiosInstance();
    let globalCartInfo = {};
    let cartItemMap = {};

    /**
     * 处理请求得到的购物车数据
     * @param cartItems
     */
    let processData = async (cartItems) => {
        for (const cartItem of cartItems) {
            let count = cartItem.count;
            // 获得extra数据
            // noinspection JSPrimitiveTypeWrapperUsage
            cartItem.product.extra = await product.getExtra(cartItem.product.id, count);
            // 计算总价
            cartItem.totalPrice = cartItem.product.extra.promotes.price;
            // 加入映射表
            cartItemMap[cartItem.id] = cartItem;
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
        result.totalMailPrice = .0;
        for (const cartItem of cartItems) {
            result.totalPrice += cartItem.totalPrice;
            result.totalMailPrice = Math.max(result.totalMailPrice, cartItem.product.extra.promotes.mailPrice);
        }
        result.totalPrice += result.totalMailPrice;
        return result;
    };

    /**
     * 从商品产生购物车数据
     *
     * @param product
     * @returns {{summary: *, items: [*]}}
     */
    let generateCartInfo = (product) => {
        let cartItem = {};
        cartItem.count = 1;
        cartItem.product = product;
        cartItem.totalPrice = cartItem.product.extra.promotes.price;
        // 构造返回信息
        return globalCartInfo = {
            items: [cartItem],
            summary: getSummary([cartItem])
        };
    };

    /**
     * 获得购物车数据
     *
     * @param inIds 筛选其中的id
     * @returns {Promise<{summary: *, items: *}>} 总价、购物车项
     */
    let getCartInfo = async (inIds = null) => {
        // 获取购物车内容
        let response = await request.get('/user/cart/')
            .catch((e) => {
                console.error("获取购物车数据失败：", e);
                functions.modal("错误", "无法获取数据，请检查网络连接！");
            });
        // 处理返回
        let data = response.data.data;
        // 筛选
        if (inIds != null) {
            let temp = [];
            for (const one of data) {
                if (inIds.indexOf(one.id) >= 0) {
                    temp.push(one);
                }
            }
            data = temp;
        }
        // 增加详细数据
        let cartItems = await processData(data);
        // 构造返回信息
        return globalCartInfo = {
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
                console.error("删除购物车项目失败：", cartItemId, e);
                functions.modal("错误", "无法删除购物车项目，请检查网络连接！");
            });
        return response.data.status === 200;
    };

    /**
     * 编辑购物车项目数量
     * @param cartItemId
     * @param count
     * @returns {Promise<null|*>}
     */
    let modifyCount = async (cartItemId, count) => {
        let params = new URLSearchParams();
        params.append('count', count);
        let response = await request.post(`/user/cart/${cartItemId}/count/`, params)
            .catch((e) => {
                console.error("更改购物车数目失败：", cartItemId, e);
                functions.modal("错误", "无法更改购物车数目，请检查网络连接！");
            });
        let data = response.data;
        if (data.status !== 200) {
            console.error("更改购物车数目错误：", cartItemId, response);
            functions.modal("错误", data.message);
            return null;
        }
        return data;
    };

    /**
     * 增加到购物车
     * @param productId
     * @returns {Promise<null|*>}
     */
    let addToCart = async (productId) => {
        let response = await request.post("/user/cart/", {
            productId: productId,
            count: 1
        }).catch((e) => {
            console.error("添加购物车项目失败：", productId, e);
            functions.modal("错误", "无法添加购物车项目，请检查网络连接！");
        });
        let data = response.data;
        if (data.status !== 200) {
            console.error("更改购物车项目错误：", productId, response);
            functions.modal("错误", data.message);
            return null;
        }
        return data;
    };

    return {
        globalCartInfo: globalCartInfo,
        cartItemMap: cartItemMap,

        getCartInfo: getCartInfo,
        generateCartInfo: generateCartInfo,
        processData: processData,
        deleteItem: deleteItem,
        modifyCount: modifyCount,
        addToCart: addToCart
    };
});