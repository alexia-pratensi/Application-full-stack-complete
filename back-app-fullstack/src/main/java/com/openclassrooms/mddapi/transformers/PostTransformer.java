package com.openclassrooms.mddapi.transformers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repositories.TopicRepository;

/**
 * This class is used to transform Post entities to DTOs and vice versa.
 */
@Component
public class PostTransformer {
    @Autowired
    private TopicRepository topicRepository;

    public PostDto transformEntitytDto(Post post) {
        return entityToDto(post, true);
    }

    public PostDto entityToDto(Post post, boolean withComments) {
        PostDto pDTO = new PostDto();
        pDTO.setId(post.getId());
        pDTO.setTitle(post.getTitle());
        pDTO.setDate(post.getDate());
        pDTO.setContent(post.getContent());

        UserEntityDto userDto = new UserEntityDto();
        userDto.setId(post.getUser().getId());
        userDto.setName(post.getUser().getName());
        userDto.setEmail(post.getUser().getEmail());
        userDto.setPassword(post.getUser().getPassword());

        pDTO.setUser(userDto);

        if (withComments) {
            List<CommentDto> commentDtos = new ArrayList<>();
            for (Comment comment : post.getComments()) {
                CommentDto commentDto = new CommentTransformer().entityToDto(comment);
                commentDtos.add(commentDto);
            }
            pDTO.setComments(commentDtos);
        }

        TopicDto topicDto = new TopicTransformer().entityToDto(post.getTopic(),
                false);
        pDTO.setTopic(topicDto);

        return pDTO;
    }

    public Post transformDtoToEntity(PostDto post) {
        return dtoToEntity(post, true);
    }

    public Post dtoToEntity(PostDto postDto, boolean withComments) {
        Post postEntity = new Post();
        postEntity.setId(postDto.getId());
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDate(postDto.getDate());
        postEntity.setContent(postDto.getContent());

        UserEntity user = new UserEntity();
        user.setId(postDto.getUser().getId());
        user.setName(postDto.getUser().getName());
        user.setEmail(postDto.getUser().getEmail());
        user.setPassword(postDto.getUser().getPassword());

        postEntity.setUser(user);

        if (withComments) {
            List<Comment> comments = new ArrayList<>();
            for (CommentDto commentDto : postDto.getComments()) {
                Comment commentEntity = new CommentTransformer().dtoToEntity(commentDto);
                comments.add(commentEntity);
            }
        }
        Topic topic = topicRepository.findById(postDto.getTopic().getId()).get();
        postEntity.setTopic(topic);

        return postEntity;
    }
}
