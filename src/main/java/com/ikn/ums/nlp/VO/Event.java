package com.ikn.ums.nlp.VO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	@Id
	@SequenceGenerator(name = "events_gen", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "events_gen")
	@Column(name = "Id", nullable = false)
	private Integer id;
	
	@Column(name = "EventId", nullable = false)
	private String eventId;
	
	@Column(name = "CreatedDateTime", nullable = false)
	private String createdDateTime;
	
	@Column(name = "OriginalStartTimeZone")
	private String originalStartTimeZone;
	
	@Column(name = "OriginalEndTimeZone")
	private String originalEndTimeZone;
	
	@Column(name = "Subject")
	private String subject;
	
	@Column(name = "Type")
	private String type;
	
	@Column(name = "OccurrenceId")
	private String occurrenceId;
	
	@Column(name = "StartDateTime")
	private LocalDateTime startDateTime;
	
	private LocalDateTime endDateTime;
	
	private String startTimeZone;
	
	private String endTimeZone;
		
	private String location;
	
    private Set<Attendee> attendees;
    
    private String organizerEmailId;
    
    private String organizerName;
    
	private String onlineMeetingId;
    
	private String onlineMeetingProvider;
	
	private String seriesMasterId;
	
	private String joinUrl;
	
	private List<Transcript> meetingTranscripts;
	                 
	private String insertedBy = "IKCON UMS";
    
    private String insertedDate = LocalDateTime.now().toString();
    
    private Integer userId;
    
    private boolean isActionItemsGenerated = false;

}
