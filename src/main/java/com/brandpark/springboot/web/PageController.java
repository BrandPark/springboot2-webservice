package com.brandpark.springboot.web;

import com.brandpark.springboot.config.auth.LoginUser;
import com.brandpark.springboot.config.auth.dto.SessionUser;
import com.brandpark.springboot.service.posts.PostsService;
import com.brandpark.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class PageController {
    private final PostsService postsService;
    private final HttpSession httpSession;
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser sessionUser) {
        model.addAttribute("Posts", postsService.findAllDesc());
//        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(sessionUser != null)
            model.addAttribute("userName", sessionUser.getName());

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}