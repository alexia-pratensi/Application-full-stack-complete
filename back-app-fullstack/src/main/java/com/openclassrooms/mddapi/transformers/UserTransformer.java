package com.openclassrooms.mddapi.transformers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;

@Component
public class UserTransformer {

    public UserEntityDto transformEntitytDto(UserEntity user) {
        return entityToDto(user, true);
    }

    public UserEntityDto entityToDto(UserEntity user, boolean withTopics) {
        UserEntityDto userDto = new UserEntityDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        if (withTopics) {
            List<TopicDto> topicDtos = new ArrayList<>();
            for (Topic topic : user.getTopics()) {
                TopicDto topicDto = new TopicTransformer().entityToDto(topic, false);
                topicDtos.add(topicDto);
            }
            userDto.setTopics(topicDtos);
        }

        return userDto;
    }

    public UserEntity transformDtoToEntity(UserEntityDto userDto) {
        return dtoToEntity(userDto, true);
    }

    public UserEntity dtoToEntity(UserEntityDto userDto, boolean withTopics) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());

        if (withTopics) {
            List<Topic> topics = new ArrayList<>();
            for (TopicDto topicDto : userDto.getTopics()) {
                Topic topic = new TopicTransformer().dtoToEntity(topicDto, false);
                topics.add(topic);
            }
            userEntity.setTopics(topics);
        }

        return userEntity;
    }

}
