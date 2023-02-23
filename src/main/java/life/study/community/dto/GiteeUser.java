package life.study.community.dto;

import lombok.Data;

@Data
public class GiteeUser {
    private String name;
    private  String id;
    private String bio;
    private String avatarUrl;
}
