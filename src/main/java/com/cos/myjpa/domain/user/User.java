package com.cos.myjpa.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity 
public class User { // junit테스트는 dto를 만들고 거기서 valid 체크를 해야함 여기는 기존 데베 만드는 곳

	@Id //javax꺼임 PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가 전략 3가지 Table, auto_increment, Sequence / 기본 전략을 따르겠다
	private Long id;
	
	private String username;
	private String password;
	private String email;
	
	@CreationTimestamp // 자동으로 현재시간이 들어간다.
    private LocalDateTime createTime; // 작성 시간이 필요하다.
}
