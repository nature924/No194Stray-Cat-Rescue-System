package com.stray.cat.dao;

import com.stray.cat.pojo.Sentence;
import com.stray.cat.vo.CommentVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SentenceMapper {

    @Select("select * from c_sentence")
    List<Sentence> queryAllSentence();

    @Insert("insert into c_sentence values(#{sentenceId},#{sentenceC},#{sentenceE},#{sentenceZ})")
    int addSentence(Sentence sentence);

    @Delete("delete from c_sentence where sentence_id=#{sentenceId}")
    int deleteSentence(int sentenceId);

}
