package com.visum.dto;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutUserDto {

    private Long id;
    private UUID code;
    private Timestamp createdAt;
    private Timestamp lastUpdateAt;
    private String firstName;
    private String lastName;
    private String phone;
}
