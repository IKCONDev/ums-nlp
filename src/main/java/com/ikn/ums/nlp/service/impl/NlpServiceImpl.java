package com.ikn.ums.nlp.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ikn.ums.nlp.VO.Event;
import com.ikn.ums.nlp.exception.BusinessException;
import com.ikn.ums.nlp.model.EventTranscriptModel;

import org.springframework.http.HttpEntity;

import com.ikn.ums.nlp.VO.ActionItemVO;
import com.ikn.ums.nlp.service.NlpService;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

@Service
@Slf4j
public class NlpServiceImpl implements NlpService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	public void generateActionItemsForEvent(List<Event> eventsList) throws IOException, FileNotFoundException {

		boolean flag = false;
		List<EventTranscriptModel> eventWithTranscriptModelList = getTranscriptsOfEvents(eventsList);
		List<Integer> eventIds = new ArrayList<>();
		// generate action items for each event
		//Integer eventId = 0;
		try {
			eventWithTranscriptModelList.forEach(eventWithTranscript -> {
				int eventId = eventWithTranscript.getEventId();
				String transcriptContent = eventWithTranscript.getTranscriptContent();

				//
				// Fetch keywords from the file
				String keywordsFilePath = "Keywords.txt";
				BufferedReader keywordsReader = null;
				try {
					keywordsReader = new BufferedReader(new FileReader(keywordsFilePath));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<String> keywords = new ArrayList<>();
				String keyword;
				try {
					while ((keyword = keywordsReader.readLine()) != null) {
						keywords.add(keyword.toLowerCase());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					keywordsReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Tokenize the transcript into sentences
				SentenceModel sentenceModel = null;
				try {
					sentenceModel = new SentenceModel(new FileInputStream("en-sent.bin"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
				String[] sentences = sentenceDetector.sentDetect(transcriptContent);

				// Initialize the tokenizer
				TokenizerModel tokenizerModel = null;
				try {
					tokenizerModel = new TokenizerModel(new FileInputStream("en-token.bin"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Tokenizer tokenizer = new TokenizerME(tokenizerModel);

				// Loop through each sentence and look for keywords
				List<String> actionItems = new ArrayList<>();
				for (String sentence : sentences) {
					sentence = sentence.toLowerCase(); // Convert the sentence to lowercase
					//String[] words = tokenizer.tokenize(sentence);

					// Check if any of the keywords are in the sentence
					for (String keyword1 : keywords) {
						if (containsKeyword(sentence, keyword1)) {
							// If a keyword is found, add the sentence to the tasks list
							actionItems.add(sentence);
							break; // No need to check further keywords in this sentence
						}
					}
				}

				// Print the extracted acitems and write them to a file
				String actionsFilePath = "actions-items" + eventId + ".txt";
				FileWriter actionsWriter = null;
				try {
					actionsWriter = new FileWriter(actionsFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String actionitem : actionItems) {
					// Define the regular expression pattern to match text between "<" and ">"
					String pattern = "<[^>]+>";
					// remove all strings between < and > symbols
					String originalActionItem = actionitem.replaceAll(pattern, "");
					System.out.println(originalActionItem);
					try {
						actionsWriter.write(originalActionItem + "\n\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					actionsWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					generateActionItems(actionsFilePath, eventId);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				eventIds.add(eventId);
			});
			flag = true;
			//generate action items and pass the action items to action items microservice
			if(flag) {
				boolean isUpdated = false;
				log.info("action items generated sucessfully for events "+eventIds.toString());
				isUpdated = updateEventActionItemsStatus(eventIds, flag);
				if(isUpdated == false) {
					throw new BusinessException("error code", "error updating status of Event for Action Items");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
			log.info("Action items were not generated due to business exception "+e.getStackTrace().toString());
			throw new BusinessException("error code", e.getStackTrace().toString());
		}
	}

	// Helper method to check if a sentence contains a keyword
	private static boolean containsKeyword(String words, String keyword) {
		// for (String word : words) {
		if (words.contains(keyword)) {
			return true;
		}
		// }
		return false;
	}

	private List<EventTranscriptModel> getTranscriptsOfEvents(List<Event> eventsList) {
		List<EventTranscriptModel> eventWithTranscriptModelList = new ArrayList<>();
		StringBuilder transcriptContentBuilder = new StringBuilder();
		eventsList.forEach(event -> {

			// if event contains transcript fetch it and provide it to NLP for generating
			// action items
			if (event.getMeetingTranscripts().size() > 0) {
				event.getMeetingTranscripts().forEach(transcript -> {
					transcriptContentBuilder.append(transcript.getTranscriptContent());

					// This object contains the event id and its transcript content
					EventTranscriptModel transcriptOfEvent = new EventTranscriptModel();
					transcriptOfEvent.setEventId(event.getId());
					transcriptOfEvent.setTranscriptContent(transcriptContentBuilder.toString());
					// System.out.println(transcriptOfEvent);
					// add the object to list
					eventWithTranscriptModelList.add(transcriptOfEvent);
				});
			} // if
		});// foreach
		System.out.println("------>" + eventWithTranscriptModelList);
		return eventWithTranscriptModelList;
	}

	// get the events with trasncripts for which the action items are not generated
	@Override
	public List<Event> getAllEventsWithTranscripts() {
		log.info("NlpServiceImpl.getAllEventsWithTranscripts()");
		ResponseEntity<List<Event>> response = restTemplate.exchange("http://UMS-BATCH-SERVICE/teams/events",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Event>>() {
				});
		if (response.getBody() == null) {
			throw new BusinessException("error code", "Events List is null");
		}
		return response.getBody();
	}

	private String SendToAction(List<ActionItemVO> actionItem) {
		// TODO Auto-generated method stub
		
		try {
			String actionItemURL = "http://UMS-ACTIONITEMS-SERVICE/api/actions/generate-actions";
			HttpEntity<?> hEntity = new HttpEntity<>(actionItem,null);
			ResponseEntity<Boolean> responseEntity =  restTemplate.exchange(actionItemURL, HttpMethod.POST, hEntity,Boolean.class);
			//List<ActionItemVO> actions = responseEntity.getBody();
			//System.out.println(responseEntity.getBody());
			//List<ActionItemVO> ac = responseEntity.getBody();
			String res = "Created ActionItems in DB";
			return res;
		}catch (Exception e) {
			// TODO: handle exception
		    e.printStackTrace();
		    return e.getMessage();
					
		}
	
	}
	
	private boolean generateActionItems(String actionItemsFilePath, Integer eventId) throws FileNotFoundException {
		// TODO Auto-generated method stub
		/*
		 *  Reading the fetched Action Items file
		 */
		boolean flag = false;
		ActionItemVO acItems_Data = null;
		InputStream file_Line = new FileInputStream(actionItemsFilePath); 
		List<ActionItemVO> vo = new ArrayList<ActionItemVO>();
		if(file_Line!=null) {
			BufferedReader acReader = null;
			String acItem = null;
			String[] temp_array = null;
			String actionLine = null;
			try {
				  /*
				   *  reading the bytes to characters
				   */
				  acReader = new BufferedReader(new InputStreamReader(file_Line));
				  System.out.println(acReader.toString());
				  /*
				   * Iterating the loop to check whether the line is empty
				   */
				  while((acItem=acReader.readLine())!=null && acReader.readLine() != "") {
					  
					  String temp= acItem;
					  /*
					   *  checking whether the line contains any conjunction and
					   */
					  if(temp.toLowerCase().contains("and")) {
						  
						  //if the line contains and split into two sentences
						  temp_array=temp.split("and");
						   
					  }
					  else {
						  temp_array = new String[] {temp};
					  }
					  
					  //Iterating the loop for the Action Items
					  for(int j=0; j<temp_array.length;j++) {
						  actionLine = temp_array[j];
						  if(actionLine.isEmpty() == false) {
							  acItems_Data = new ActionItemVO(null, temp_array[j],temp_array[j],null,eventId,LocalDateTime.now(),null,"NotConverted");	
							  vo.add(acItems_Data);
						  }
						  
					  }//for loop
					  
				  }//while loop
				  
				  System.out.println(vo);
				  //send generated action item from the transcript file to action items microservice and save in db
				  SendToAction(vo);
				  
				  //close the reader
			      acReader.close();
			      
			}catch (Exception e) {
				log.info("Error while generating action items for event "+eventId+" "+e.getStackTrace().toString());
				throw new BusinessException("error code", e.getStackTrace().toString());
			}//catch
		}
		try {
			file_Line.close();
		}catch (IOException e) {
			log.info("input stream closed");
		}
		flag = true;
		//if action items are generated, update the status of event to true for actionItemsGenerated
		return  flag;
		
	}
	
	
	private boolean updateEventActionItemsStatus(List<Integer> eventids, boolean isActionItemsGenerated) {
		//communicate with batch processing microservice and set the status
		boolean flag = false;
		String eventIds = eventids.toString();
		String batchProcessUrl = "http://UMS-BATCH-SERVICE/teams/events/status/"+eventIds+"/"+isActionItemsGenerated;
		int isUpdated = restTemplate.getForObject(batchProcessUrl, Integer.class);
		System.out.println(isUpdated);
		return flag = true;
	}
   
}
