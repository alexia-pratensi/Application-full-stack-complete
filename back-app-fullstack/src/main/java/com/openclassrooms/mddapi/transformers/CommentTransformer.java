package com.openclassrooms.mddapi.transformers;

import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.models.Comment;
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

        commentDto.setUser(userDto);

        return commentDto;
    }

    public Comment dtoToEntity(CommentDto commentDto) {
        Comment commentEntity = new Comment();
        commentEntity.setId(commentDto.getId());
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setDate(commentDto.getDate());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(commentDto.getUser().getId());
        userEntity.setName(commentDto.getUser().getName());
        userEntity.setEmail(commentDto.getUser().getEmail());
        userEntity.setPassword(commentDto.getUser().getPassword());

        commentEntity.setUser(userEntity);

        return commentEntity;
    }

}
