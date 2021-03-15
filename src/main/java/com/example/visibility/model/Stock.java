package com.example.visibility.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock extends CsvBean {

    @CsvBindByPosition(position = 0)
    private int sizeId;

    @CsvBindByPosition(position = 1)
    private int quantity;
}
