package com.brandpark.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //BaseTimeEntity클래스를 상송하는 클래스들은 BaseTimeEntity클래스의 필드들도 칼럼으로 인식한다.
@EntityListeners(AuditingEntityListener.class)  //Auditing기능 포함.
public abstract class BaseTimeEntity {
    @CreatedDate    //Entity가 생성되어 저장될 때 시간이 자동 저장.
    private LocalDateTime createdDate;

    @LastModifiedDate   //조회한 Entity의 값을 변경할 때 시간이 자동 저장된다. 
    private LocalDateTime modifiedDate;
}
