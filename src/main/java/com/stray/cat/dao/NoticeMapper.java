package com.stray.cat.dao;

import com.stray.cat.pojo.Notice;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {

    @Select("select * from c_notice order by notice_createtime desc limit 5")
    List<Notice> queryNotice();

    @Select("select * from c_notice order by notice_createtime desc")
    List<Notice> queryNotice1();

    @Delete("delete from c_notice where notice_id=#{noticeId}")
    int deleteNotice(int noticeId);

    @Insert("insert into c_notice values(#{noticeId},#{noticeCreatetime},#{noticeContent})")
    int addNotice(Notice notice);

    @Update("update c_notice set notice_creatime=#{noticeCreatetime},notice_content=#{noticeContent} where notice_id=#{noticeId}")
    int updateNotice(Notice notice);

    @Select("select count(*) from c_notice")
    int queryCount();

    @Select("select notice_createtime from c_notice order by notice_createtime desc limit 1")
    Date queryNearDate();
}
