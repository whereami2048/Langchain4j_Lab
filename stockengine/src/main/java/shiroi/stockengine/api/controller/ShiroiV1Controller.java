package shiroi.stockengine.api.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shiroi.stockengine.api.service.ShiroiService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShiroiV1Controller {

    private final ShiroiService shiroiService;

    @GetMapping()
    public String helloWorld() {
        return shiroiService.getHelloWorld();
    }

    @GetMapping("/analyze")
    public List<String> analyzeStockByLongTermStrategy(@RequestParam String strategyType) {
        return shiroiService.analyzeStockByLongTermStrategy(strategyType);
    }
}
