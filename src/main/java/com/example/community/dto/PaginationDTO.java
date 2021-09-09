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
    private Integer totalPage;
    public void setPagination(Integer totalCount,Integer page,Integer size){//话题数 当前页面 每一页显示话题数

        totalPage=totalCount/size;
        if(totalCount%size!=0)totalPage=totalPage+1;
        if(page<1)page=1;
        if(page>=totalPage)page=totalPage;
        this.page=page;
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0)pages.add(0,page-i);
            if(page+i<=totalPage)pages.add(page+i);
        }
        //是否显示<
        if(page==1){
            showPrecious=false;
        }else{
            showPrecious=true;
        }
        //是否显示>
        if(page<=totalPage){
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
