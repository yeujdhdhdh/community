package life.study.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDto {
    private List<QuestionDto> questionDtos;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;
    private Integer thisPage;
    private List<Integer> thisPages=new ArrayList<>();
    private Integer totalPage;
    private Integer prePage;
    private Integer nextPage;
    public void setPage(Integer totalCount, Integer page, Integer size) {


        if(totalCount%size!=0){
            totalPage=totalCount/size+1;
        }else if (totalCount%size==0){
            totalPage=totalCount/size;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        this.thisPage=page;
        this.prePage=page-1;
        this.nextPage=page+1;
        thisPages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                thisPages.add(0,page-i);
            }
            if (page+i<=totalPage){
                thisPages.add(page+i);
            }
        }
        if (page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        if(page==totalPage){
            showNextPage=false;
        }else {
            showNextPage=true;
        }
        //是否展示首页
        if (thisPages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //是否展示末尾页
        if (thisPages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
