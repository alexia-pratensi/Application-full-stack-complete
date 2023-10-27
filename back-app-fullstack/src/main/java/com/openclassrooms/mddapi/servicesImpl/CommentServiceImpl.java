package com.openclassrooms.mddapi.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.transformers.CommentTransformer;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentTransformer commentTransformer;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void createComment(CommentDto commentDto, Long postId) {
        Post post = postRepository.findById(postId).get();

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setDate(commentDto.getDate());
        comment.setUser(commentTransformer.dtoToEntity(commentDto).getUser());

        post.getComments().add(comment);
        postRepository.save(post);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();

        if (!comments.isEmpty()) {
            return comments.stream().map(comment -> commentTransformer.entityToDto(comment))
                    .collect(java.util.stream.Collectors.toList());
        }
        return new ArrayList<>();
    }

}
