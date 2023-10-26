package com.openclassrooms.mddapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private Long id;

	@NotBlank
	@Size(max = 50)
	private String title;

	private Long user_id;

	@NotBlank
	private String content;

	private Date date;

	private Long topic_id;

	private LocalDateTime created_at;

	private LocalDateTime updated_at;
}
