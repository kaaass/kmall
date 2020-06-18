var Result = Java.type("net.kaaass.kmall.promote.IPromoteStrategy.Result");
var ResultType = Java.type("net.kaaass.kmall.promote.IPromoteStrategy.ResultType");

var result = null;

// 总价格满 100 参与折扣
if (context.price > 100) {
    // 商品打 8 折，保留两位小数
    var discount = context.price * 0.2;
    discount.toFixed(2);
    // 最高减 50
    if (discount > 50) {
        discount = 50;
    }
    context.price -= discount;
    result = new Result(ResultType.OK, context);
} else {
    result = new Result(ResultType.NOT_COND, context);
}

// 返回打折结果
result;
