package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.questions.Question;
import com.revature.p0.services.CourseService;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.util.HashMap;

public class UpdateScreen extends Screen{
    private final Logger logger = LogManager.getLogger(UpdateScreen.class);
    private UserService serviceLink;
    private ScreenRouter router;
    private UserSession session;
    private BufferedReader reader;
    private CourseService courseService;
    QuestionFactory qFactory = QuestionFactory.getInstance();

    public UpdateScreen(BufferedReader reader, ScreenRouter router, UserService serviceLink, UserSession session){
        super("Update Screen", "/update", reader, router);
        this.serviceLink = serviceLink;
        this.router = router;
        this.session = session;
        this.reader = reader;
        this.courseService = courseService;
    }

    public void render() throws Exception{
        AppUser beforeUpdate = session.getCurrentUser();
        System.out.println("**USER UPDATE**\n");
        String currentInfo = "First name: "+beforeUpdate.getFirstName()+"\t"
                +"Last name: "+beforeUpdate.getLastName()+"\n"
                +"ID: "+beforeUpdate.getId()+"\t"
                +"Email: "+beforeUpdate.getEmail()+"\n"
                +"Username: "+beforeUpdate.getUsername()+"\t"
                +"Password: "+beforeUpdate.getPassword()+"\n"; //TODO Find way to encrypt
        System.out.println(currentInfo);

        String menu = "Please select the item you wish to update: \n"+
                "1)First Name\t  2)Last Name\t 3)Email\n4)Username\t 5)Password\t 6)Back";
        System.out.println(menu);

        NavigateScreenQuestion prompt = new NavigateScreenQuestion(6);
        String userInput = reader.readLine();
        while(!prompt.validAnswer(userInput))
            userInput = reader.readLine();

        String[] questionTypeArray = {"firstname", "lastname", "email", "username", "password"};
        //TODO updating password should not allow for duplication.
        if(userInput.equals("6"))
            router.previousScreen();
        else {
            AppUser user = new AppUser(session.getEducation(), beforeUpdate.getId(), beforeUpdate.getFirstName(), beforeUpdate.getLastName(), beforeUpdate.getEmail(), beforeUpdate.getUsername(), beforeUpdate.getPassword());
            Question question = qFactory.getQuestion(questionTypeArray[Integer.parseInt(userInput)-1], serviceLink);

            String answer = reader.readLine();
            while (!question.validAnswer(answer))
                answer = reader.readLine();

            switch(userInput){
                case "1":
                    user.setFirstName(answer);
                    break;
                case "2":
                    user.setLastName(answer);
                    break;
                case "3":
                    user.setEmail(answer);
                case "4":
                    user.setUsername(answer);
                case "5":
                    user.setUsername(answer);
            }
            serviceLink.update(beforeUpdate, user);
            System.out.println("Your information has been updated!\n");
        }
    }
}
