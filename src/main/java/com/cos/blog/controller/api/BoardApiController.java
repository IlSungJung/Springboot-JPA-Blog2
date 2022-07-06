package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;


//웹 및 앱에도 사용하기 위해 Rest방식으로 구현함.
@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) { //입력 파라미터가 json이므로, json은 http body이고..
		System.out.println("BoardApiController : save 호출됨.");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
		
		boardService.글쓰기(board, principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
	}

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> delete(@PathVariable int id) { 
		System.out.println("BoardApiController : delete 호출됨.");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
		
		boardService.삭제하기(id);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
	}
	
	@PutMapping("/api/board")
	public ResponseDto<Integer> update(@RequestBody Board board) { 
		System.out.println("BoardApiController : update 호출됨.");
		
		boardService.글수정하기(board);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
	}
	
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) { //입력 파라미터가 json이므로, json은 http body이고..
		System.out.println("BoardApiController : replySave 호출됨.");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
		
		boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId) { //입력 파라미터가 json이므로, json은 http body이고..
		System.out.println("BoardApiController : replyDelete 호출됨.");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
		
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오프젝트를 json으로 변환해서 리턴(Jackson)
	}
}
