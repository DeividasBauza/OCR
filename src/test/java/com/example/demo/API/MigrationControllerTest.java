package com.example.demo.API;

import com.example.demo.controller.MigrationController;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.model.Word;
import com.example.demo.service.MigrationService;
import com.example.demo.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {MigrationController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc
class MigrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MigrationService migrationService;

    @InjectMocks
    private MigrationController migrationController;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        mvc = standaloneSetup(migrationController).
                setControllerAdvice(createExceptionResolver()).
                build();
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalExceptionHandler(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    @Test
    void shouldMigrateDataSuccessfully() throws Exception {
        mvc.perform(post("/api/migration/ocr")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonString(TestUtils.getValidWord()))).
                andExpect(status().isOk());
    }
}
