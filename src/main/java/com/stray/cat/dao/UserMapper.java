package com.stray.cat.dao;

import com.stray.cat.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from c_user where user_nickname=#{userName} and user_password=#{userPassword}")
    User queryByNameAndPassword(String userName,String userPassword);

    @Select("select * from c_user")
    List<User> queryUserAll();

    @Select("select * from c_user where user_id=#{userId}")
    User queryUserById(int userId);

    @Select("select * from c_user where user_phone=#{userPhone}")
    User queryByPhone(String userPhone);

    @Insert("insert into c_user values(#{userId},#{userNickname},#{userPhone},#{userPassword},#{userQq},#{userWechat},#{userCreatetime},#{userCount},#{userUrl})")
    int addUser(User user);

    @Update("update c_user set user_password=#{userPassword} where user_id=#{userId}")
    int updateUserPassword(String newPassword,int userId);

    @Update("update c_user set user_password=#{userPassword} where user_phone=#{userPhone}")
    int updateUserPhone(String newPassword,String userPhone);

    @Update("update c_user set user_url=#{userUrl},user_qq=#{userQq},user_wechat=#{userWechat},user_nickname=#{userNickname} where user_phone=#{userPhone}")
    int updateUser(User user);

    @Delete("delete from c_user where user_id=#{userId}")
    int deleteUser(int userId);

    @Select("select count(*) from c_user")
    int queryCount();

    @Select("select user_createtime from c_user order by user_createtime desc limit 1")
    Date queryNearDate();

}
