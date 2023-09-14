package com.ikn.ums.nlp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.ikn.ums.nlp.VO.Event;
import java.util.List;

import com.ikn.ums.nlp.VO.ActionItemVO;

public interface NlpService {
	
	List<Event> getAllEventsWithTranscripts();
	void generateActionItemsForEvent(List<Event> eventsList) throws IOException, FileNotFoundException;
	void generateActionItemsFromTranscript();
	
	String SendToAction(List<ActionItemVO> actionItem);
	String  generateActionItems() throws FileNotFoundException;

}
