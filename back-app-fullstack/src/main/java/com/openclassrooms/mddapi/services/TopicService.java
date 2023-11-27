package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.TopicDto;

public interface TopicService {

    public List<TopicDto> getAllTopics();

    public TopicDto findById(Long id);

    public List<PostDto> findPostsByTopicId(Long id);

}
