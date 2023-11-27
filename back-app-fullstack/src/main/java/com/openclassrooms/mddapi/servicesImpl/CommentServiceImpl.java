package com.openclassrooms.mddapi.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.transformers.CommentTransformer;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentTransformer commentTransformer;

    @Autowired
    private PostRepository postRepository;

    /**
     * This method is used to create a new comment for a specific post.
     * It retrieves the post by its id, creates a new comment from the provided DTO,
     * adds the comment to the post, and saves the post.
     *
     * @param commentDto This is the DTO that contains the details of the comment.
     * @param postId     This is the id of the post to which the comment is added.
     */
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

    /**
     * This method is used to retrieve all comments of a specific post.
     * It retrieves the post by its id, gets the comments of the post, and
     * transforms them to DTOs.
     * If there are no comments, it returns an empty list.
     *
     * @param postId This is the id of the post whose comments are retrieved.
     * @return List<CommentDto> This returns the list of comment DTOs.
     */
    @Override
    public List<CommentDto> getAllComments(Long postId) {
        Post post = postRepository.findById(postId).get();
        List<Comment> comments = post.getComments();

        if (!comments.isEmpty()) {
            return comments.stream().map(comment -> commentTransformer.entityToDto(comment))
                    .collect(java.util.stream.Collectors.toList());
        }
        return new ArrayList<>();
    }

}
