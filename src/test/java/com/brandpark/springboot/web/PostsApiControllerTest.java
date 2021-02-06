package com.brandpark.springboot.web;

import com.brandpark.springboot.domain.posts.Posts;
import com.brandpark.springboot.domain.posts.PostsRepository;
import com.brandpark.springboot.web.dto.PostsResponseDto;
import com.brandpark.springboot.web.dto.PostsSaveRequestDto;
import com.brandpark.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @Autowired private PostsRepository postsRepository;
    @Autowired private TestRestTemplate testRestTemplate;
    @LocalServerPort private String port;

    @Test
    @Rollback
    public void Posts_저장된다(){
        String expectedTitle = "title";
        String expectedContent = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        List<Posts> postsList = postsRepository.findAll();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0);

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.getContent()).isEqualTo(expectedContent);
    }

    @Rollback
    @Test
    public void Posts_수정된다(){
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("mingon")
                .build());

        String expectedTitle = "title2";
        String expectedContent = "content2";
        Long id = savedPosts.getId();

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Posts posts = postsRepository.findAll().get(0);
        assertThat(posts.getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void Posts_조회된다(){
        String title = "title";
        String content = "content";

        Posts savedPosts = postsRepository.save(Posts.builder()
            .title(title)
            .content(content)
            .build());

        Long id = savedPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;
        PostsResponseDto responseDto = testRestTemplate.getForObject(url, PostsResponseDto.class);

        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContent()).isEqualTo(content);
    }
}