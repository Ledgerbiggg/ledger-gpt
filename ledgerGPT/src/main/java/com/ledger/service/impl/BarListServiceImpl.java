package com.ledger.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.common.Result;
import com.ledger.domain.BarList;
import com.ledger.domain.dto.BarListDTO;
import com.ledger.mapper.BarListMapper;
import com.ledger.service.BarListService;
import com.ledger.threa.UserThread;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author ledger
 * @version 1.0
 **/
@Service
public class BarListServiceImpl extends ServiceImpl<BarListMapper, BarList> implements BarListService {

    @Override
    public Result<String> add(BarListDTO barListDTO) {
        String currentUser = UserThread.getCurrentUser();
        String barList = barListDTO.getBarList();
        BarList barList1 = new BarList();
        Integer current = barListDTO.getCurrent();
        //获取最后一个存到数据库
        if (current == -1) {
            current = 0;
        }
        barList1.setBar(barList);
        barList1.setSequence(current);
        barList1.setUserId(currentUser);
        boolean save = save(barList1);
        return save ? Result.success("添加bar成功") : Result.error("添加失败");
    }

    @Override
    public Result<String> edit(String oldValue, String newValue) {
        String currentUser = UserThread.getCurrentUser();
        LambdaUpdateWrapper<BarList> barListLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        barListLambdaUpdateWrapper.set(BarList::getBar, newValue);
        barListLambdaUpdateWrapper.eq(BarList::getUserId, currentUser);
        barListLambdaUpdateWrapper.eq(BarList::getBar, oldValue);
        boolean update = update(barListLambdaUpdateWrapper);
        return update ? Result.success("修改成功") : Result.error("修改失败");
    }


}
