package com.cos.myjpa.web.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.service.UserService;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserRepository userRepository;
	private final HttpSession session;
	private final UserService userService;
	
	@GetMapping("/user") // 전체 찾기
	public CommonRespDto<?> findAll(){
		List<User> usersEntity = userRepository.findAll();

		return new CommonRespDto<>(1,"성공",userService.전체찾기());
	}
	
	
	@GetMapping("/user/{id}") // 한건 찾기
	public CommonRespDto<?> findById(@PathVariable Long id){
		
		return new CommonRespDto<>(1,"성공",userService.한건찾기(id));
	}
	
	
	@GetMapping("/user/{id}/post") // 유저 한명이 쓴 게시글 전체보기
	public CommonRespDto<?> profile(@PathVariable Long id){
		
		return new CommonRespDto<>(1,"성공",userService.프로파일(id));
	}
	
	
	@PostMapping("/join") //auth(인증) 회원가입
	public CommonRespDto<?> join(@RequestBody UserJoinReqDto userJoinReqDto){
		
		return new CommonRespDto<>(1,"성공",userService.회원가입(userJoinReqDto));
	}
	
	
	@PostMapping("/login") // 서블릿 관련은 여기서 세션
	public CommonRespDto<?> login(@RequestBody UserLoginReqDto userLoginReqDto){
		User userEntity = userService.로그인(userLoginReqDto);
		if(userEntity ==null) {
			return new CommonRespDto<>(-1,"실패",null);
		}else {
			userEntity.setPassword(null);
			session.setAttribute("principal", userEntity);
			return new CommonRespDto<>(1,"성공",userEntity);
		}
	}
	
//	@GetMapping("/user/{id}")
//	public CommonRespDto<?> userInfo(@PathVariable Long id){
//	
//		User principal =(User) session.getAttribute("principal");
//		if(principal == null) {
//			return new CommonRespDto<>(-1,"실패",null);
//		}else {
//			User userEntity = userRepository.findById(id).get();	
//			return new CommonRespDto<>(1,"성공",userEntity);
//		}
//	}
}