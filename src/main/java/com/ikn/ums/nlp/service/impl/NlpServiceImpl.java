package com.ikn.ums.nlp.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.ikn.ums.nlp.VO.ActionItemVO;
import com.ikn.ums.nlp.service.NlpService;

@Service
public class NlpServiceImpl implements NlpService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void generateActionItemsFromTranscript() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String SendToAction(List<ActionItemVO> actionItem) {
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

	@Override
	public String generateActionItems() throws FileNotFoundException {
		// TODO Auto-generated method stub
		/*
		 *  Reading the fetched Action Items file
		 */
		ActionItemVO acItems_Data = null;
		InputStream file_Line = new FileInputStream("ActionItems.txt"); 
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
				  int i=0;
				  /*
				   * Iterating the loop to check whether the line is empty
				   */
				  while((acItem=acReader.readLine())!=null) {
					  
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
							  acItems_Data = new ActionItemVO(1, temp_array[j],temp_array[j],null,null,null,null,null);	
							  vo.add(acItems_Data);
						  }
						  
					  }//for loop
					  
				  }//while loop
				  
				  System.out.println(vo);
				  SendToAction(vo);
			        
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			
			}//catch
			
	      
		}
		return  "Created";
	}
   
	
}
