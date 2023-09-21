package com.ikn.ums.nlp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ikn.ums.nlp.VO.Event;
import com.ikn.ums.nlp.exception.ControllerException;
import com.ikn.ums.nlp.service.NLPService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/nlp")
@Slf4j
public class NLPController {
	
	@Autowired
	private NLPService nLPService;
	
	@GetMapping("/generate/{email}")
	public ResponseEntity<?> generateActionItemsForUserEvents(@PathVariable String email){
        log.info("NLPController.generateActionItemsForUserEvents() Entered with userId "+email);
		try {
			//get events from batch processing microservice
			System.out.println("NLPController.generateActionItemsForUserEvents() is under execution...");
			List<Event> eventsList = nLPService.getAllEventsWithTranscripts(email);  // 1. Getting Meetings & 2. Transcripts
			//send the events and generate action items from transcript
			nLPService.filterActionItemsFromEventTranscript(eventsList, email);
			log.info("NLPController.generateActionItemsForUserEvents() exited successfully");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}catch (Exception e) {
            log.info("NLPController.generateActionItemsForUserEvents() exited with exception "+e.getMessage());
			ControllerException umsCE = new ControllerException("error code", e.getStackTrace().toString());
			return new ResponseEntity<>(umsCE, HttpStatus.OK);
		}
	}

	@GetMapping("/getdetails")
	public String getDetails() {
		return "Your NLP MicroService is Running";
	}
	
	/*
	@PostMapping("/create")
	public ResponseEntity<?> SendActionItemsToMicroservice(@RequestBody List<ActionItemVO> actionItems){
		
		try {
			 String actions =nLPService.SendToAction(actionItems);
			 return new ResponseEntity<>(actions,HttpStatus.OK);
			
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/generate")
	public ResponseEntity<?> fetchTransctiptAndGenerateActions() throws FileNotFoundException{
		
		try {
			String data = nLPService.generateActionItems();
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		*/
	
		
	}
	
