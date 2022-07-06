/**
 * 
 */

let index = {
	init: function() {
		$("#btn-save").on("click", ()=> { //function()을 사용하지 않는 이유는 this를 바인딩하기 위해서 ()=>{}을 하용함.
			//index.save(); //function()일 경우 this가 window object임, 따라서 index로 save함수를 호출할수 있다.
			this.save();
		});
		$("#btn-delete").on("click", ()=> { //function()을 사용하지 않는 이유는 this를 바인딩하기 위해서 ()=>{}을 하용함.
			//index.save(); //function()일 경우 this가 window object임, 따라서 index로 save함수를 호출할수 있다.
			this.deleteById();
		});
		$("#btn-update").on("click", ()=> { //function()을 사용하지 않는 이유는 this를 바인딩하기 위해서 ()=>{}을 하용함.
			//index.save(); //function()일 경우 this가 window object임, 따라서 index로 save함수를 호출할수 있다.
			this.update();
		});
		$("#btn-reply-save").on("click", ()=> { //function()을 사용하지 않는 이유는 this를 바인딩하기 위해서 ()=>{}을 하용함.
			//index.save(); //function()일 경우 this가 window object임, 따라서 index로 save함수를 호출할수 있다.
			this.replySave();
		});
	}
	, save: function() {
		console.log("board의 save함수 호출됨.");
		
		let data = {
			title: $('#title').val()
			,content: $('#content').val()
		}
		
		$.ajax({
			type: "POST"
			,url: "/api/board"
			,data: JSON.stringify(data) //http body데이터
			,contentType: "application/json; charset=utf-8" //body데이터를 보낼때는 contentType을 알려 줘야 한다.
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			if( resp.status == 200 ) {
				alert("글쓰기가 완료되었습니다.");
				location.href = "/";
			} else {
				alert("글쓰기가 실패하였습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}

	, deleteById: function() {
		if(!confirm("삭제하시겠습니다?")) {
			return;
		}
		console.log("board의 delete함수 호출됨.");
		
		var id = $("#id").text();
		
		console.log("id=", id);
		
		$.ajax({
			type: "DELETE"
			,url: "/api/board/" + id
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			if( resp.status == 200 ) {
				alert("글이 삭제되었습니다.");
				location.href = "/";
			} else {
				alert("글삭제가 실패하였습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}
	, update: function() {
		console.log("board의 update함수 호출됨.");
		
		let data = {
			id: $('#id').val()
			,title: $('#title').val()
			,content: $('#content').val()
		}
		
		console.log(data);
		
		$.ajax({
			type: "Put"
			,url: "/api/board"
			,data: JSON.stringify(data) //http body데이터
			,contentType: "application/json; charset=utf-8" //body데이터를 보낼때는 contentType을 알려 줘야 한다.
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			if( resp.status == 200 ) {
				alert("글쓰기가 수정되었습니다.");
				location.href = "/";
			} else {
				alert("글쓰기가 수정하기가 실패했습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}
	, replySave: function() {
		console.log("reply의 save함수 호출됨.");
		
		let data = {
			userId: $('#userId').val()
			,boardId: $('#boardId').val()
			,content: $('#reply-content').val()
		}
		
		console.log(data);
		
		$.ajax({
			type: "POST"
			,url: `/api/board/${data.boardId}/reply` //이렇게하면 javascript의 변수가 문자열에 들어감.
			,data: JSON.stringify(data) //http body데이터
			,contentType: "application/json; charset=utf-8" //body데이터를 보낼때는 contentType을 알려 줘야 한다.
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			if( resp.status == 200 ) {
				alert("댓글작성이 완료되었습니다.");
				location.href = `/board/${data.boardId}`;
			} else {
				alert("댓글작성이 실패하였습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}
	, replyDelete: function(boardId, replyId) {
		console.log("reply의 delete함수 호출됨.");
		console.log("boardId = ", boardId);
		console.log("replyId = ", replyId);
		
		$.ajax({
			type: "DELETE"
			,url: `/api/board/${boardId}/reply/${replyId}` //이렇게하면 javascript의 변수가 문자열에 들어감.
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			if( resp.status == 200 ) {
				alert("댓글삭제가 완료되었습니다.");
				location.href = `/board/${boardId}`;
			} else {
				alert("댓글삭제가 실패하였습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}
}

index.init();
