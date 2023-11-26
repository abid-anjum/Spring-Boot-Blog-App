package net.fernandosalas.springbootblogpost.service.implementation;

import lombok.AllArgsConstructor;
import net.fernandosalas.springbootblogpost.entity.Post;
import net.fernandosalas.springbootblogpost.exception.ResourceNotFoundException;
import net.fernandosalas.springbootblogpost.payload.PostDto;
import net.fernandosalas.springbootblogpost.payload.PostResponse;
import net.fernandosalas.springbootblogpost.repository.PostRepository;
import net.fernandosalas.springbootblogpost.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        Post newPost = postRepository.save(post);
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // Create a Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        // Retrieve a page of posts
        Page<Post> postList = postRepository.findAll(pageable);
        List<Post> listOfPosts = postList.getContent();
        List<PostDto> content = listOfPosts.stream()
                .map((post -> modelMapper.map(post, PostDto.class)))
                .toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postList.getNumber());
        postResponse.setPageSize(postList.getSize());
        postResponse.setTotalElements(postList.getTotalElements());
        postResponse.setTotalPages(postList.getTotalPages());
        postResponse.setLast(postList.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatePost = postRepository.save(post);
        return modelMapper.map(updatePost, PostDto.class);
    }

    @Override
    public void deletePostId(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
