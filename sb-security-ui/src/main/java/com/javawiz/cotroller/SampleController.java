package com.javawiz.cotroller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SampleController {
	public String getHomePage(Model model, Principal p) {
		return "home";
	}
	
	@GetMapping("/actuatorHome")
	public String getActuatorHome(Model model, Principal p) {
		return "actuatorHome";
	}
}
