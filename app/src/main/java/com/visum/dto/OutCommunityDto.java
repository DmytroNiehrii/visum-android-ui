package com.visum.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OutCommunityDto {

    public Long id;
    public UUID code;
    public Timestamp createdAt;
    public Timestamp lastUpdateAt;
    public String name;
    public String description;
    public List<OutCommunityMemberDto> members;
}
