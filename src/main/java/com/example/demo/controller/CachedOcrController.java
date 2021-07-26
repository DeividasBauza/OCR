package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.service.MigrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;

import static com.example.demo.config.CacheConfiguration.WORDS_CACHE;

@Log4j2
@RestController
@RequestMapping("/api/cached")
@CacheConfig(cacheNames = WORDS_CACHE)
public class CachedOcrController {

    private final Jedis jedis;
    private final MigrationService migrationService;

    @Autowired
    public CachedOcrController(MigrationService migrationService, Jedis jedis) {
        this.migrationService = migrationService;
        this.jedis = jedis;
    }

    @CacheEvict(value = WORDS_CACHE, allEntries = true)
    @RequestMapping("/flush")
    public void flushCache(){
        jedis.flushAll();
      log.info("Cache flushed");
    }

    @Cacheable(WORDS_CACHE)
    @GetMapping("/all")
    public List<Word> findAllCached(){
     return migrationService.findAll();
    }

    @GetMapping("/details/{foreign_id}")
    public Word getDetailsFromCacheByForeignId(@PathVariable int foreign_id){
        return migrationService.findByForeignId(foreign_id);
    }


}
