package net.kaaass.kmall.eventhandle;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.util.Constants;
import org.springframework.scripting.support.StandardScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Js事件处理器
 */
@Slf4j
public class JavaScriptEventHandler implements IEventListener {

    String codeStr = null;

    public JavaScriptEventHandler(String codeStr) {
        this.codeStr = codeStr;
    }

    @Override
    public void invoke(Event event) {
        StandardScriptEvaluator evaluator = new StandardScriptEvaluator();
        evaluator.setLanguage(Constants.SCRIPT_TYPE_JAVASCRIPT);
        var arguments = new HashMap<String, Object>();
        arguments.put("event", event);
        // 执行脚本
        Object result = null;
        try {
            result = evaluator.evaluate(new StaticScriptSource(codeStr), arguments);
        } catch (Exception e) {
            log.warn("事件执行脚本错误：", e);
        }
        if (result == null) {
            return;
        }
        log.debug("代码执行成功 {}", result);
        // 结果赋值
        try {
            Field[] fields = event.getClass().getFields();
            for (var field : fields) {
                Object value = field.get(result);
                field.set(event, value);
            }
        } catch (IllegalAccessException e) {
            log.warn("结果赋值失败", e);
        }
        log.debug("结果赋值成功 {}", event);
    }
}
