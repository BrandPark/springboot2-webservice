package com.brandpark.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
* 단순히 인터페이스를 생성 후 상속만 하면 기본적인 CRUD메소드가 자동으로 생성된다.
*/
public interface PostsRepository extends JpaRepository<Posts, Long> {   //<Entity클래스, PK타입>
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
