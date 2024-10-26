package com.stray.cat.dao;

import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.Share;
import com.stray.cat.pojo.Sponsor;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ShareMapper {
    @Select("select * from c_share")
    List<Share> queryShareAll();

    @Select("select * from c_share where share_title like #{shareTitle}")
    List<Share> queryShareByTitle(String shareTitle);

    @Select("select * from c_share where share_phone=#{sharePhone}")
    List<Share> queryShareByPhone(String sharePhone);

    @Select("select * from c_share where share_id=#{shareId}")
    Share queryShareById(int shareId);

    @Select("SELECT * FROM c_share ORDER BY share_count DESC LIMIT #{limit}; ")
    List<Share> queryShareMaxCount(int limit);

    @Select("select * from c_share where share_status=#{shareStatus}")
    List<Share> queryShareByStatus(int shareStatus);

    @Insert("insert into c_share values(#{shareId},#{sharePhone},#{shareTitle},#{shareContent},#{shareCreatetime},#{shareCount},#{shareUrl})")
    int addShare(Share share);

    @Delete("delete from c_share where share_id=#{shareId}")
    int deleteShare(int shareId);

    @Delete("delete from c_share where share_phone=#{sharePhone}")
    int deleteShareByPhone(String sharePhone);

    @Update("update c_share set share_count=#{shareCount} where share_id=#{shareId}")
    int updateShareCount(int shareCount,int shareId);

    @Update("update c_share set share_title=#{shareTitle},share_content=#{shareContent},share_url=#{shareUrl},share_createtime=#{shareCreatetime} where share_id=#{shareId}")
    int updateShare(Share share);

    @Select("select count(*) from c_share")
    int queryCount();

    @Select("select share_createtime from c_share order by share_createtime desc limit 1")
    Date queryNearDate();

}
