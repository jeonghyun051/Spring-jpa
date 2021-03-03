package com.cos.myjpa.exhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.web.dto.CommonRespDto;

@RestController // 데이터를 리턴할 수 있음
@ControllerAdvice // 모든 인셉션을 낚아 챈다.
public class GlobalExceptionHandler {

	
	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public CommonRespDto<?> dataIntegrityViolation(Exception e) {
		return new CommonRespDto<>(-1, e.getMessage(), null);
	}
	
	// 그 중 IllegalArgumentException이 발생하면 해당 함수가 실행됨
	@ExceptionHandler(value=IllegalArgumentException.class)
	public CommonRespDto<?> illegalArgument(Exception e) {
		return new CommonRespDto<>(-1, e.getMessage(), null);
	}
	
	@ExceptionHandler(value=EmptyResultDataAccessException.class) // delete id찾기 실패시 인셉션
	public CommonRespDto<?> emptyResultDataAccess(Exception e) {
		return new CommonRespDto<>(-1, e.getMessage(), null);
	}
}
