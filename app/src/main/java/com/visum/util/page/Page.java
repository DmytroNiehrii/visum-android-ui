package com.visum.util.page;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page<T> {

    public List<T> content;
    public Pageable pageable;
    public int totalPages;
    public int totalElements;
    public boolean last;
    public int number;
    public Sort sort;
    public int size;
    public int numberOfElements;
    public boolean first;
    public boolean empty;
}
