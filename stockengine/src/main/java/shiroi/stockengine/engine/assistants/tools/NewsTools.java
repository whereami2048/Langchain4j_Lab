package shiroi.stockengine.engine.assistants.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class NewsTools {

    @Tool
    public void getRecentNews() {
        System.out.println("getRecentNews....");
    }
}
