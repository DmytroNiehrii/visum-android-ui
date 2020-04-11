package com.visum.util.page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sort {
    public boolean sorted;
    public boolean unsorted;
    public boolean empty;
}