package shiroi.stockengine;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shiroi.stockengine.engine.aiservice.GeminiAssistant;

@SpringBootTest
class StockengineApplicationTests {

    @Autowired
    private GeminiAssistant geminiAssistant;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        String chatResult = geminiAssistant.chat("call tool getRecentNews");
        System.out.println(chatResult);
        assertThat(chatResult).isNotNull();
    }

}
