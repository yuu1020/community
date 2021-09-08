package com.example.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO>questions;
    private boolean showPrecious;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    public void setPagination(Integer totalCount,Integer page,Integer size){//话题数 当前页面 每一页显示话题数
        Integer totalPage;
        totalPage=totalCount/size;
        if(totalCount%size!=0)totalPage=totalPage+1;

        for(int i=0;i<3;i++){
            if(page-i>0)pages.add(page);
            if(page+i<=totalCount)pages.add(page+i);
        }


        //是否显示<
        if(page==1){
            showPrecious=false;
        }else{
            showPrecious=true;
        }
        //是否显示>
        if(totalCount==totalPage){
            showNextPage=false;
        }else{
            showNextPage=true;
        }
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }


    }
}
