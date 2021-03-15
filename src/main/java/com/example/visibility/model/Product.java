package com.example.visibility.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends CsvBean implements Comparable<Product> {

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private Integer sequence;

    @Override
    public int compareTo(Product p) {
        return sequence.compareTo(p.getSequence());
    }
}
