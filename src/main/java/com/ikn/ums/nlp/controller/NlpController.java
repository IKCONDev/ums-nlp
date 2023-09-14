package com.ikn.ums.nlp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikn.ums.nlp.VO.Event;
import com.ikn.ums.nlp.exception.ControllerException;
import com.ikn.ums.nlp.service.NlpService;

@RestController
@RequestMapping("/nlp")
public class NlpController {
	
	@Autowired
	private NlpService nlpService;
	
	@GetMapping("/generate")
	public ResponseEntity<?> generateActionItemsForEvents(){
		try {
			//get events from batch processing microservice
			List<Event> eventsList = nlpService.getAllEventsWithTranscripts();
			//send the events and generate action items from transcript
			nlpService.generateActionItemsForEvent(eventsList);
			return new ResponseEntity<>(eventsList, HttpStatus.OK);
		}catch (Exception e) {
			ControllerException umsCE = new ControllerException("error code", e.getStackTrace().toString());
			return new ResponseEntity<>(umsCE, HttpStatus.OK);
		}
	}

}
