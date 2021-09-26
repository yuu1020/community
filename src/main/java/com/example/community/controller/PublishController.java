package com.example.community.controller;

import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish()
    {
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id")Integer id,
                       Model model){
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id.longValue());
        Question question=new Question();
        question= questionMapper.selectByPrimaryKey(id.longValue());
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        return "/publish";
    }
    @PostMapping("/publish")
    public String doPublish(
        @RequestParam(value="title",required = false) String title,
        @RequestParam(value="description",required = false) String description,
        @RequestParam(value="tag",required = false) String tag,
        @RequestParam(value = "id",required = false)Long id,
        HttpServletRequest request,
        Model model
        ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
           return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId().longValue());
        question.setGmtModified(System.currentTimeMillis());
        question.setId(id);
        if(question.getId()!=null) {
            QuestionExample questionExample=new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int update=questionMapper.updateByExampleSelective(question,questionExample);
            if(update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
        else {
            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.insertSelective(question);
        }
        return "redirect:/";
    }
}
