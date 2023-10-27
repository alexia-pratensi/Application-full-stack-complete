package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.dto.CommentDto;

public interface CommentService {

    public void createComment(CommentDto comment, Long postId);

    public List<CommentDto> getAllComments();

}
