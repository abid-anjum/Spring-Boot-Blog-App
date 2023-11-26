package net.fernandosalas.springbootblogpost.payload;

import lombok.Data;

@Data
public class PostDto {
    private long id;

    private String title;

    private String description;

    private String content;
}
