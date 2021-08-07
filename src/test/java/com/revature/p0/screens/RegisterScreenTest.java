package com.revature.p0.screens;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;

import static org.mockito.Mockito.mock;

public class RegisterScreenTest {
    RegisterScreen sut;
    BufferedReader mockReader;
    Repository mockRepo;

    @Before
    public void beforeEachTest(){
        mockReader = mock(BufferedReader.class);
        mockRepo = mock(Repository.class);
        sut = new RegisterScreen(mockReader, mockRepo);
    }
    @After
    public void afterEachTest(){
        mockReader.close();
        sut = new;
    };

    @Test
    public

}
