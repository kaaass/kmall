package net.kaaass.kmall.promote.strategy.dbms;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.KmallApplication;
import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.event.BeforeScriptStrategyExecuteEvent;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.BaseException;
import net.kaaass.kmall.promote.BaseDbmsPromoteStrategy;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.promote.ServiceAdapter;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.util.FileUtils;
import org.springframework.scripting.support.StandardScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;

import java.io.IOException;
import java.util.HashMap;

/**
 * 脚本策略
 */
@Slf4j
public class JavascriptStrategy extends BaseDbmsPromoteStrategy<OrderPromoteContext, OrderPromoteContext> {

    /**
     * 参数
     */
    @Data
    public static class Param {

        /**
         * 脚本路径
         */
        private String javascriptFile;
    }

    private Param param = null;

    private String function = null;

    @Override
    protected void initialize(PromoteStrategyDto promoteStrategyDto, ServiceAdapter serviceAdapter) throws BaseException {
        var paramStr = promoteStrategyDto.getParam();
        this.param = parseJsonParam(paramStr, Param.class);
        // 读取脚本内容
        try {
            function = FileUtils.readAll(this.param.javascriptFile);
        } catch (IOException e) {
            log.warn("脚本文件读入错误：", e);
            throw new BadRequestException("脚本文件读入错误！");
        }
    }

    @Override
    public Result<OrderPromoteContext> doPromote(OrderPromoteContext context) {
        StandardScriptEvaluator evaluator = new StandardScriptEvaluator();
        evaluator.setLanguage(Constants.SCRIPT_TYPE_JAVASCRIPT);
        var arguments = new HashMap<String, Object>();
        arguments.put("context", context);
        // 触发事件获得附加参数
        var event = new BeforeScriptStrategyExecuteEvent(context, getPromoteInfo(), this.param, null);
        KmallApplication.EVENT_BUS.post(event);
        arguments.put("extraInfo", event.getExtraInfo());
        // 执行脚本
        Object result = null;
        try {
            result = evaluator.evaluate(new StaticScriptSource(function), arguments);
        } catch (Exception e) {
            log.warn("执行脚本错误：", e);
        }
        if (result == null) {
            return new Result<>(false);
        }
        log.debug("脚本执行成功 {}", result);
        return new Result<>(true, (OrderPromoteContext) result);
    }
}
