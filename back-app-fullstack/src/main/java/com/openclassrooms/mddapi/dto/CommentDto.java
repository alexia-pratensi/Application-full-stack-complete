package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private Long id;

	@NotNull
	private Date date;

	@NotBlank
	private String content;

	@NotBlank
	private UserEntityDto user_id;

	// @NotBlank
	// private PostDto post_id;
}
