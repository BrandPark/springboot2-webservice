package com.brandpark.springboot.domain.posts;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void 게시글저장_불러오기() {
        String title = "테스트 게시글";
        String content = "content";

        postsRepository.save(Posts.builder().title(title).content(content).author("박민곤").build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo("박민곤");
    }

    @Test
    public void BaseEntity_테스트() {
        LocalDateTime now = LocalDateTime.of(2021, 2, 8, 0, 0, 0);

        Posts postsSaved = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        System.out.println("생성시간 : " + postsSaved.getCreatedDate() + ", 수정시간 : " + postsSaved.getModifiedDate());

        assertThat(postsSaved.getCreatedDate()).isAfter(now);
        assertThat(postsSaved.getModifiedDate()).isAfter(now);
    }
}