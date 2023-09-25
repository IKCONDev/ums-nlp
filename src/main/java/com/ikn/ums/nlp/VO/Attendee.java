package com.ikn.ums.nlp.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendee {

	private Integer id;
	private String type;
	private String status;
	private String email;
    private Meeting meeting;
	private Integer userId;

}
