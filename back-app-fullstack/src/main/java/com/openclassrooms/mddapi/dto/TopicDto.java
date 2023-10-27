package com.openclassrooms.mddapi.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String title;

	@NotNull
	@Size(max = 300)
	private String description;

	private List<PostDto> posts;

}
