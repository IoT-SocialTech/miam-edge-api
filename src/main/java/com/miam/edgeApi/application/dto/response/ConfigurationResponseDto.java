package com.miam.edgeApi.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigurationResponseDto {

    private String id;

    private String configName;

    private String configValues;

    private String deviceId;

}
