package com.ikn.ums.nlp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikn.ums.nlp.VO.Meeting;
import com.ikn.ums.nlp.exception.ControllerException;
import com.ikn.ums.nlp.exception.EmptyListException;
import com.ikn.ums.nlp.exception.ErrorCodeMessages;
import com.ikn.ums.nlp.service.NLPService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/nlp")
@Slf4j
public class NLPController {

	@Autowired
	private NLPService nlpService;
	
	//	getMeetingDetailsForUserId();
	//	getAttendeesDetailsForUserId();
	//	readTranscriptAndGenerateMeetingActionItemsForUserId();
	//	
	//	saveMeetingDetailsForUserId();
	//	saveAttendeesDetailsForUserId();
	//	saveMeetingActionItemsFromTrasncriptForUserId();
		
	//Then present the action items for that particular meeting to user in UI	
	
	@GetMapping("/generate/{email}")
	public ResponseEntity<?> processEventSourceDataToGenerateMeetingDetailsForUserId(@PathVariable("email") String emailId) {
		log.info("NLPController.processEventSourceDataToGenerateMeetingDetailsForUserId() Entered : emailId : " + emailId);
		List<Meeting> meetingsListFromSourceData = new ArrayList<Meeting>();
		
		try {
			// get events from batch processing microservice
			meetingsListFromSourceData = nlpService.getMeetingsListWithAttendeesAndTranscriptForUserId( emailId );
			if (meetingsListFromSourceData.size() == 0) {
				log.info("The retrieved meeting list is empty.");
				throw new EmptyListException(ErrorCodeMessages.ERR_NLP_MSTEAMS_EVENTS_NOT_FOUND_CODE,
						ErrorCodeMessages.ERR_NLP_MSTEAMS_EVENTS_NOT_FOUND_MSG);
			}
			log.info("The Meeting List size for the user id : " + emailId + ", is : " + meetingsListFromSourceData.size());
//			log.info("The Meeting List got retrieved from the  for the user id : " + emailId + ", is : " + meetingsListFromSourceData.size());
//			log.info("The Meeting List size for the user id : " + emailId + ", is : " + meetingsListFromSourceData.size());
			
			// send the events and generate action items from transcript
			nlpService.getActionItemsFromMeetingTranscript(meetingsListFromSourceData, emailId);
			
//			saveMeetingWithAttendeesDetailsForUserId( emailId );
//			saveActionItems
			
//			// send the events and generate action items from transcript
//			nlpService.filterActionItemsFromMeetingTranscript(meetingsListFromSourceData, emailId);
			log.info("NLPController.generateActionItemsForUserMeetings() exited successfully");
			return new ResponseEntity<>(true, HttpStatus.OK);
		} catch (Exception e) {
			log.info("NLPController.generateActionItemsForUserMeetings() exited with exception " + e.getMessage());
			ControllerException umsCE = new ControllerException("error code", e.getStackTrace().toString());
			return new ResponseEntity<>(umsCE, HttpStatus.OK);
		}
	}
	

	/**
	 * generateActionItemsForUserMeetings method retrieves all the Meeting
	 * information and Transcript details of the user logged in.
	 * 
	 * @param emailId
	 * @return
	 */
	@GetMapping("/generate/{email}")
	public ResponseEntity<?> generateActionItemsForUserMeetings(@PathVariable("email") String emailId) {
		log.info("NLPController.generateActionItemsForUserMeetings() Entered : emailId : " + emailId);
		try {
			// get events from batch processing microservice
			System.out.println("NLPController.generateActionItemsForUserMeetings() is under execution...");
			List<Meeting> meetingList = nlpService.getMeetingsListWithAttendeesAndTranscriptForUserId(emailId); // 1. Getting Meetings & 2.
			if (meetingList.size() == 0) {
				log.info("The retrieved meeting list is empty.");
				throw new EmptyListException(ErrorCodeMessages.ERR_NLP_MSTEAMS_EVENTS_NOT_FOUND_CODE,
						ErrorCodeMessages.ERR_NLP_MSTEAMS_EVENTS_NOT_FOUND_MSG);
			}
			log.info("The Meeting List size for the user id : " + emailId + ", is : " + meetingList.size());
			log.info("The Meeting List got retrieved from the  for the user id : " + emailId + ", is : " + meetingList.size());
			log.info("The Meeting List size for the user id : " + emailId + ", is : " + meetingList.size());
			
			
			// send the events and generate action items from transcript
			nlpService.getActionItemsFromMeetingTranscript(meetingList, emailId);
			log.info("NLPController.generateActionItemsForUserMeetings() exited successfully");
			return new ResponseEntity<>(true, HttpStatus.OK);
		} catch (Exception e) {
			log.info("NLPController.generateActionItemsForUserMeetings() exited with exception " + e.getMessage());
			ControllerException umsCE = new ControllerException("error code", e.getStackTrace().toString());
			return new ResponseEntity<>(umsCE, HttpStatus.OK);
		}
	}

	@GetMapping("/getdetails")
	public String getDetails() {
		return "Your NLP MicroService is Running";
	}

	/*
	 * @PostMapping("/create") public ResponseEntity<?>
	 * SendActionItemsToMicroservice(@RequestBody List<ActionItemVO> actionItems){
	 * 
	 * try { String actions =nLPService.SendToAction(actionItems); return new
	 * ResponseEntity<>(actions,HttpStatus.OK);
	 * 
	 * }catch (Exception e) { // TODO: handle exception return new
	 * ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * }
	 * 
	 * @GetMapping("/generate") public ResponseEntity<?>
	 * fetchTransctiptAndGenerateActions() throws FileNotFoundException{
	 * 
	 * try { String data = nLPService.generateActionItems(); return new
	 * ResponseEntity<>(data,HttpStatus.OK); }catch (Exception e) { // TODO: handle
	 * exception return new
	 * ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	 * 
	 * }
	 */

}
