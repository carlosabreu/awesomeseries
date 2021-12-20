package com.fishtudo.awesomeseries.repositories.database.converter;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListConverter {

    String LINE_DIVIDER = "///";

    @TypeConverter
    public String toString(List<String> listValues) {
        StringBuilder result = new StringBuilder();
        for (String value : listValues) {
            result.append(value).append(LINE_DIVIDER);
        }
        return result.toString();
    }

    @TypeConverter
    public List<String> toList(String string) {
        List<String> result = new ArrayList<>();
        Collections.addAll(result, string.split(LINE_DIVIDER));
        return result;
    }
}
