package life.study.community.dto;

import lombok.Data;

@Data
public class CommenCreateDto {
    private Integer parentId;
    private String content;
    private Integer type;

}
