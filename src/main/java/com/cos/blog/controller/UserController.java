package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// 인증이 않되 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 /이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {
	
	// OAuth로그인시 사용하는 암호로, 절대 노출되면 않된다.
	// 노출시, 모든 로그인이 뚤리게 된다. 
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinFrom() {
		
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginFrom() {
		
		return "user/loginForm";
	}
	
	// public @ResponseBody String kakaoCallback(String code) { 
	// @ResponseBody 를 사용시 RestController 처럼 데이터로 리턴할수 있다.
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤로 함수
		// POST 방식으로 key=value 데이터 요청(카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate
		
		//1. 카카오 엑세스 토큰 얻기
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "461c5bdfe5d43a5d5748fe7436e44ffc");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com//oauth/token" //url
				,HttpMethod.POST //method type
				,kakaoTokenRequest //HttpEntity
				,String.class //responseType
		);
		
		//Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		
		// kakao jason 데이터를 java 오브젝트로 변경함.
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰: " + oauthToken.getAccess_token());

		//2. 카카오 엑세스 토큰으로 사용자저보 얻기
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		
		// HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me" //url
				,HttpMethod.POST //method type
				,kakaoProfileRequest2 //HttpEntity
				,String.class //responseType
		);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		
		// kakao jason 데이터를 java 오브젝트로 변경함.
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호): " + kakaoProfile.getId());
		System.out.println("카카오 이메일: " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("카카오 닉네임: " + kakaoProfile.getProperties().getNickname());
		
		System.out.println("블로그서버 유저네임: " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그서버 이메일: " + kakaoProfile.getKakao_account().getEmail());
		//UUID란 중복되지 않는 어떤 특정 값을 만들어 내는 알고리즘
		//UUID garbagePassword = UUID.randomUUID();
		//UUID를 암호는 계속 바뀌기 때문에 로그인처리시 불가함.
		System.out.println("블로그서버 패스워드: " + cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() ==null) {
			System.out.println("신규 회원입니다...");
			userService.회원가입(kakaoUser);
		} else {
			System.out.println("기존 회원입니다...");
		}
		
		// 로그인 처리
		// 세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateFrom() {
		
		return "user/updateForm";
	}
}
