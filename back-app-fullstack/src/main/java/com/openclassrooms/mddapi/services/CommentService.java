package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.models.Comment;

public interface CommentService {

    public Comment createComment(Comment comment);

    public List<Comment> getAllComments();

}
