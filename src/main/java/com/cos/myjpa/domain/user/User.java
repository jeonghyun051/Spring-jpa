package com.cos.myjpa.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	// 역방향 매핑
	@JsonIgnoreProperties({"user"}) //MessageConvert에 잭슨이 포스트안에있는 user를 getter 때리지마라 / dto 만드는 방법도 있다.
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // fk가 만들어지는데 안만들기 위해서 mappedBy / postEntity에 있는 user가 fk이구나 라고 생각함, 나는 fk의 주인이 아니다 라고 할 때 사용
	private List<Post> post;
	
//	@Transient // 테이블에는 안만들어지는데 자바 클래스에는 존재
//	private int value;
}
