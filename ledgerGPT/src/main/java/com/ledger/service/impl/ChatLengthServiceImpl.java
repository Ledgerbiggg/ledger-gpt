package com.ledger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.common.Result;
import com.ledger.domain.BarList;
import com.ledger.domain.ChatLength;
import com.ledger.domain.dto.ChatInfoDTO;
import com.ledger.mapper.ChatLengthMapper;
import com.ledger.service.BarListService;
import com.ledger.service.ChatLengthService;
import com.ledger.threa.UserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
@Service
public class ChatLengthServiceImpl extends ServiceImpl<ChatLengthMapper, ChatLength> implements ChatLengthService {

    @Autowired
    BarListService barListService;

    @Transactional
    @Override
    public Result<String> saveChat(ChatInfoDTO chatInfoDTO) {
        String currentUser = UserThread.getCurrentUser();
        Integer current = chatInfoDTO.getCurrent();
        //保存一次问答(两个)
        Map<Integer, ArrayList<String>> chatLengthMap = chatInfoDTO.getChatLength();
        //先查一下有效的bar长度，空的bar就不存了
        LambdaQueryWrapper<BarList> barListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        barListLambdaQueryWrapper.eq(BarList::getUserId,currentUser);
        List<BarList> barLists = barListService.list(barListLambdaQueryWrapper);
        int size = barLists.size();
        //获取的时候避免获取错误还是取current的标记
        ArrayList<String> list = chatLengthMap.get(current);
        String ans = list.get(list.size() - 1);
        String ques = list.get(list.size() - 2);
        ChatLength chatLength = new ChatLength();
        ChatLength chatLength2 = new ChatLength();

        chatLength.setChatKey(size-1);
        chatLength.setUserId(currentUser);
        chatLength.setContent(ques);
        chatLength.setSequence(list.size()-2);
        chatLength2.setChatKey(size-1);
        chatLength2.setUserId(currentUser);
        chatLength2.setContent(ans);
        chatLength2.setSequence(list.size()-1);

        ArrayList<ChatLength> chatLengths = new ArrayList<>();
        chatLengths.add(chatLength);
        chatLengths.add(chatLength2);
        saveBatch(chatLengths);

        return Result.success("成功");
    }

    @Transactional
    @Override
    public Result<String> delete(String bar, Integer current) {
        String currentUser = UserThread.getCurrentUser();
        //移出同名的bar
        LambdaQueryWrapper<BarList> barListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        barListLambdaQueryWrapper.eq(BarList::getUserId,currentUser);
        barListLambdaQueryWrapper.eq(BarList::getBar,bar);
        boolean remove1 = barListService.remove(barListLambdaQueryWrapper);
        //移出消息
        LambdaQueryWrapper<ChatLength> chatLengthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatLengthLambdaQueryWrapper.eq(ChatLength::getUserId,currentUser);
        chatLengthLambdaQueryWrapper.eq(ChatLength::getChatKey,current);
        boolean remove = remove(chatLengthLambdaQueryWrapper);
        //将后面的消息提前
        LambdaUpdateWrapper<ChatLength> chatLengthLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        chatLengthLambdaUpdateWrapper.setSql("chat_key=chat_key-1").gt(ChatLength::getChatKey,current);
        chatLengthLambdaUpdateWrapper.eq(ChatLength::getUserId,currentUser);
        update(chatLengthLambdaUpdateWrapper);
        return remove1&&remove? Result.success("移出成功"):Result.error("移出失败");
    }

    @Transactional
    @Override
    public Result<String> deleteAll() {
        //根据后端代码把用户id对应的信息删除
        String currentUser = UserThread.getCurrentUser();
        LambdaQueryWrapper<ChatLength> chatLengthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatLengthLambdaQueryWrapper.eq(ChatLength::getUserId,currentUser);
        remove(chatLengthLambdaQueryWrapper);
        LambdaQueryWrapper<BarList> barListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        barListLambdaQueryWrapper.eq(BarList::getUserId,currentUser);
        barListService.remove(barListLambdaQueryWrapper);
        return Result.success("删除成功");
    }
}
