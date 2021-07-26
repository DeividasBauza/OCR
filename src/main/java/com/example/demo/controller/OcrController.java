package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OcrController {

    private WordRepository wordRepository;

    @Autowired
    public OcrController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @GetMapping("/all")
    public List<Word> findAll(){
       return this.wordRepository.findAll();
    }

}
