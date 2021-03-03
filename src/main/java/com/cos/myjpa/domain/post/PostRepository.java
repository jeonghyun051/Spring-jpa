package com.cos.myjpa.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

// 원래는 @Repositoty를 사용해서 등록해야하지만 생략가능, 내부적으로 ioc에 등록되어있음.
public interface PostRepository extends JpaRepository<Post, Long>{

}
