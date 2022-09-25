package com.villvay.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class RequestResponse<T> implements Serializable {
    private boolean isSuccess;
    private List<T> responses;
    private List<ErrorResponse> errors;
}
