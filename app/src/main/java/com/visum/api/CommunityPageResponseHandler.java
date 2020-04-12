package com.visum.api;

import com.visum.dto.OutCommunityShortDto;
import com.visum.util.page.response.Page;

public interface CommunityPageResponseHandler {

    void handle(Page<OutCommunityShortDto> page);
}
