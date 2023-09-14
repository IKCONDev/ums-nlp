package com.ikn.ums.nlp.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikn.ums.nlp.VO.ActionItemVO;
import com.ikn.ums.nlp.service.NlpService;

@RestController
@RequestMapping("/nlp")
public class NlpController {
	
	@Autowired
	private NlpService service;
	
	@GetMapping("/getdetails")
	public String getDetails() {
		return "Your NLP MicroService is Running";
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<?> SendActionItemsToMicroservice(@RequestBody List<ActionItemVO> actionItems){
		
		try {
			 String actions =service.SendToAction(actionItems);
			 return new ResponseEntity<>(actions,HttpStatus.OK);
			
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/generate")
	public ResponseEntity<?> fetchTransctiptAndGenerateActions() throws FileNotFoundException{
		
		try {
			String data = service.generateActionItems();
			return new ResponseEntity<>(data,HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	
		
	}
	
	

}
