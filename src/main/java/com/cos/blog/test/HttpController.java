package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

	//인터넷 브라우져 요청은 get만 가능하다.
	//http://localhost:8080/http/get(select)
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id, @RequestParam String username) {
		return "get 요청 : " + id + ", " + username;
	}
	
	//http://localhost:8080/http/post(insert)
	@PostMapping("/http/post")
	public String postTest() {
		return "post 요청";
	}
	
	//http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	
	//http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}

}
