package com.example.controller;

import com.example.entity.Article;
import com.example.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleHandler {
    Calendar now = Calendar.getInstance();


    private final com.example.mapper.ArticleMapper articleMapper;

    public ArticleHandler(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }
    @GetMapping("/all")
    public List<Article> findAll(){
        return articleMapper.findAll();
    }

//    @GetMapping("/{createdDate}")
//    public List<Article> dayAll(@PathVariable("createdDate") Date createdDate){
//        return articleMapper.todayAll();
//    }
    @GetMapping("/list")
    public List<Article> todayAll(){
        return articleMapper.todayAll();
    }

    @GetMapping("/todayCategory/{category}")
    public List<Article> todayCategory(@PathVariable("category") String category){
//        String category = URLDecoder.decode(todayCategory, "UTF-8");
        return articleMapper.todayCategory(category);
    }

    @GetMapping("/id/{id}")
    public Article findById(@PathVariable("id") int id){
        return articleMapper.findById(id);
    }

    @GetMapping("/today/{todayId}")
    public Article todayById(@PathVariable("todayId") int id){
        return articleMapper.todayById(id);
    }

    @PostMapping("/save")
    public String save(Article article){
        article.setCreateDate(new Date());
        articleMapper.save(article);
        return "0";
    }

    @PutMapping("/update")
    public String update(Article article,Model model){
        article.setUpdateDate(new Date());
        articleMapper.update(article);
        Collection<com.example.entity.Article> articles = articleMapper.todayAll();
        model.addAttribute("articles", articles);
        return "0";
    }

//    @DeleteMapping("/delete/{id}")
//    public void delete(@PathVariable("id") int id){
//        articleMapper.deleteArticle(id);
//    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Integer id) {
        articleMapper.deleteArticle(id);
        return "0";
    }
    @PostMapping("/articleCopy/{id}")
    public Article articleCopy(@PathVariable("id") Integer id){
        articleMapper.articleCopy(id);
        int newId = articleMapper.getArticleCopyID();
        articleMapper.updateCategory(newId);
        return articleMapper.findById(newId);
    }
}