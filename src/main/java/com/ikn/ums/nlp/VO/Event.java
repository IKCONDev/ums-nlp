package com.ikn.ums.nlp.VO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	
	private Long meetingId;
	
	private String eventId;
	
	private String createdDateTime;
	
	private String originalStartTimeZone;
	
	private String originalEndTimeZone;
	
	private String subject;
	
	private String type;
	
	private String occurrenceId;
	
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
	
	/*
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "recurrence_fk_id", referencedColumnName = "id", unique = true, nullable = true)
	private Recurrence recurrence;
	*/
	
	private String seriesMasterId;
	
	private String joinUrl;
	
	private List<Transcript> meetingTranscripts;
	   
	private String insertedBy = "IKCON UMS";
    
    private String insertedDate = LocalDateTime.now().toString();
    
    private String emailId;
    
    private boolean isActionItemsGenerated = false;

    private Long batchId;
}
