package com.ledger.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author ledger
 * @version 1.0
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class BarListDTO {
    private String barList;
    private Integer current;
}
