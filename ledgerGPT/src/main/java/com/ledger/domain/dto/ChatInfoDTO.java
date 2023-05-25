package com.ledger.domain.dto;

import com.ledger.domain.BarList;
import com.ledger.domain.ChatInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author ledger
 * @version 1.0
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class ChatInfoDTO extends ChatInfo {
    private Map<Integer, ArrayList<String>> chatLength;
    private List<String> barList;
}
