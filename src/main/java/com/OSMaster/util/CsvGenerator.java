package com.OSMaster.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CsvGenerator {

    public static <T> String convertToCsv(List<T> data, Class<T> clazz) {
        if (data == null || data.isEmpty()) {
            return "";
        }

        Field[] fields = clazz.getDeclaredFields();
        String header = Stream.of(fields)
                .map(Field::getName)
                .collect(Collectors.joining(","));

        String rows = data.stream()
                .map(obj -> mapObjectToRow(obj, fields))
                .collect(Collectors.joining("\n"));

        return header + "\n" + rows;
    }

    private static <T> String mapObjectToRow(T obj, Field[] fields) {
        return Stream.of(fields)
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        return value != null ? value.toString() : "";
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                })
                .collect(Collectors.joining(","));
    }
}
