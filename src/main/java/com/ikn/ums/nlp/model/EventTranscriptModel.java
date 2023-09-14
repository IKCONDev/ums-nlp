package com.ikn.ums.nlp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTranscriptModel {
	
	private Integer eventId;
	private String transcriptContent;

}
