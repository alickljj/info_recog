package com.cib.applicant.info_recog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class IndexController {
	@GetMapping("/main")
	public String index() {
		return "main";
	}
	@GetMapping("/")
	public String login() {
		return "login";
	}
}
