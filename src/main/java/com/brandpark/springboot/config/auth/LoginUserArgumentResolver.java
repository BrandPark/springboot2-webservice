package com.brandpark.springboot.config.auth;

import com.brandpark.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {   //컨트롤러 메서드의 특정 파라미터를 지원하는지 판단한다.
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;    //파라미터에 LoginUser 어노테이션이 붙어있고, 파라미터의 클래스 타입이 SessionUser 이면 true 반환
    }

    @Override
    public Object resolveArgument(MethodParameter parameter //파라미터에 전달할 객체를 생성합니다.
            , ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest
            , WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");    //세션에서 객체를 가져온다.
    }
}
