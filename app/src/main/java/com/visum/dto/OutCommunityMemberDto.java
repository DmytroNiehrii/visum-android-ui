package com.visum.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutCommunityMemberDto {

    private Long id;
    private Timestamp createdAt;
    private Timestamp lastUpdateAt;
    private OutUserDto user;
}
