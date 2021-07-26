package com.example.demo.service;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import static java.util.Objects.nonNull;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo.config.CacheConfiguration.WORDS_CACHE;

@Log4j2
@Service
public class MigrationService {

    private static final LocalDateTime EARLIEST_POSSIBLE_DATE = LocalDateTime.of(2015,1,1,0,0,0);
    private final WordRepository wordRepository;

    @Autowired
    public MigrationService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Caching(evict = { @CacheEvict(value = WORDS_CACHE, allEntries = true), }, put = {
            @CachePut(value = WORDS_CACHE, key = "#word.getForeign_id()", unless="#result == null") })
    public void addWord(Word word){
        if(word.getCreated().isBefore(EARLIEST_POSSIBLE_DATE)){
            throw new InvalidParameterException("Dates earlier than 2015-01-01 are not allowed, therefore data are not saved to DB");
        } else if (foreignKeyExists(word.getForeign_id())){
            throw new InvalidParameterException("Entry with this foreign ID already exists on database");
        } else
        wordRepository.save(word);
    }

    public List<Word> findAll(){
        return wordRepository.findAll();
    }

    public Word findByForeignId(int foreignId){
        return wordRepository.findByForeign_id(foreignId);
    }
    private boolean foreignKeyExists(int foreignId){
        return nonNull(wordRepository.findByForeign_id(foreignId));
    }
}
