package com.example.controller;

import com.example.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/article")
public class Article {
    @Autowired
    HttpSession httpSession;

    @Autowired
    ArticleMapper articleMapper;

    @RequestMapping("/list")
    public String today(Model model) {
        Collection<com.example.entity.Article> articles = articleMapper.todayAll();
        model.addAttribute("articles", articles);
        return "list";
    }
    @RequestMapping("/todayCategory/{category}")
    public String todayCategory(@PathVariable String category,Model model) {
        Collection<com.example.entity.Article> articles = articleMapper.todayCategory(category);
        model.addAttribute("articles", articles);
        return "todayCategory";
    }
    @GetMapping("/add")
    public String add() {
        return "add";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model) {
        com.example.entity.Article article = articleMapper.findById(id);
        model.addAttribute("article", article);
        return "edit";
    }
    @PostMapping("/save")
    public String save(com.example.entity.Article article,Model model){
        article.setCreateDate(new Date());
        articleMapper.save(article);
        Collection<com.example.entity.Article> articles = articleMapper.todayAll();
        model.addAttribute("articles", articles);
        return "redirect:/article/list";
    }

    @PutMapping("/update")
    public String update(com.example.entity.Article article,Model model) {
        article.setUpdateDate(new Date());
        articleMapper.update(article);
        Collection<com.example.entity.Article> articles = articleMapper.todayAll();
        model.addAttribute("articles", articles);
        return "redirect:/article/list";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Integer id) {
        articleMapper.deleteArticle(id);
        return "redirect:/article/list";
    }

    @PostMapping("/articleCopy/{id}")
    public String createCopy(@PathVariable("id") Integer id){
        articleMapper.articleCopy(id);
        return "redirect:/article/list";
    }
}

