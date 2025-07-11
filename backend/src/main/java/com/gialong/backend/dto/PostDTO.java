package com.gialong.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private UUID id;
    private String title;
    private String content;
    private Set<String> tags = new HashSet<>();
    private LocalDateTime createdDate;
    private UUID createdBy;
    private LocalDateTime lastModifiedDate;
    private UUID lastModifiedBy;
}
