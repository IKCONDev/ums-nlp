package com.ikn.ums.nlp.model;

import java.util.Set;

import com.ikn.ums.nlp.VO.Attendee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingTranscriptModel {
	
	private Integer meetingId;
	private Set<Attendee> attendeesList;
	private String transcriptContent;

}
