package shiroi.stockengine.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiroi.stockengine.api.service.ShiroiEngineService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShiroiEngineV1Controller {

    private final ShiroiEngineService shiroiEngineService;

    @GetMapping()
    public String helloWorld() {
        return shiroiEngineService.getHelloWorld();
    }

    @GetMapping("/analyze")
    public String analyzeStock() {
        return shiroiEngineService.analyzeStock();
    }
}
