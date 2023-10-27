package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.dto.PostDto;

public interface PostService {

    public PostDto createPost(PostDto post);

    public PostDto getPostById(Long id);

    public List<PostDto> getAllPosts();

}
