package com.stray.cat.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminIndexVo {
    private int adminIndexId;
    private String adminIndexContent;
    private Date adminNearTime;
    private String adminUrl;
}
