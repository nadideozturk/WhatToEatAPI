package com.whattoeat.api.whattoeat.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @RequestMapping("/")
    public String exapmleFunc(){
        return "Calisti";
    }

    @RequestMapping("/meals/{mealId}")
    public String getMeal(@PathVariable String mealId){
        if(mealId.equals("T1")){
            return "Firinda Tavuk";
        }
        else{
            return "Selected meal: "+mealId+" is not found.";
        }

    }
}
