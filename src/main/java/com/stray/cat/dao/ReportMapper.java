package com.stray.cat.dao;

import com.stray.cat.pojo.Report;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReportMapper {

    @Select("select * from c_report")
    List<Report> queryAllReport();

    @Insert("insert into c_report values(#{reportId},#{reportUser},#{reportContent},#{reportFrom},#{reportFromId},#{reportCreatetime})")
    int addReport(Report report);

    @Delete("delete from c_report where report_id=#{reportId}")
    int deleteReport(int reportId);


}
