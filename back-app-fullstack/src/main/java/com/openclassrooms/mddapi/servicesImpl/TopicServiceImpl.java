package com.openclassrooms.mddapi.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.transformers.TopicTransformer;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicTransformer topicTransformer;

    @Override
    public List<TopicDto> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();

        if (!topics.isEmpty()) {
            return topics.stream().map(topic -> topicTransformer.entityToDto(topic, true))
                    .collect(java.util.stream.Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public TopicDto findById(Long id) {
        Topic topic = topicRepository.findById(id).get();
        return topicTransformer.entityToDto(topic, true);
    }

}
