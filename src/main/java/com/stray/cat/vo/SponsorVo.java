package com.stray.cat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SponsorVo {
    private int sponsorId;
    private String sponsorPhone;
    private String sponsorUserName;
    private String sponsorTitle;
    private Date sponsorCreatetime;
    private String qq;
}