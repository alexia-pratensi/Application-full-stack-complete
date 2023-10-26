package com.openclassrooms.mddapi.servicesImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).get();

        // PostDto postDto = modelMapper.map(post, PostDto.class);
        return post;
    }

    @Override
    public Post createPost(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

}
