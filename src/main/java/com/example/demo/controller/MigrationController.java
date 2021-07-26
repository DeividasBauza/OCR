package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.service.MigrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static java.util.Objects.nonNull;

import javax.validation.Valid;
import java.security.InvalidParameterException;

@Log4j2
@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    private MigrationService migrationService;

    @Autowired
    public MigrationController(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @PostMapping("/ocr")
    public void migrate(@RequestBody @Valid Word word){
       migrationService.addWord(word);
    }

}
