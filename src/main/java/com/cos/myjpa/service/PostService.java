package com.cos.myjpa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.web.post.dto.PostRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

// jpa 영속성컨텍스트는 변경감지를 하는데 언제하느냐? 서비스 종료시에 함

@RequiredArgsConstructor
@Service
public class PostService {
	
	private final PostRepository postRepository;

	@Transactional
	public Post 글쓰기(PostSaveReqDto postSaveReqDto, User principal) {
		Post post = postSaveReqDto.toEntity();
		post.setUser(principal); // FK를 insert할 수 있다.
		Post postEntity = postRepository.save(post);
		return postEntity;
	}
	
	
	@Transactional(readOnly = true) // 변경감지 자체를 수행안한다. select하는곳에는 다 붙혀줘야함
	public PostRespDto 글찾기(Long id) {
		// 옵셔널 orElseThrow
		Post  postsEntity =postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을수 없습니다.");
			});
		
//		// 옵셔널 get() 있거나 없거나 일단 가져와 null값 가져오기 가능
//		postRepository.findById(1L).get();
//		
//		// 옵셔널 orElseGet 있으면 값을 넣고 없으면 빈 객체 던지기
//		postRepository.findById(1L).orElseGet(new Supplier<Post>() {
//
//			@Override
//			public Post get() {
//				
//				return new Post();
//			}
//		});
//		
//		// 옵셔널 orElseGet 두번째 방식
//		postRepository.findById(1L).orElseGet(()->{
//			return new Post();
//		});
		
		
		PostRespDto postRespDto = new PostRespDto(postsEntity);
		return postRespDto;
	}
	
	
	@Transactional(readOnly = true) // 변경감지 자체를 수행안한다. select하는곳에는 다 붙혀줘야함, 고립성
	public List<Post> 글전체보기() {
		List<Post> postsEntity = postRepository.findAll();
		return postsEntity;
	}
	
	
	@Transactional
	public Post 글수정(PostUpdateReqDto postUpdateReqDto,Long id) {
		
		// 영속화하는 법
//		Post p = new Post();
//		em.persist(p); 
//		Query q = em.createNativeQuery("select * from post");
				
		// findById 때문에 영속화가 되었다. 1차캐시에 들어가있다. 이미 영속컨텍스트에 있으면 다시 값을 찾을 때 db에 select하지 않는다. postEntity2를 하면 영속성컨텍스트에 이미 있는 캐시를 재사용한다. 힙공간(메모리공간)은 다르다.
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		}); // null이 될수가 있어서 오류가 뜬다.
		

		//영속성되어있는 데이터 값을 바꿈
		postEntity.setTitle(postUpdateReqDto.getTitle());
		postEntity.setContent(postUpdateReqDto.getContent());
		
		Post postUpdateEntity = postRepository.save(postEntity); // 더티 체킹을 사용해야 하는데 그러러면 @service를 만들어야 가능함.
		
		return postUpdateEntity;
	} // 트랙잭션(서비스) 종료시 영속성 컨텍스트에 영속화 되어있는 모든 객체의 변경을 감지하여 변경된 아이들을 flush 한다 (commit) = 더티체킹
	
	
	@Transactional
	public void 글삭제(Long id) {
		postRepository.deleteById(id);
	}
}
