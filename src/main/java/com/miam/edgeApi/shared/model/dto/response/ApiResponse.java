package com.miam.edgeApi.shared.model.dto.response;

import com.miam.edgeApi.shared.model.enums.Estatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private Estatus status;
    private T data;
}
