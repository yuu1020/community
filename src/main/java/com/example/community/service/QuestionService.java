package com.example.community.service;

import com.example.community.dto.QuestionDTO;
import com.example.community.dto.PaginationDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1)page=1;
        if(page>=paginationDTO.getTotalPage())page=paginationDTO.getTotalPage();
        Integer offset =(page-1)*size;
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator().intValue());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Long userId, Integer page, Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1)page=1;
        if(page>=paginationDTO.getTotalPage())page=paginationDTO.getTotalPage();
        Integer offset =(page-1)*size;
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator().intValue());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
    Question question=questionMapper.selectByPrimaryKey(id);
    User user=userMapper.selectByPrimaryKey(question.getCreator().intValue());
    QuestionDTO questionDTO=new QuestionDTO();
    BeanUtils.copyProperties(question,questionDTO);
    questionDTO.setUser(user);
        return questionDTO;
    }

}
