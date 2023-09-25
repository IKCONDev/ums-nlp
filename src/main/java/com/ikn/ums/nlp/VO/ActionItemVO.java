package com.ikn.ums.nlp.VO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionItemVO {
	
	private Integer actionItemId;
	private String eventId;
	private String meetingId;
	private String emailId;
	private String actionItemOwner;
	private String actionItemTitle;
	private String actionItemDescription;
	private String actionPriority;
	private String actionStatus;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
}
