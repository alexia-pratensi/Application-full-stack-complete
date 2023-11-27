package com.openclassrooms.mddapi.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.transformers.PostTransformer;
import com.openclassrooms.mddapi.transformers.TopicTransformer;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicTransformer topicTransformer;

    @Autowired
    private PostTransformer postTransformer;

    /**
     * This method is used to retrieve all topics.
     * It retrieves all topics from the repository, transforms them to DTOs, and
     * returns them.
     * If there are no topics, it returns an empty list.
     *
     * @return List<TopicDto> This returns the list of topic DTOs.
     */
    @Override
    public List<TopicDto> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();

        if (!topics.isEmpty()) {
            return topics.stream().map(topic -> topicTransformer.entityToDto(topic, true))
                    .collect(java.util.stream.Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * This method is used to retrieve a topic by its id.
     * It retrieves the topic from the repository and transforms it to a DTO.
     *
     * @param id This is the id of the topic to be retrieved.
     * @return TopicDto This returns the DTO of the retrieved topic.
     */
    @Override
    public TopicDto findById(Long id) {
        Topic topic = topicRepository.findById(id).get();
        return topicTransformer.entityToDto(topic, true);
    }

    /**
     * This method is used to retrieve all posts of a specific topic.
     * It retrieves the topic by its id, gets the posts of the topic, and transforms
     * them to DTOs.
     *
     * @param id This is the id of the topic whose posts are retrieved.
     * @return List<PostDto> This returns the list of post DTOs.
     */
    @Override
    public List<PostDto> findPostsByTopicId(Long id) {
        Topic topic = topicRepository.findById(id).get();
        return topic.getPosts().stream().map(post -> postTransformer.entityToDto(post, true))
                .collect(java.util.stream.Collectors.toList());
    }

}
