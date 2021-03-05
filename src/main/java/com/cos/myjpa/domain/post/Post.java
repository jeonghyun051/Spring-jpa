package com.cos.myjpa.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity 
public class Post { // junit테스트는 dto를 만들고 거기서 valid 체크를 해야함 여기는 기존 데베 만드는 곳

	@Id //javax꺼임
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가 전략 3가지 Table, auto_increment, Sequence / 기본 전략을 따르겠다
	private Long id;
	
	@Column(length = 60, nullable = false) // null을 넣을 수 없음
	private String title;
	
	@Lob //대용량 데이터
	private String content;
	
	//순방향 매핑
	//누가 적었는지
	@ManyToOne(fetch = FetchType.EAGER) // many는 post one은 user 관계를 맺었다
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp // 자동으로 현재시간이 들어간다.
    private LocalDateTime createTime; // 작성 시간이 필요하다.
	
	
}
