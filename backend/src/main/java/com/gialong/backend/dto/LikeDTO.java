package com.gialong.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private Long id;
    private LocalDateTime createdDate;
    private UUID userId;
    private UUID postId;
}
