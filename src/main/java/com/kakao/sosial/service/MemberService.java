package com.kakao.sosial.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.kakao.sosial.info.KakaoOAuth2;
import com.kakao.sosial.info.KakaoUserInfo;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final KakaoOAuth2 kakaoOAuth2;
	private static final String ADMIN_TOKEN = "fd46327bfa2b0ef0cab7ee7537696fda";

	public void kakaoLogin( String authorizedCode ) {
		KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo( authorizedCode );

		Long kakaoId = userInfo.getId();
		String nickname = userInfo.getNickname();
		String email = userInfo.getEmail();

		System.out.println(kakaoId);
		System.out.println(nickname);
		System.out.println(email);

	}


}
