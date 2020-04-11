package com.visum.util.page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pageable {
    public Sort sort;
    public int offset;
    public int pageNumber;
    public int pageSize;
    public boolean paged;
    public boolean unpaged;

}
