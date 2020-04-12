package com.visum.util.page.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sort {
    private boolean sorted;
    private boolean unsorted;
    private boolean empty;
}