package com.openclassrooms.mddapi.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.transformers.PostTransformer;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostTransformer postTransformer;

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).get();

        return postTransformer.entityToDto(post, true);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDate(postDto.getDate());
        post.setContent(postDto.getContent());
        post.setTopic(postTransformer.dtoToEntity(postDto, true).getTopic());
        post.setUser(postTransformer.dtoToEntity(postDto, true).getUser());

        Post postCreated = postRepository.save(post);
        return postTransformer.entityToDto(postCreated, true);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        if (!posts.isEmpty()) {
            return posts.stream().map(post -> postTransformer.entityToDto(post, true))
                    .collect(java.util.stream.Collectors.toList());
        }
        return new ArrayList<>();
    }

}
