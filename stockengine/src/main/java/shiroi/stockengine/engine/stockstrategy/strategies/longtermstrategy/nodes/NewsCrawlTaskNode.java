package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.nodes;

import dev.langchain4j.model.input.Prompt;
import shiroi.stockengine.engine.assistants.Assistant;
import shiroi.stockengine.engine.stockstrategy.strategies.common.AbstractTaskNode;
import shiroi.stockengine.engine.stockstrategy.strategies.common.TaskNode;

public class NewsCrawlTaskNode extends AbstractTaskNode implements TaskNode {

    public NewsCrawlTaskNode(Prompt prompt, Assistant assistant) {
        super(prompt, assistant);
    }

    @Override
    public void execute() {
        String result = getAssistant().execute(getPrompt().text());
        System.out.println(result);
    }
}
