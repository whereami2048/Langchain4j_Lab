package shiroi.stockengine.engine.assistants;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
class AssistantManagerTests {

    @Value("classpath:test-gemini-prompt.md")
    private Resource promptResource;

    @Value("classpath:test-gemini-analysis.md")
    private Resource analysisPromptSource;

    @Autowired
    private AssistantManager assistantManager;

    @Test
    void apply() throws IOException {
        String prompt = analysisPromptSource.getContentAsString(StandardCharsets.UTF_8);
        GoogleAiGeminiChatModel geminiChatModel = GoogleAiGeminiChatModel.builder()
                .apiKey("")
                .modelName("gemini-2.5-flash") // gemini-3-pro-preview 모델 api 사용 쿼타 X
                .allowGoogleSearch(true)
//                .responseFormat(ResponseFormat.JSON) -> 응답 결과 json 포맷 설정하면 예러 남
                .build();

        String result = geminiChatModel.chat(prompt);
        System.out.println(result);
    }
}
