package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyController {
	
	@Autowired 
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(Exception e) {
			return "삭제에 실패했습니다. 해야 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id:" + id;
	}
	
	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이탁 없으면 insert를 해요.
	// @RequestBody는 html에서 Json 데이터를 수신 받을 때.
	// json 데이터를 요청=>java object(MessageConvert의 Jackson라이브러리가 변환해서 받아줘.
	@Transactional //함수 종료시에 자동 commit 됨.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{ //람다식
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		//더티 체킹(JPA연속성 컨텍스트에서 변화를 감지)
		return user;
	}
	
	// http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
	//http://localhost:8000/blog/dummy/user?page=0
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		
		return users;
	}
	
	// contents 영역만 리턴
	//http://localhost:8000/blog/dummy/user2?page=0
	@GetMapping("/dummy/user2")
	public List<User> pageList2(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		
		return users;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	//http://localhost:8000/blog/dummy/user/2
	//람다식 ()-> {}
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		
		
		/*//람다식
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
		});
		*/
		
		// 요청: 웹브라우저
		// User 객체 = 자바 오브젝트
		// 변환( 웹브라우저가 이해할 수 있는 데이터) json(Gson 라이브러리)
		// 스프링부트 = MessageConver라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConver가 Jacson 라이브러리르 호출해서
		// User 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join 요청
	// http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
}
