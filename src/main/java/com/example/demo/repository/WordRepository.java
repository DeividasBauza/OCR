package com.example.demo.repository;

import com.example.demo.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query("Select w from Word w where w.foreign_id = ?1")
    Word findByForeign_id(int foreign_id);
}
