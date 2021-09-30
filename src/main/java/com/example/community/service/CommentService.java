package com.example.community.service;

import com.example.community.dto.CommentDTO;
import com.example.community.enums.CommentTypeEnum;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.*;
import com.example.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Transactional//事务化 此注解下方法视为一个事务
    public void insert(Comment comment){
        if(comment.getParentId()==null||comment.getParentId()==0)
        {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !(CommentTypeEnum.isExist(comment.getType()))){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WORRY);
        }
        if(comment.getType()==CommentTypeEnum.QUESTION.getType()){
            //回复问题
            Question dbquestion=questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbquestion==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }else{//评论数增加
                commentMapper.insertSelective(comment);
                Question record=new Question();
                record.setId(dbquestion.getId());
                record.setCommentCount(1);
                questionExtMapper.incComment(record);
            }
        }else{
            //回复评论
            Comment dbcomment=commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbcomment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }else{
                Comment record=new Comment();
                record.setId(comment.getParentId());
                record.setCommentCount(1);
                commentExtMapper.incComment(record);
                commentMapper.insertSelective(comment);
            }
        }
    }
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample=new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size()==0){
            return new ArrayList<>();
        }
        ArrayList<Integer>userId=new ArrayList<>();//id集合
        Set<Integer>commentators=comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        userId.addAll(commentators);
        UserExample userExample=new UserExample();
        userExample.createCriteria()
                .andIdIn(userId);
        List<User> users = userMapper.selectByExample(userExample);
        //转换成map
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;//流转换
    }
}

