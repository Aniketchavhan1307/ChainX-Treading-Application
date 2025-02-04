package com.treading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController 
{
	@GetMapping
	public String home()
	{
		return "Welcome to treading plateform";
	}
	
	@GetMapping("/api")
	public String secure()
	{
		return "Api";
	}

}
