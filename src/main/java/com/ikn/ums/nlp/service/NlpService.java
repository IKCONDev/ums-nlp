package com.ikn.ums.nlp.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.ikn.ums.nlp.VO.ActionItemVO;

public interface NlpService {
	
	void generateActionItemsFromTranscript();
	
	String SendToAction(List<ActionItemVO> actionItem);
	
	String  generateActionItems() throws FileNotFoundException;

}
