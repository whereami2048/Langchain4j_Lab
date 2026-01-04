package shiroi.stockengine.engine.stockstrategy.strategies.common;

import org.springframework.statemachine.action.Action;

public interface TaskNode<S, E>{
    S getState();
    default Action<S, E> stateAction() { return null; }
    default Action<S, E> errorAction()  { return null; }
}
