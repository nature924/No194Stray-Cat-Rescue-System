package com.stray.cat.dao;

import com.stray.cat.pojo.Post;
import com.stray.cat.pojo.Sponsor;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface PostMapper {
    @Select("select * from c_post where post_phone=#{postPhone}")
    List<Post> queryPostByPhone(String postPhone);

    @Select("select * from c_post where post_title like #{postTitle}")
    List<Post> queryPostByTitle(String postTitle);

    @Select("select * from c_post")
    List<Post> queryPostAll();

    @Select("select * from c_post where post_id=#{postId}")
    Post queryPostById(int postId);

    @Insert("insert into c_post values(#{postId},#{postTitle},#{postPhone},#{postContent},#{postCreatetime},#{postCount},#{postUrl})")
    int addPost(Post post);

    @Update("update c_post set post_count=#{postCount} where post_id=#{postId}")
    int updatePostCount(int postCount,int postId);

    @Update("update c_post set post_title=#{postTitle},post_content=#{postContent},post_url=#{postUrl},post_createtime=#{postCreatetime} where post_id=#{postId}")
    int updatePost(Post post);

    @Delete("delete from c_post where post_id=#{postId}")
    int deletePost(int postId);

    @Delete("delete from c_post where post_phone=#{postPhone}")
    int deletePostByPhone(String postPhone);

    @Select("select count(*) from c_post")
    int queryCount();

    @Select("select post_createtime from c_post order by post_createtime desc limit 1")
    Date queryNearDate();

    @Select("select * from c_post order by post_count desc limit #{limit}")
    List<Post> queryPostMaxCount(int limit);


}
