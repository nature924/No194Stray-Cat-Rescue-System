package com.stray.cat.dao;

import com.stray.cat.pojo.Comment;
import com.stray.cat.vo.CommentVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Select("select * from c_comment where comment_source=#{commentSource} and comment_ps_id=#{commentPsId} order by comment_createtime desc")
    List<CommentVo> queryComment(int commentSource, int commentPsId);

    @Delete("select * from c_comment where comment_id=#{commentId}")
    int deleteComment(int commentId);

    @Insert("insert into c_comment values(#{commentId},#{commentPhone},#{commentContent},#{commentCreatetime},#{commentPsId},#{commentSource})")
    int addComment(Comment comment);

    @Select("select count(*) from c_comment")
    int queryCount();

    @Select("select comment_createtime from c_comment order by comment_createtime desc limit 1")
    Date queryNearDate();

    @Select("select count(*) from c_comment where comment_ps_id=#{commentPsId} and comment_source=#{commentFrom}")
    int queryCount1(int commentPsId,int commentFrom);
}
