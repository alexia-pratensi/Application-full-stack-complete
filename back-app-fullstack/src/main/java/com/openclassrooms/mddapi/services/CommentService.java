package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.dto.CommentDto;

public interface CommentService {

    public CommentDto createComment(CommentDto comment);

    public List<CommentDto> getAllComments();

}
