/**
 * 
 */

let index = {
	init: function() {
		$("#btn-save").on("click", ()=> { //function()을 사용하지 않는 이유는 this를 바인딩하기 위해서 ()=>{}을 하용함.
			//index.save(); //function()일 경우 this가 window object임, 따라서 index로 save함수를 호출할수 있다.
			this.save();
		});
		
/*		$("#btn-login").on("click", ()=> { //function()을 사용하지 않는 이유는 this를 바인딩하기 위해서 ()=>{}을 하용함.
			//index.save(); //function()일 경우 this가 window object임, 따라서 index로 save함수를 호출할수 있다.
			this.login();
		});
*/		
		$("#btn-update").on("click", ()=> { //function()을 사용하지 않는 이유는 this를 바인딩하기 위해서 ()=>{}을 하용함.
			//index.save(); //function()일 경우 this가 window object임, 따라서 index로 save함수를 호출할수 있다.
			this.update();
		});
	}
	, save: function() {
		//alert("user의 save함수 호출됨.");
		
		let data = {
			username: $('#username').val()
			,password: $('#password').val()
			,email: $('#email').val()
		}
		
		//console.log(data);
		
		//ajax호출시 default가 비동기 호출
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오프젝트로 변환해주네요.
		$.ajax({
			type: "POST"
			,url: "/auth/joinProc"
			,data: JSON.stringify(data) //http body데이터
			,contentType: "application/json; charset=utf-8" //body데이터를 보낼때는 contentType을 알려 줘야 한다.
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			//alert(resp);
			console.log(resp);
			if( resp.status == 200 ) {
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			} else {
				alert("회원가입에 실패했습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}
	
/*	, login: function() {
		//alert("user의 save함수 호출됨.");
		
		let data = {
			username: $('#username').val()
			,password: $('#password').val()
		}
		
		//console.log(data);
		
		//ajax호출시 default가 비동기 호출
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오프젝트로 변환해주네요.
		$.ajax({
			type: "POST"
			,url: "/api/user/login"
			,data: JSON.stringify(data) //http body데이터
			,contentType: "application/json; charset=utf-8" //body데이터를 보낼때는 contentType을 알려 줘야 한다.
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			//alert(resp);
			//console.log(resp);
			if( resp.status == 200 ) {
				alert("회원로그인이 완료되었습니다.");
				location.href = "/";
			} else {
				alert("회원로그인에 실패했습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}
*/	
	, update: function() {
		//alert("user의 save함수 호출됨.");
		let data = {
			id: $('#id').val()
			,username: $('#username').val()
			,password: $('#password').val()
			,email: $('#email').val()
		}
		
		//console.log(data);
		
		//ajax호출시 default가 비동기 호출
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오프젝트로 변환해주네요.
		$.ajax({
			type: "PUT"
			,url: "/user"
			,data: JSON.stringify(data) //http body데이터
			,contentType: "application/json; charset=utf-8" //body데이터를 보낼때는 contentType을 알려 줘야 한다.
			,dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
		}).done(function(resp){ //응답도 json으로 옮
			//alert(resp);
			//console.log(resp);
			if( resp.status == 200 ) {
				alert("회원수정이 완료되었습니다.");
				location.href = "/";
			} else {
				alert("회원수정에 실패했습니다.(" + resp.data + ")");
			}
		}).fail(function(error){ //응답도 json으로 옮
			alert(JSON.stringify(error));
			//console.log(error);
		}); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청함.
		
	}
}

index.init();
