package ru.isu.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateToStringConvertable {
    default String getStringDate(LocalDateTime date){
        return date == null ? "" : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
    default String getStringDate(LocalDate date){
        return date == null ? "" :  date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
