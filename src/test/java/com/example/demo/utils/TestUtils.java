package com.example.demo.utils;

import com.example.demo.model.Word;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static String convertObjectToJsonString(Object object) throws JsonProcessingException{
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(object);
    }
    public static Word getValidWord(){
        return Word.builder().
                word("word").
                foreign_id(1).
                created(LocalDateTime.of(2020,10,15,15,15,15)).
                build();
    }
    public static Word getWordWithDateEarlierThan2015(){
        return Word.builder().
                word("word").
                foreign_id(1).
                created(LocalDateTime.of(2010,10,15,15,15,15)).
                build();
    }
    public static Word getWordWithDifferentForeignKey(){
        return Word.builder().
                word("word").
                foreign_id(2).
                created(LocalDateTime.of(2020,10,15,15,15,15)).
                build();
    }

    public static List<Word> getListOfWords(){
        return new ArrayList<>(List.of(
                getWordWithDateEarlierThan2015(),
                getValidWord(),
                getWordWithDifferentForeignKey()
        ));
    }

}
