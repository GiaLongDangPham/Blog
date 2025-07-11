package com.gialong.backend.dto;

import com.gialong.backend.enums.RoleEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    private RoleEnum name;
    private String description;
}
