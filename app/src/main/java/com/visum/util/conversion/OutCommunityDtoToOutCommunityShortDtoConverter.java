package com.visum.util.conversion;

import com.visum.dto.OutCommunityDto;
import com.visum.dto.OutCommunityShortDto;

import org.springframework.core.convert.converter.Converter;

public class OutCommunityDtoToOutCommunityShortDtoConverter implements Converter<OutCommunityDto, OutCommunityShortDto> {

    @Override
    public OutCommunityShortDto convert(OutCommunityDto source) {
        return OutCommunityShortDto.builder()
                .id(source.getId())
                .code(source.getCode())
                .createdAt(source.getCreatedAt())
                .lastUpdateAt(source.getLastUpdateAt())
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
