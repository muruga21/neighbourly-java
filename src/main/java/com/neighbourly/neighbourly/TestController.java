package com.neighbourly.neighbourly;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/testing")
public class TestController {

    @GetMapping
    public String GetHello(){
        return "Hello world";
    }

    @GetMapping("/viewList")
    public List<Map<String, Object>> GetList(){
        return Arrays.asList(
                Map.of("name", "product1", "price", 1234),
                Map.of("name", "product2", "price", 54321)
        );
    }
}
