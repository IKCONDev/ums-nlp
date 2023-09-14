package com.ikn.ums.nlp.VO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionItemVO {
	
	private Integer id;
	
	private String actionTitle;
	
	private String description;
	
	private String actionPriority;
	
	private Integer eventid;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private String actionStatus;
	

}
