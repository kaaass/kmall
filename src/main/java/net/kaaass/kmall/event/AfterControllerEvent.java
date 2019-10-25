package net.kaaass.kmall.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.kaaass.kmall.eventhandle.Event;
import org.aspectj.lang.JoinPoint;

/**
 * Controller返回后事件
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AfterControllerEvent extends Event {

    private Object[] args;

    private JoinPoint.StaticPart staticPart;

    private Object controllerResult;
}
