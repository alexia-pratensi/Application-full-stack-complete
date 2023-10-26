package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.models.Post;

public interface PostService {

    public Post createPost(Post post);

    public Post getPostById(Long id);

    public List<Post> getAllPosts();

}
