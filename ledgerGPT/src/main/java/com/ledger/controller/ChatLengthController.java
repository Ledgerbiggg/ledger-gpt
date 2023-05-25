package com.ledger.controller;

import com.ledger.common.Result;
import com.ledger.domain.dto.ChatInfoDTO;
import com.ledger.service.ChatLengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
@RequestMapping("/chatLength")
public class ChatLengthController {

    @Autowired
    ChatLengthService chatLengthService;


    @PostMapping("/saveChat")
    public Result<String> saveChat(@RequestBody ChatInfoDTO chatInfoDTO){
        return chatLengthService.saveChat(chatInfoDTO);
    }
    @DeleteMapping("/delete/{bar}/{current}")
    public Result<String> delete(@PathVariable String bar, @PathVariable Integer current){
        return chatLengthService.delete(bar,current);
    }
    @DeleteMapping("/deleteAll")
    public Result<String> deleteAll(){
        return chatLengthService.deleteAll();
    }





}
