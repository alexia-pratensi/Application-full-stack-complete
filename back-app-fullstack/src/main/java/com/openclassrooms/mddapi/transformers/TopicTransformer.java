package com.openclassrooms.mddapi.transformers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;

/**
 * This class is used to transform Topic entities to DTOs and vice versa.
 */
@Component
public class TopicTransformer {

    public TopicDto transformEntitytoDto(Topic topic) {
        return entityToDto(topic, true);
    }

    public TopicDto entityToDto(Topic topic, boolean withPosts) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setTitle(topic.getTitle());
        topicDto.setDescription(topic.getDescription());

        if (withPosts) {
            List<PostDto> postDtos = new ArrayList<>();
            for (Post post : topic.getPosts()) {
                PostDto postDto = new PostTransformer().entityToDto(post, false);
                postDtos.add(postDto);
            }
            topicDto.setPosts(postDtos);
        }
        return topicDto;
    }

    public Topic transformDtoToEntity(TopicDto topicDto) {
        return dtoToEntity(topicDto, true);
    }

    public Topic dtoToEntity(TopicDto topicDto, boolean withPosts) {
        Topic topicEntity = new Topic();
        topicEntity.setId(topicDto.getId());
        topicEntity.setTitle(topicDto.getTitle());
        topicEntity.setDescription(topicDto.getDescription());

        if (withPosts) {
            List<Post> posts = new ArrayList<>();
            for (PostDto postDto : topicDto.getPosts()) {
                Post postEntity = new PostTransformer().dtoToEntity(postDto, false);
                posts.add(postEntity);
            }
            topicEntity.setPosts(posts);
        }

        return topicEntity;
    }

}
