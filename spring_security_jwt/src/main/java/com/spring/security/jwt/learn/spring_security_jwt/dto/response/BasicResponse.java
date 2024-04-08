package com.spring.security.jwt.learn.spring_security_jwt.dto.response;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicResponse<T> {
    private String message;
    private T data;

    @Builder.Default
    private List<T> dataList = Collections.emptyList();
}
