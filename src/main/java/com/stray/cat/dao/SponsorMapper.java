package com.stray.cat.dao;

import com.github.pagehelper.PageInfo;
import com.stray.cat.pojo.Sponsor;
import com.stray.cat.vo.SponsorVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface SponsorMapper {

    @Select("select count(*) from c_sponsor")
    int queryCount();

    @Select("select sponsor_createtime from c_sponsor order by sponsor_createtime desc limit 1")
    Date queryNearDate();

    @Select("select * from c_sponsor where sponsor_status=#{sponsorStatus}")
    List<Sponsor> querySponsorByStatus(int sponsorStatus);

    @Select("select * from c_sponsor where sponsor_status=#{sponsorStatus} and sponsor_title like #{sponsorTitle}")
    List<Sponsor> querySponsorByTitle(int sponsorStatus,String sponsorTitle);

    @Select("select * from c_sponsor where sponsor_title like #{sponsorTitle}")
    List<Sponsor> querySponsorByTitle2(String sponsorTitle);

    @Select("select * from c_sponsor where sponsor_id=#{sponsorId}")
    Sponsor querySponsorById(int sponsorId);

    @Select("select * from c_sponsor where sponsor_phone=#{sponsorPhone}")
    List<Sponsor> querySponsorByPhone(String sponsorPhone);

    @Select("select * from c_sponsor where sponsor_phone=#{sponsorPhone} and sponsor_status=#{sponsorStatus}")
    List<Sponsor> querySponsorByPhoneStatus(String sponsorPhone,int sponsorStatus);

    @Insert("insert into c_sponsor values(#{sponsorId},#{sponsorPhone},#{sponsorTitle},#{sponsorContent},#{sponsorReason},#{sponsorStatus},#{sponsorCreatetime},#{sponsorPicture},#{sponsorCount})")
    int addSponsor(Sponsor sponsor);

    @Delete("delete from c_sponsor where sponsor_id=#{sponsorId}")
    int deleteSponsor(int sponsorId);

    @Delete("delete from c_sponsor where sponsor_phone=#{sponsorPhone}")
    int deleteSponsorByPhone(String sponsorPhone);

    @Update("update c_sponsor set sponsor_phone=#{sponsorPhone}," +
            "sponsor_title=#{sponsorTitle}" +
            "sponsor_content=#{sponsorContent}" +
            "sponsor_reason=#{sponsorReason}" +
            "sponsor_status=#{sponsorStatus}" +
            "sponsor_picture=#{sponsorPicture} " +
            "sponsor_createtime=#{sponsorCreatetime}"+
            "where sponsor_id=#{sponsorId}")
    int updateSponsor(Sponsor sponsor);

    @Update("update c_sponsor set sponsor_status=#{sponsorStatus} where sponsor_id=#{sponsorId}")
    int updateSponsorStatus(int sponsorStatus,int sponsorId);

    @Select("select * from c_sponsor where sponsor_status=#{status}")
    List<Sponsor> querySponsorBystatu(int status);
    @Select("select * from c_sponsor where sponsor_status=1")
    List<Sponsor> queryPostSponsors();
    @Select("SELECT * FROM c_sponsor WHERE sponsor_status=1 ORDER BY sponsor_count DESC LIMIT #{limit} ")
    List<Sponsor> querySponsorsOrderByCount(int limit);

    @Select("select * from c_sponsor where sponsor_status=#{status} order by sponsor_createtime desc")
    List<SponsorVo> querySponsorVoByStatus1(int status);
}
