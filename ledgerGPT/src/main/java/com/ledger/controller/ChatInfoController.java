package com.ledger.controller;

import com.ledger.common.Result;
import com.ledger.domain.ChatInfo;
import com.ledger.domain.dto.ChatInfoDTO;
import com.ledger.service.ChatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
@RequestMapping("/chatInfo")
public class ChatInfoController {
    @Autowired
    private ChatInfoService chatInfoService;

    @GetMapping("/getInfo")
    public Result<ChatInfoDTO> getInfo(){
       return chatInfoService.getInfo();
    }

    @PostMapping("/saveInfo")
    public Result<String> saveInfo(@RequestBody ChatInfoDTO chatInfoDTO){
        return chatInfoService.saveInfo(chatInfoDTO);
    }

    /**
     * 更改这个页面的所有状态
     * @param current
     * @param maxKey
     * @return
     */
    @PutMapping("/updatePage")
    public Result<String> updateCurrent(@RequestParam Integer current,@RequestParam Integer maxKey){
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setCurrent(current);
        chatInfo.setMaxKey(maxKey);
        return chatInfoService.updateCurrent(chatInfo);
    }

}
