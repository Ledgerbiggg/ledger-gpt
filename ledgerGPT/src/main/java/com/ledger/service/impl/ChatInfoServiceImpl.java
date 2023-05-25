package com.ledger.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.common.Result;
import com.ledger.domain.BarList;
import com.ledger.domain.ChatInfo;
import com.ledger.domain.ChatLength;
import com.ledger.domain.dto.ChatInfoDTO;
import com.ledger.mapper.ChatInfoMapper;
import com.ledger.service.BarListService;
import com.ledger.service.ChatInfoService;
import com.ledger.service.ChatLengthService;
import com.ledger.threa.UserThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ledger
 * @version 1.0
 **/
@Service
@Slf4j
public class ChatInfoServiceImpl extends ServiceImpl<ChatInfoMapper, ChatInfo> implements ChatInfoService {
    @Autowired
    BarListService barListService;

    @Autowired
    ChatLengthService chatLengthService;

    @Override
    public Result<ChatInfoDTO> getInfo() {
        //new一个
        ChatInfoDTO chatInfoDTO = new ChatInfoDTO();
        //获取用户的id
        String currentUser = UserThread.getCurrentUser();

        //查询对话列表框
        LambdaQueryWrapper<BarList> barListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        barListLambdaQueryWrapper.eq(BarList::getUserId, currentUser);
        barListLambdaQueryWrapper.orderByAsc(BarList::getSequence);
        List<BarList> list = barListService.list(barListLambdaQueryWrapper);
        if(list.size() == 0){
            return Result.success(null);
        }
        //添加对话列表
        ArrayList<String> barListStr = new ArrayList<>();
        for (BarList barList : list) {
            String bar = barList.getBar();
            barListStr.add(bar);
        }
        chatInfoDTO.setBarList(barListStr);

        //获取消息内容
        LambdaQueryWrapper<ChatLength> chatLengthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatLengthLambdaQueryWrapper.eq(ChatLength::getUserId, currentUser);
        chatLengthLambdaQueryWrapper.orderByAsc(ChatLength::getSequence);
        List<ChatLength> chatLengthList = chatLengthService.list(chatLengthLambdaQueryWrapper);
        HashMap<Integer, ArrayList<String>> chatLengthMap = new HashMap<>();
        if(chatLengthList.size() == 0){
            return Result.success(null);
        }
        for (ChatLength chatLength : chatLengthList) {
            String content = chatLength.getContent();
            Integer chatKey = chatLength.getChatKey();
            ArrayList<String> list1 = chatLengthMap.get(chatKey);
            if(list1!=null){
                list1.add(content);
            }else {
                ArrayList<String> list2 = new ArrayList<>();
                list2.add(content);
                chatLengthMap.put(chatKey,list2);
            }
        }
        chatInfoDTO.setChatLength(chatLengthMap);
        //获取一些其他参数
        LambdaQueryWrapper<ChatInfo> chatInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatInfoLambdaQueryWrapper.eq(ChatInfo::getUserId, currentUser);
        ChatInfo chatInfo = getOne(chatInfoLambdaQueryWrapper);
        if(chatInfo==null){
            return Result.success(null);
        }
        //判断current是不是大于bar的总长度
        Integer current = chatInfo.getCurrent();
        LambdaQueryWrapper<BarList> barListLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        barListLambdaQueryWrapper1.eq(BarList::getUserId,currentUser);
        List<BarList> list1 = barListService.list(barListLambdaQueryWrapper1);
        if(current>list1.size()-1){
            chatInfo.setCurrent(list1.size()-1);
        }
        BeanUtil.copyProperties(chatInfo, chatInfoDTO);
        return Result.success(chatInfoDTO);
    }

    @Override
    @Transactional
    public Result<String> saveInfo(ChatInfoDTO chatInfoDTO) {

        //获取用户的id
        String currentUser = UserThread.getCurrentUser();

        //获取当前聊天的key
        Integer current = chatInfoDTO.getCurrent();

        //保存页面信息
        ChatInfo chatInfo = BeanUtil.copyProperties(chatInfoDTO, ChatInfo.class);
        chatInfo.setUserId(currentUser);
        //先查询这个用户的页面信息是否已经存在
        LambdaQueryWrapper<ChatInfo> chatInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatInfoLambdaQueryWrapper.eq(ChatInfo::getUserId,currentUser);
        ChatInfo one = getOne(chatInfoLambdaQueryWrapper);
        //没有就保存
        if(one==null){
            save(chatInfo);
        }

        //保存用户聊天主题(list保存)
        List<String> barList = chatInfoDTO.getBarList();
        List<BarList> collect = barList.stream().map(s -> {
            BarList barList1 = new BarList();
            barList1.setBar(s);
            barList1.setUserId(currentUser);
            barList1.setSequence(barList.size()-1);
            return barList1;
        }).collect(Collectors.toList());
        barListService.saveBatch(collect);

        //保存一次问答(两个)
        Map<Integer, ArrayList<String>> chatLengthMap = chatInfoDTO.getChatLength();
        ArrayList<String> list = chatLengthMap.get(current);
        String ans = list.get(list.size() - 1);
        String ques = list.get(list.size() - 2);
        ChatLength chatLength = new ChatLength();
        ChatLength chatLength2 = new ChatLength();

        chatLength.setChatKey(current);
        chatLength.setUserId(currentUser);
        chatLength.setContent(ques);
        chatLength.setSequence(list.size()-2);
        chatLength2.setChatKey(current);
        chatLength2.setUserId(currentUser);
        chatLength2.setContent(ans);
        chatLength2.setSequence(list.size()-1);

        ArrayList<ChatLength> chatLengths = new ArrayList<>();
        chatLengths.add(chatLength);
        chatLengths.add(chatLength2);
        chatLengthService.saveBatch(chatLengths);

        return Result.success("保存成功");
    }

    @Override
    public Result<String> updateCurrent(ChatInfo chatInfo) {
        //获取用户信息
        String currentUser = UserThread.getCurrentUser();
        LambdaUpdateWrapper<ChatInfo> chatInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        chatInfoLambdaUpdateWrapper.eq(ChatInfo::getUserId,currentUser);
        //根据用户id保存
        chatInfo.setUserId(currentUser);
        boolean update = saveOrUpdate(chatInfo, chatInfoLambdaUpdateWrapper);
        return update?Result.success("保存成功"):Result.error("保存失败");
    }
}
