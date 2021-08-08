package com.revature.p0.screens;

import com.revature.p0.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;

import static org.mockito.Mockito.mock;

public class RegisterScreenTest {
    RegisterScreen sut;
    BufferedReader mockReader;
    UserRepository mockRepo;

    @Before
    public void beforeEachTest(){
        mockReader = mock(BufferedReader.class);
        mockRepo = mock(UserRepository.class);
        sut = new RegisterScreen();
    }
    @After
    public void afterEachTest(){
        mockReader.close();
        sut = new;
    }

    @Test
    public

}
