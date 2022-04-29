package com.example.practice.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.practice.models.Customer;
import com.example.practice.services.CustomerService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cliftonlabs.json_simple.JsonObject;

@RestController
public class jsonController {

	@Autowired
	CustomerService cser;

	@PostMapping(value = "/jsobj")
	public void jsonFiletojavaObject(@RequestPart(value = "customerjson") MultipartFile jsonFiles[])
			throws StreamReadException, DatabindException, IOException, ParseException {

//		System.out.println(jsonFiles[0].getName());
//		System.out.println(jsonFiles[1].getName());

		JSONParser parser = new JSONParser();
		String[] compeData = new String[12];
        JSONObject[] jsonObjs = new JSONObject[10];
        JSONArray customerListcombined = new JSONArray();

        for (int i = 0; i < jsonFiles.length; i++) {
			byte[] bytes = jsonFiles[i].getBytes();
			compeData[i] = new String(bytes);
			//System.out.println(compeData[i]);
			 jsonObjs[i] = (JSONObject) parser.parse(compeData[i]);
			 //System.out.println(jsonObjs[i]);
			 customerListcombined.add(jsonObjs[i]);
		}
        System.out.println(customerListcombined);
        
        try (FileWriter file = new FileWriter("customers.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(customerListcombined.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        try (FileReader reader = new FileReader("customers.json"))
        {
            //Read JSON file
            Object obj = parser.parse(reader);
 
            JSONArray customerlistfromfile = (JSONArray) obj;
            //System.out.println(customerlistfromfile);
             
           
            List<Customer> cuslist = new ArrayList<>();
            for(int k=0;k<customerlistfromfile.size();k++) {
            	JSONObject cusjsonObj = (JSONObject) customerlistfromfile.get(k);
            	Customer cus = new Customer();
            	cus.setId((Long)cusjsonObj.get("id"));
            	cus.setName((String)cusjsonObj.get("name"));
            	cus.setEmail((String)cusjsonObj.get("email"));
            	cus.setPassword((String)cusjsonObj.get("password"));
            	
            	cuslist.add(cus);
            }
            
            cser.saveAll(cuslist);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // InputStream inJson =
		// Customer.class.getResourceAsStream("/json./customer.json");
//			
//				Customer cs = new ObjectMapper().readValue(inJson, Customer.class);
//				System.out.println(cs);
//			    Customer customer = new Customer();
//			    
//			    customer.setId(cs.getId());
//			    customer.setName(cs.getName());
//			    customer.setEmail(cs.getEmail());
//			    customer.setPassword(cs.getPassword());
//			    
//			    cser.save(customer);

		/*
		 * TypeReference<List<Customer>> typeref = new TypeReference<List<Customer>>() {
		 * }; ObjectMapper mapper = new ObjectMapper();
		 * 
		 * List<Customer> cslist = mapper.readValue(inJson, typeref);
		 * 
		 * for( Customer c : cslist) { System.out.println(c); }
		 * 
		 * cser.saveAll(cslist);
		 */

//		JSONParser parser = new JSONParser();
//		
//			Object obj = parser.parse(new FileReader("/json./customer.json"));
// 
//			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
//			JSONObject jsonObject = (JSONObject) obj;
//			System.out.println(jsonObject);
//		

//		JSONParser jsparser = new JSONParser();
//		if(!jsonFile.isEmpty()) {
//			try {
//				byte[] bytes = jsonFile.getBytes();
//				String completeData = new String(bytes);
//			
//					System.out.println(completeData);
//		
//				
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//		}

//		if(jsonFile != null) {
//			System.out.println("file came");
//		}
//		else {
//			System.out.println("file not found");
//		}
//		try {
//			//Reader reader = Files.newBufferedReader("customer.json");
//			Object obj = jsparser.parse(new FileReader("src/main/resources/json/customer.json"));
//			JsonObject jsobjparser =  (JsonObject) obj;
//			
//			int id = (int) jsobjparser.get("id");
//			String name = (String) jsobjparser.get("name");
//			String email = (String) jsobjparser.get("email");
//			String pwd = (String) jsobjparser.get("password");
//			
//			System.out.println(id+"    "+name+"    "+email+"   "+pwd);
//			
//		}
//		catch (Exception e) {
//			System.out.println("file not found");
//	}

	}


