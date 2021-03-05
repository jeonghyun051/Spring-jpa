package com.cos.myjpa.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // autowired 대신해서 얘 사용, di
@RestController
public class TestController {
	
	private final PostRepository postRepository;

	@PostMapping("/test/post")
	public CommonRespDto<?> save(@RequestBody PostSaveReqDto postSaveReqDto) { // 받아야 할 값만 받아야함 Post로 받지 말것 !
		
		//User user = new User(1L,"ssar","1234","ssar@nate.com",LocalDateTime.now());
		
		Post postEntity = postRepository.save(postSaveReqDto.toEntity()); // 실패 => 인셉션 타게끔 해야함
		//postEntity.setUser(user);
		return new CommonRespDto<>(1,"성공",postEntity); 
	}
	
	@GetMapping("test/post")
	public CommonRespDto<?> findAll(){
		List<Post> postsEntity = postRepository.findAll();
		return new CommonRespDto<>(1,"성공",postsEntity);
		
	}
	
	@GetMapping("test/post/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id){
		
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		}); // null이 될수가 있어서 오류가 뜬다.
		
		return new CommonRespDto<>(1,"성공",postEntity);
	}
	
	@PutMapping("test/post/{id}")
	public CommonRespDto<?> update(@PathVariable Long id, @RequestBody PostUpdateReqDto postUpdateReqDto){
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		}); // null이 될수가 있어서 오류가 뜬다.
		postEntity.setTitle(postUpdateReqDto.getTitle());
		postEntity.setContent(postUpdateReqDto.getContent());
		
		Post postUpdateEntity = postRepository.save(postEntity); // 더티 체킹을 사용해야 하는데 그러러면 @service를 만들어야 가능함.
		
		return new CommonRespDto<>(1,"성공",postUpdateEntity);
	}
	
	@DeleteMapping("/test/post/{id}")
	public CommonRespDto<?> deleteById(@PathVariable Long id){ // 인셉션 만들기
		postRepository.deleteById(id);
		
		return new CommonRespDto<>(1,"성공",null);
	}
}
