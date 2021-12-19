package com.fishtudo.awesomeseries.repositories.database.converter;

import androidx.room.TypeConverter;

import java.util.HashMap;
import java.util.Map;

public class ImageConverter {

    String KEY_DIVIDER = ":::";

    String LINE_DIVIDER = "///";

    @TypeConverter
    public String toString(Map<String, String> mapValues) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : mapValues.entrySet()) {
            result.append(entry.getKey()).append(KEY_DIVIDER)
                    .append(entry.getValue()).append(LINE_DIVIDER);
        }
        return result.toString();
    }

    @TypeConverter
    public Map<String, String> toMap(String mapValues) {
        String[] entries = mapValues.split(LINE_DIVIDER);
        Map<String, String> result = new HashMap<>();
        for (String s : entries) {
            String[] entry = s.split(KEY_DIVIDER);
            result.put(entry[0], entry[1]);
        }
        return result;
    }
}
