package com.visum.util.page.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pageable {
    private Sort sort;
    private int offset;
    private int pageNumber;
    private int pageSize;
    private boolean paged;
    private boolean unpaged;
}
