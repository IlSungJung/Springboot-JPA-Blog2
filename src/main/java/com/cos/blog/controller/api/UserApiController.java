package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;


//웹 및 앱에도 사용하기 위해 Rest방식으로 구현함.
@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //입력 파라미터가 json이므로, json은 http body이고..
		System.out.println("UserApiController : save 호출됨.");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
		
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
	}

//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) { //입력 파라미터가 json이므로, json은 http body이고..
//		System.out.println("UserApiController : login 호출됨.");
//		// 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
//		
//		User principal = userService.로그인(user);
//		if( principal != null) {
//			session.setAttribute("principal", principal);
//			return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
//		} else {
//			return new ResponseDto<Integer>(HttpStatus.NOT_FOUND.value(), -1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
//		}
//	}

	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) { //입력 파라미터가 json이므로, json은 http body이고..
		System.out.println("UserApiController : update 호출됨.");
		
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값이 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해 줄 것임.
		
		// 세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
	}
}