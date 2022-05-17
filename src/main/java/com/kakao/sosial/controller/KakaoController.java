package com.kakao.sosial.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kakao.sosial.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoController {

	private final MemberService memberService;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping( "/user/kakao/callback" )
	public String kakaoLogin( String code ) {
		// code는 카카오 서버로부터 받은 인가 코드
		log.info("kakaoLogin");
		memberService.kakaoLogin(code);
		return "redirect:/";
	}

}
