package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.questions.Question;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;

import java.io.BufferedReader;

public class UpdateScreen {
    private UserService service;
    private ScreenRouter router;
    private UserSession session;
    private BufferedReader reader;
    QuestionFactory qFactory = QuestionFactory.getInstance();

    public UpdateScreen(UserService service, ScreenRouter router, UserSession session, BufferedReader reader){
        this.service = service;
        this.router = router;
        this.session = session;
        this.reader = reader;
    }

    public void render() throws Exception{
        AppUser user = session.getCurrentUser();
        String currentInfo = "First name: "+user.getFirstName()+"\t"
                +"Last name: "+user.getLastName()+"\n"
                +"ID: "+user.getId()+"\t"
                +"Email: "+user.getEmail()+"\n"
                +"Username: "+user.getUsername()+"\t"
                +"Password: "+user.getPassword()+"\n"; //TODO Find way to encrypt

        String menu = "Please select the item you wish to update: \n"+
                "1)First Name\t  2)Last Name\t 3)Email\n4)Username\t 5)Password";

        int userEntry = Integer.parseInt(reader.readLine());

        String[] questionTypeArray = {"firstname", "lastname", "email", "username", "password"};
        //TODO updating password should not allow for duplication.

        Question question = qFactory.getQuestion(questionTypeArray[userEntry], service);
        String answer = reader.readLine();
        while(!question.validAnswer(answer))
            answer = reader.readLine();

        user.getProperties().replace(questionTypeArray[userEntry], answer);
        service.register(user,"update");
    }
}
