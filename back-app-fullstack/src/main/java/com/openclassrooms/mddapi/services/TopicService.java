package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.dto.TopicDto;

public interface TopicService {

    public List<TopicDto> getAllTopics();

    public TopicDto findById(Long id);

}
