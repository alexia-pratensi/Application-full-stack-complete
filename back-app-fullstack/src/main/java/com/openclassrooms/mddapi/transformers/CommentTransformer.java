package com.openclassrooms.mddapi.transformers;

import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.UserEntity;

@Component
public class CommentTransformer {

    public CommentDto entityToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setDate(comment.getDate());

        UserEntityDto userDto = new UserEntityDto();
        userDto.setId(comment.getUser().getId());
        userDto.setName(comment.getUser().getName());
        userDto.setEmail(comment.getUser().getEmail());
        userDto.setPassword(comment.getUser().getPassword());

        commentDto.setUser_id(userDto);

        // PostDto postDto = new PostTransformer().entityToDto(comment.getPost(),
        // false);
        // commentDto.setPost_id(postDto);

        return commentDto;
    }

    public Comment dtoToEntity(CommentDto commentDto) {
        Comment commentEntity = new Comment();
        commentEntity.setId(commentDto.getId());
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setDate(commentDto.getDate());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(commentDto.getUser_id().getId());
        userEntity.setName(commentDto.getUser_id().getName());
        userEntity.setEmail(commentDto.getUser_id().getEmail());
        userEntity.setPassword(commentDto.getUser_id().getPassword());

        commentEntity.setUser(userEntity);

        // Post postEntity = new PostTransformer().dtoToEntity(commentDto.getPost_id(),
        // false);
        // commentEntity.setPost(postEntity);

        return commentEntity;
    }

}
