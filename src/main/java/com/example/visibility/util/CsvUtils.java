package com.example.visibility.util;

import com.example.visibility.model.CsvBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvUtils {

    public static List<? extends CsvBean> parseCsvToBean(Path path, Class clazz) throws Exception {
        List<CsvBean> beans;
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(clazz);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withMappingStrategy(ms)
                .build();

        beans = cb.parse();
        reader.close();
        return beans;
    }
}
