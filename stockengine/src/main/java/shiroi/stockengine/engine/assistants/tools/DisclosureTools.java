package shiroi.stockengine.engine.assistants.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class DisclosureTools {

    @Tool
    public void getRecentDisclosure() {
        System.out.println("getRecentDisclosure...");
    }
}
