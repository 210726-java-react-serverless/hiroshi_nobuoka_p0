package com.revature.p0.util;


import com.revature.p0.screens.Screen;
import com.revature.p0.screens.WelcomeScreen;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static org.mockito.Mockito.mock;

public class ScreenRouterTest {
    ScreenRouter sut;
    WelcomeScreen mockWelcomeScreen;
    @Before
    public void beforeEachTest() {
        mockWelcomeScreen = mock(WelcomeScreen.class);
        sut = new ScreenRouter();
    }
    @After
    public void afterEachTest(){
        sut = null;
    }
    @Test
    public void changeCurrentScreen_works(){
        //Arrange
        String route = "/welcome";
        Screen expectedResult = mockWelcomeScreen;

        //Act
        sut.changeCurrent(route);
        Screen actualResult = sut.getCurrent();

        //Assert
        Assert.assertEquals("Expect current screen to be the welcome screen",expectedResult,actualResult);
    }
}
