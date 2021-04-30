package br.com.quarktech.auction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
public class AuctionController {
	
	@GetMapping("/sale")
	public @ResponseBody String auction() {
		try {
			return "{\"result\": \"OK\"}";
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "{\"result\": \"NOK\"}";
	}

}