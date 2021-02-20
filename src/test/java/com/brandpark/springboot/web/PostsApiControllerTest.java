package com.brandpark.springboot.web;

import com.brandpark.springboot.domain.posts.Posts;
import com.brandpark.springboot.domain.posts.PostsRepository;
import com.brandpark.springboot.web.dto.PostsSaveRequestDto;
import com.brandpark.springboot.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class PostsApiControllerTest {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private String port;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = "USER")
    @Test
    public void Posts_저장된다() throws Exception {
        String expectedTitle = "title";
        String expectedContent = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

//        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0);

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정된다() throws Exception {
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



        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        Posts posts = postsRepository.findAll().get(0);
        assertThat(posts.getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_조회된다() throws Exception {
        String title = "title";
        String content = "content";

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        Long id = savedPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;
//        PostsResponseDto responseDto = testRestTemplate.getForObject(url, PostsResponseDto.class);

        MvcResult mvcResult = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.content", is(content)))
                .andReturn();

//        String s = mvcResult.getResponse().getContentAsString();
//        PostsResponseDto responseDto = new ObjectMapper().readValue(s, PostsResponseDto.class);
//
//        assertThat(responseDto.getTitle()).isEqualTo(title);
//        assertThat(responseDto.getContent()).isEqualTo(content);
    }
}