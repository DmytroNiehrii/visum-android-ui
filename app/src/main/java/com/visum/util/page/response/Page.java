package com.visum.util.page.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page<T> {

    private List<T> content;
    private Pageable pageable;
    private int totalPages;
    private int totalElements;
    private boolean last;
    private int number;
    private Sort sort;
    private int size;
    private int numberOfElements;
    private boolean first;
    private boolean empty;
}
