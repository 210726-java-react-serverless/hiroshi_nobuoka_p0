package com.revature.p0.util;

import com.revature.p0.screens.Screen;
import java.util.HashSet;
import java.util.Set;

    public class ScreenRouter implements Router<Screen> {

        private String currentScreen;
        private Set<Screen> screens = new HashSet<>();

        public void add(){};
        public void changeCurrent(String route){};
        public void getCurrent(){};




    }
}
