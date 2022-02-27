package de.boschbewerbung.app.authappjson;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class KeywordSearchController {

	@GetMapping("/keyword")
	public String keywordForm(Model model) {
		model.addAttribute("keyword", new Keyword());
		return "keyword";
	}

	@PostMapping("/keyword")
	public String keywordSubmit(@ModelAttribute Keyword keyword, Model model) {
		model.addAttribute("keyword", keyword);
		
		RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String url = "https://www.google.com/search?q="+keyword.getKeywordValue()+"&num=16";
        ResponseEntity<String> res = rt.exchange(url, HttpMethod.GET, entity, String.class);
        
        

       String str = res.toString();
       Matcher m = Pattern.compile("(<div class=\"BNeawe vvjwJb AP7Wnd\">)((.|\n)*?)(</div>?)").matcher(str);
       
       JSONObject review = new JSONObject();
       
       int index =1;
       while (m.find()&&(index<=10)) {
    	   
           review.put("result "+index , m.group(2));
           index++;
           
       }
       
       keyword.setKeywordValue(review.toString());
		
       return "result";
	}

}
