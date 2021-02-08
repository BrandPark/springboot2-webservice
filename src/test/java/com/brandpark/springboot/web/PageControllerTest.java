package com.brandpark.springboot.web;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PageControllerTest {
    @LocalServerPort
    private String port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void 메인화면_출력(){
        String s = restTemplate.getForObject("/", String.class);
        assertThat(s).contains("SpringBoot2 WebService만들기");
    }

}