package com.example.demo.service;

import com.example.demo.model.Word;
import com.example.demo.repository.WordRepository;
import com.example.demo.utils.TestUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class MigrationServiceTest {

    @Mock
    WordRepository repository;

    @InjectMocks
    MigrationService service;

    @Test
    void shouldAddWord(){
       Word word = TestUtils.getValidWord();
       when(repository.findByForeign_id(word.getForeign_id())).thenReturn(null);
       when(repository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
       service.addWord(word);
       verify(repository, times(1)).save(word);
    }
    @Nested
    class ShouldNotAddWord{

        @Test
        void whenDuplicateForeignKey(){
            Word word = TestUtils.getValidWord();
            when(repository.findByForeign_id(word.getForeign_id())).thenReturn(word);
            assertThrows(InvalidParameterException.class, () -> service.addWord(word));
            verify(repository, times(0)).save(word);
        }
        @Test
        void whenCreatedIsEarlierThan2015(){
            Word word = TestUtils.getWordWithDateEarlierThan2015();
            when(repository.findByForeign_id(word.getForeign_id())).thenReturn(null);
            assertThrows(InvalidParameterException.class, () -> service.addWord(word));
            verify(repository, times(0)).save(word);
        }
    }

    @Test
    void shouldFindAll(){
        when(repository.findAll()).thenReturn(TestUtils.getListOfWords());
        List<Word> words = repository.findAll();
        assertEquals(3, words.size());
        verify(repository, times(1)).findAll();
    }
    @Test
    void shouldFindByForeignId(){
        when(repository.findByForeign_id(1)).thenReturn(TestUtils.getValidWord());
        Word word = repository.findByForeign_id(1);
        assertEquals(word.getForeign_id(), 1);
        verify(repository, times(1)).findByForeign_id(1);
    }
    @Nested
    class ForeignKeyExists{
        @Test
        void shouldReturnTrueIfExists(){
            when(repository.findByForeign_id(1)).thenReturn(TestUtils.getValidWord());
            assertTrue(service.foreignKeyExists(1));
            verify(repository, times(1)).findByForeign_id(1);
        }
        @Test
        void shouldReturnFalseIfDoesntExist(){
            when(repository.findByForeign_id(1)).thenReturn(null);
            assertFalse(service.foreignKeyExists(1));
            verify(repository, times(1)).findByForeign_id(1);
        }
    }

}
