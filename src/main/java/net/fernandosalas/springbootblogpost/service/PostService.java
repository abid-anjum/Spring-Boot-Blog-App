package net.fernandosalas.springbootblogpost.service;

import net.fernandosalas.springbootblogpost.payload.PostDto;
import net.fernandosalas.springbootblogpost.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostId(long id);
}
