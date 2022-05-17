package com.kakao.sosial.info;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoOAuth2 {

	public KakaoUserInfo getUserInfo( String authorizedCode ) {
		System.out.println("getUserInfo 호출");
		String accessToken = getAccessToken( authorizedCode );
		return getUserInfoByToken(accessToken);
	}

	private String getAccessToken( String authorizedCode ) {
		System.out.println("getAccessToken 호출");
		HttpHeaders headers = new HttpHeaders();
		headers.add( "Content-type", "application/x-www-form-urlencoded;charset=utf-8" );

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add( "grant_type", "authorization_code" );
		params.add( "client_id", "f618eb2bbac93215faf21dbcef31f362");
		params.add( "redirect_url", "http://localhost:8080/user/kakao/callback" );
		params.add( "code", authorizedCode );

		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>( params, headers );

		ResponseEntity<String> response = rt.exchange(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
			);

		String tokenJson = response.getBody();
		JSONObject rjson = new JSONObject(tokenJson);
		return rjson.getString( "access_token" );
	}

	private KakaoUserInfo getUserInfoByToken( String accessToken ) {
		HttpHeaders headers = new HttpHeaders();
		headers.add( "Authorization", "Bearer " + accessToken );
		headers.add( "Content-type", "application/x-www-form-urlencoded;charset=utf-8" );

		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>( headers );

		ResponseEntity<String> response = rt.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.POST,
			kakaoProfileRequest,
			String.class
		);

		System.out.println(response.getBody());

		JSONObject body = new JSONObject( response.getBody() );
		Long id = body.getLong( "id" );
		String email = body.getJSONObject( "kakao_account" ).getString( "email" );
		String nickname = body.getJSONObject( "properties" ).getString( "nickname" );

		return new KakaoUserInfo( id, email, nickname );
	}

}
