package com.stray.cat.dao;

import com.stray.cat.pojo.Enclosure;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface EnclosureMapper {

    @Select("select * from c_enclosure where enclosure_number=#{enclosureNumber}")
    List<Enclosure> queryByNumber(String enclosureNumber);

    @Delete("delete from c_enclosure where enclosure_id=#{enclosureId}")
    int deleteEnclosure(int enclosureId);

    @Insert("insert into c_enclosure values(#{enclosureId},#{enclosureType},#{enclosureUrl},#{enclosureCreatetime},#{enclosureNumber},#{enclosureSmallUrl},#{enclosureWh})")
    int addEnclosure(Enclosure enclosure);

    @Select("select count(*) from c_enclosure")
    int queryCount();

    @Select("select enclosure_createtime from c_enclosure order by enclosure_createtime desc limit 1")
    Date queryNearDate();

}
