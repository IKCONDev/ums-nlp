package com.ikn.ums.nlp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.ikn.ums.nlp.VO.Meeting;
import java.util.List;

import com.ikn.ums.nlp.VO.ActionItemVO;

public interface NLPService {
	
	List<Meeting> getMeetingsListWithAttendeesAndTranscriptForUserId(String userId);
//	void filterActionItemsFromMeetingTranscript(List<Meeting> eventsList, String userId) throws IOException, FileNotFoundException;
	void getActionItemsFromMeetingTranscript(List<Meeting> meetingList, String userId) throws IOException, FileNotFoundException;
	
	//String SendToAction(List<ActionItemVO> actionItem);
	//String  generateActionItems() throws FileNotFoundException;

}
