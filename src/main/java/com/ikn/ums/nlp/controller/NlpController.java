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
import com.ikn.ums.nlp.service.NlpService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/nlp")
@Slf4j
public class NlpController {
	
	@Autowired

	private NlpService nlpService;
	
	@GetMapping("/generate/{email}")
	public ResponseEntity<?> generateActionItemsForUserEvents(@PathVariable String email){
        log.info("NlpController.generateActionItemsForUserEvents() Entered with userId "+email);
		try {
			//get events from batch processing microservice
			System.out.println("NlpController.generateActionItemsForUserEvents() is under execution...");
			List<Event> eventsList = nlpService.getAllEventsWithTranscripts(email);
			//send the events and generate action items from transcript
			nlpService.filterActionItemsFromEventTranscript(eventsList, email);
			log.info("NlpController.generateActionItemsForUserEvents() exited successfully");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}catch (Exception e) {
            log.info("NlpController.generateActionItemsForUserEvents() exited with exception "+e.getMessage());
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
			 String actions =nlpService.SendToAction(actionItems);
			 return new ResponseEntity<>(actions,HttpStatus.OK);
			
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/generate")
	public ResponseEntity<?> fetchTransctiptAndGenerateActions() throws FileNotFoundException{
		
		try {
			String data = nlpService.generateActionItems();
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		*/
	
		
	}
	
