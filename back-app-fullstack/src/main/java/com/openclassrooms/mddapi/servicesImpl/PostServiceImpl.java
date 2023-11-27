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

    /**
     * This method is used to retrieve a post by its id.
     * It retrieves the post from the repository and transforms it to a DTO.
     *
     * @param id This is the id of the post to be retrieved.
     * @return PostDto This returns the DTO of the retrieved post.
     */
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).get();

        return postTransformer.entityToDto(post, true);
    }

    /**
     * This method is used to create a new post.
     * It creates a new post from the provided DTO, saves the post, and returns the
     * DTO of the created post.
     *
     * @param postDto This is the DTO that contains the details of the post.
     * @return PostDto This returns the DTO of the created post.
     */
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDate(postDto.getDate());
        post.setContent(postDto.getContent());
        post.setTopic(postTransformer.dtoToEntity(postDto, false).getTopic());
        post.setUser(postTransformer.dtoToEntity(postDto, false).getUser());

        Post postCreated = postRepository.save(post);
        return postTransformer.entityToDto(postCreated, false);
    }

    /**
     * This method is used to retrieve all posts.
     * It retrieves all posts from the repository, transforms them to DTOs, and
     * returns them.
     * If there are no posts, it returns an empty list.
     *
     * @return List<PostDto> This returns the list of post DTOs.
     */
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
