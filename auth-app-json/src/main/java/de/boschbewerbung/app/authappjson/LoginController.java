package de.boschbewerbung.app.authappjson;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {
	
	@GetMapping("/")
	public String loginForm(Model model) {
		model.addAttribute("anmelden", new Authenticate());
		return "anmelden";
	}
	
	@PostMapping("/anmelden")
	public String keywordSubmit(@ModelAttribute Authenticate anmelden, Model model) {
	    model.addAttribute("anmelden", anmelden);
		if (anmelden.getBenutzername().equals("admin") && anmelden.getPasswort().equals("Bosch"))
		{
			model.addAttribute("keyword", new Keyword());
			return "keyword";
		}
		else
		{
			return "anmelden";
		}
			
	}
}
