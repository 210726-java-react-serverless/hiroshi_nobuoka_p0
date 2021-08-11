package com.revature.p0.util;

import com.revature.p0.exceptions.ScreenNotFoundException;
import com.revature.p0.screens.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class ScreenRouter{
        private final Logger logger = LogManager.getLogger();
        private Screen currentScreen;
        private final Set<Screen> screens = new HashSet<>();
        private ArrayDeque<Screen> history;

        public ScreenRouter(){ history = new ArrayDeque<Screen>();}

        public void addScreen(Screen screen){
            screens.add(screen);
        };

        public void navigate(String route){
            if (currentScreen != null)
                history.push(currentScreen);
            currentScreen = screens.stream()
                            .filter(screen -> screen.getRoute().equals(route))
                            .findFirst()
                            .orElseThrow(ScreenNotFoundException::new);
        }

       public void previousScreen(){
            if(history.isEmpty())
                throw new ScreenNotFoundException();
            while(currentScreen.equals(history.getFirst())){
                currentScreen = history.pop();
           } currentScreen = history.pop();
       }

        public Screen getCurrentScreen(){return currentScreen;};

}
