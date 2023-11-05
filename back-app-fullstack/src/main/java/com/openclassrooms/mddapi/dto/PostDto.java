package com.openclassrooms.mddapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private Long id;

	@NotBlank
	@Size(max = 50)
	private String title;

	private UserEntityDto user;

	private Date date;

	@NotBlank
	private String content;

	private TopicDto topic;

	private List<CommentDto> comments = new ArrayList<>();

}
