package com.cos.myjpa.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonRespDto <T>{

	private int satuteCode; // 상태코드 1은 정상 -1은 실패 
	private String msg; // 오류내용
	private T data;
	
}
