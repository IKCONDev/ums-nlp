package com.ikn.ums.nlp.VO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

	private Integer id;
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
    private String organizerEmailId;
    private String organizerName;
	private String onlineMeetingId;
	private String onlineMeetingProvider;
	private String seriesMasterId;
	private String joinUrl;
	private String insertedBy = "IKCON UMS";
    private String insertedDate = LocalDateTime.now().toString();
    private Integer userId;
    private boolean isActionItemsGenerated = false;
	private Set<Attendee> attendees;
	private List<Transcript> meetingTranscripts;

}
