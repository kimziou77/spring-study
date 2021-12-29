package ex.client.controller;

import ex.client.dto.Req;
import ex.client.dto.UserResponse;
import ex.client.service.RestTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ApiController {

    private final RestTemplateService restTemplateService;

    public ApiController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping("/hello")
    public Req<UserResponse> getHello(){
        //return restTemplateService.hello();
        return restTemplateService.genericExchange();
    }

}
