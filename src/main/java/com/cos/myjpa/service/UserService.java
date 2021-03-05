package com.cos.myjpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	@Transactional(readOnly = true) // 변경감지 자체를 수행안한다. select하는곳에는 다 붙혀줘야함
	public List<UserRespDto> 전체찾기() {
		List<User> usersEntity = userRepository.findAll();
		
		// 1번 for each
		List<UserRespDto> userRespDtos = new ArrayList<>();
	    for (User user : usersEntity) {
	    	userRespDtos.add(new UserRespDto(user));
		}
		
	    // 2번 맵
//		List<UserRespDto> userRespDtos = usersEntity.stream().map((u)->{
//			return new UserRespDto(u);
//		}).collect(Collectors.toList());;
		return userRespDtos;
	}
	
	@Transactional(readOnly = true) // 변경감지 자체를 수행안한다. select하는곳에는 다 붙혀줘야함
	public UserRespDto 한건찾기(Long id) {
		User userEntity =userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을수 없습니다.");
		});

		UserRespDto userRespDto = new UserRespDto(userEntity);
		return userRespDto;
	}
	
	@Transactional(readOnly = true) // 변경감지 자체를 수행안한다. select하는곳에는 다 붙혀줘야함
	public User 프로파일(Long id) {
		User userEntity =userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을수 없습니다.");
		});
		return userEntity;
		
	}
					// 예를 들어 서비스가 두개이고 하나가 실패하면 롤백된다.
	@Transactional // 쓰기는 트랙잭션을 가져야함 쓰기 하는동안에는 다른애들이 간섭하지 못하도록 동시접속 못하도록 , 데이터베이스에 쓰기 하고있으니까 다른애들이 쓰지 않도록
	public User 회원가입(UserJoinReqDto userJoinReqDto) {
		User userEntity = userRepository.save(userJoinReqDto.toEntity());
		return userEntity;
	}
	
	@Transactional(readOnly = true) // 변경감지 자체를 수행안한다. select하는곳에는 다 붙혀줘야함
	public User 로그인(UserLoginReqDto userLoginReqDto) {
		User userEntity = userRepository.findByUsernameAndPassword(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
		return userEntity;
	}
}
