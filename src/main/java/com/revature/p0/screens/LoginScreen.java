package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.questions.EduQuestion;
import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class LoginScreen extends Screen {
    private final Logger logger = LogManager.getLogger(LoginScreen.class);
    private final UserService service;
    private BufferedReader reader;
    private ScreenRouter router;
    private UserSession session;
    QuestionFactory qfactory = QuestionFactory.getInstance();

    public LoginScreen(BufferedReader reader, ScreenRouter router, UserService service, UserSession session) {
        super("LoginScreen", "/login", reader, router);
        this.service = service;
        this.reader= reader;
        this.session = session;
        this.router= router;
    }

    @Override
    public void render() throws Exception {

        String menu = "\nUser Login\n" +
                "1) Login\n" +
                "2) Go Back\n" +
                ">";
        System.out.println(menu);
        NavigateScreenQuestion askUserInput = new NavigateScreenQuestion(2);
        String userInput = reader.readLine();
        while(!askUserInput.validAnswer(userInput)){
            userInput = reader.readLine();
        }
        switch(userInput){
            case "1":
                break;
            case "2":
                router.previousScreen();
                return;
        }

        EduQuestion eduQuestion = new EduQuestion(session);
        String edu = reader.readLine();
        while(!eduQuestion.validAnswer(edu)){
            edu = reader.readLine();
        }

        qfactory.getQuestion("username",service);
        String username = reader.readLine();
        qfactory.getQuestion("password",service);
        String password = reader.readLine();

        AppUser user = service.login(username, password);

        //Called an arbitrary getter to invoke NPE if user authentication failed. Otherwise, navigate user to correct dash.
        try{
            user.getUsername();
            System.out.println("Login successful!");
            if (user.getEdu().equals("STUDENT"))
                router.navigate("/sdash");
            router.navigate("/fdash");

        }catch(NullPointerException npe){
            System.out.println("Login attempt failed. Please try again.");
            router.navigate("/login");
        }
    }
}
