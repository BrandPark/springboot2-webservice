package com.brandpark.springboot.domain.posts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @Test
    @Rollback
    public void 게시글저장_불러오기(){
        String title = "테스트 게시글";
        String content = "content";

        postsRepository.save(Posts.builder().title(title).content(content).author("박민곤").build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo("박민곤");
    }

    @Rollback
    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2021,2,7,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        Posts posts = postsRepository.findAll().get(0);

        System.out.println(">>>>>>>>>>>>>>>>> createdDate : " + posts.getCreatedDate() +
                ", modifiedDate : " + posts.getModifiedDate());

        //then
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}