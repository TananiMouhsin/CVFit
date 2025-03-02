package com.cvfit.cvfit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class Test {



    @GetMapping("/hi")
    public String test(){
        return "HIIIIIII" ; 
    }
    
}
