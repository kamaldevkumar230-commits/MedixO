package com.medixo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MedicineController {

	@GetMapping("/search-medicine")
	public List<Map<String,String>> searchMedicine(@RequestParam String keyword){

	    String url =
	    "https://clinicaltables.nlm.nih.gov/api/rxterms/v3/search?terms="
	    + keyword + "&maxList=20";

	    RestTemplate restTemplate = new RestTemplate();

	    Object[] response = restTemplate.getForObject(url, Object[].class);

	    List<Map<String,String>> result = new ArrayList<>();

	    if(response == null) return result;

	    List<List<String>> medicines = (List<List<String>>) response[3];

	    for(List<String> med : medicines){

	        String name = med.get(0);

	        Map<String,String> m = new HashMap<>();

	        m.put("id", name);
	        m.put("text", name);

	        result.add(m);

	    }

	    return result;
	}
}