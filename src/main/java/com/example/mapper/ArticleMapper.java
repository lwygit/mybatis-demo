package com.example.mapper;

import com.example.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {

    /**
     * 查询所有文章
     *
     * @return 文章
     */
    @Select("select * from article")
    List<Article> findAll();

    /**
     * 增加一篇文章
     *
     * @param article
     */
    @Insert("insert into article(todayId,title,columns,describes,content,category,author,editor,updateBy,createDate) values" +
            "(#{todayId},#{title},#{columns},#{describes},#{content},#{category},#{author},#{editor},#{updateBy},now())")
    void save(Article article);

    /**
     * 通过id删除一篇文章
     *
     * @param id
     */
    @Delete("delete from article where id = #{id}")
    void deleteArticle(int id);

    /**
     * 根据id修改文章
     *
     * @param article
     */

    @Update("update article set todayId=#{todayId},title=#{title},columns=#{columns},describes=#{describes},content=#{content},category=#{category},status=#{status}," +
            "author=#{author},editor=#{editor},updateBy=#{updateBy},updateDate=now(),status=#{status} where id = #{id}")
    void update(Article article);


    /**
     * 查询今日文章
     *
     * @return 文章
     */
    @Select("select * from article where TO_DAYS(createDate) = TO_DAYS(NOW()) ORDER BY category,todayId")
    List<Article> todayAll();

    /**
     * 查询今日分类文章
     *
     * @return 文章
     */
    @Select("select * from article where TO_DAYS(createDate) = TO_DAYS(NOW()) and category = #{category} ORDER BY category,todayId")
    List<Article> todayCategory(String category);

    /**
     * 通过id查询文章
     *
     * @param id
     * @return 文章
     */
    @Select("select * from article where id = #{id}")
    Article findById(int id);

    @Select("select * from article where TO_DAYS(createDate) = TO_DAYS(NOW()) and todayId = #{todayId}")
    Article todayById(int id);

    @Insert("insert into article(todayId,title,columns,describes,content,category,author,editor,updateBy,createDate) " +
            "select todayId,title,columns,describes,content,category,author,editor,updateBy,createDate from article " +
            "where id=#{id}")
    void articleCopy(int id);

    @Select("SELECT @@IDENTITY")
    int getArticleCopyID();
}
