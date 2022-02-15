package com.ftn.TeretanaVebProjekat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/Popust")
public class PopustController {
	
	@GetMapping
	public String index() {
		// prosleđivanje
		return "popust";
	}

}
