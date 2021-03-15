package com.example.visibility.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Size extends CsvBean {

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private int productId;

    @CsvBindByPosition(position = 2)
    private boolean backSoon;

    @CsvBindByPosition(position = 3)
    private boolean special;
}
