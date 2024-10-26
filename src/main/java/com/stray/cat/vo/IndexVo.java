package com.stray.cat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexVo {
    private int indexId;
    private String indexFrom;
    private String indexTitle;
    private Date indexCreateTime;
    private int indexCount;
    private String indexContent;
    private String indexUrl;
}
