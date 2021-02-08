package com.brandpark.springboot.domain.posts;

import com.brandpark.springboot.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity //테이블과 링크될 클래스임을 표시.기본값으로 클래스의 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭.
public class Posts extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성규칙. 스프링 부트 2.0에서는 IDENTITY옵션을 해야만 자동 증가.
    private long id;

    @Column(length = 500, nullable = false) //굳이 선언하지 않아도 클래스의 필드는 모두 칼럼이 된다. 추가 옵션이 필요할 경우 사용.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    // 생성자에 선언 시 생성자에 포함된 필드만 빌더에 포함.
    public Posts(long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content =content;
    }
}
