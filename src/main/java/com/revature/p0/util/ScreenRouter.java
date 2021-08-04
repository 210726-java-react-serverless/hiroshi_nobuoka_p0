package com.revature.p0.util;

import com.revature.p0.screens.Screen;
import java.util.HashSet;
import java.util.Set;

    public class ScreenRouter implements Router<Screen> {

        private Screen currentScreen;
        private Set<Screen> screens = new HashSet<>();

        public void add(Screen screen){
            screens.add(screen);
        };
        public void changeCurrent(String route){
            for(Screen screen: screens)
                if(screen.getRoute().equals(route)) {
                    this.currentScreen = screen;
                    break;
                }
        };
        public Screen getCurrent(){
            return currentScreen;
        };

}
