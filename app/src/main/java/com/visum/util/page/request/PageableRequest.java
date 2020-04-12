package com.visum.util.page.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class PageableRequest {
    @Getter
    @Setter
    private int pageNumber;
    @Getter
    private int pageSize;
    private String[] sort;
    private SortDirection sortDirection;

/*    public PageableRequest(int pageNumber) {

    }*/

    public String getSortString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sort.length; i++) {
            if (sb.length() > 0) sb.append(",");
            sb.append(sort[i]);
        }
        if (sb.length() > 0) {
            if (sortDirection != null) sb.append(",").append(sortDirection);
            else sb.append(",").append(SortDirection.ASC);
        }
        return sb.toString();
    }
}
