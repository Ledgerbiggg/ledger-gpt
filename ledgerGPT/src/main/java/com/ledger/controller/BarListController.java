package com.ledger.controller;

import com.ledger.common.Result;
import com.ledger.domain.dto.BarListDTO;
import com.ledger.service.BarListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
@RequestMapping("/barList")
public class BarListController {

    @Autowired
    BarListService barListService;

    @PostMapping("/add")
    public Result<String> addBar(@RequestBody BarListDTO barListDTO){
        return  barListService.add(barListDTO);
    }
    @PutMapping("/edit/{oldValue}/{newValue}")
    public Result<String> edit(@PathVariable String newValue, @PathVariable String oldValue){
        return barListService.edit(oldValue,newValue);
    }

}
