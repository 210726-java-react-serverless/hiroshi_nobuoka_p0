package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.documents.Course;
import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.questions.Question;
import com.revature.p0.questions.courseQuestions.CourseTagQuestion;
import com.revature.p0.services.CourseService;
import com.revature.p0.services.UserService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class FacultyRegistrationScreen extends Screen{
    static final Logger logger = LogManager.getLogger(RegisterScreen.class);
    private final CourseService service;
    private UserSession session;
    QuestionFactory qFactory = QuestionFactory.getInstance();

    public FacultyRegistrationScreen(BufferedReader reader, ScreenRouter router, CourseService service, UserSession session){
        super("Faculty Registration Screen", "/freg",reader,router);
        this.service = service;
        this.session = session;
    }

    @Override
    public void render() throws Exception {
        String courseList = "List courses here\n";
        String menu = "You're on the course management screen.\n" +
                "1)Create a course\n" +
                "2)Remove a course\n" +
                "3)Update course info\n" +
                "4)Back\n";

        System.out.println(courseList);
        System.out.println(menu);

        Question prompt = new NavigateScreenQuestion(4);
        String userInput = reader.readLine();
        while (!prompt.validAnswer(userInput)) {
            userInput = reader.readLine();
        }

        switch (userInput) {
            case "1":
                String[] questionTypeArray = {"coursetag", "coursename"};
                String[] answerArray = new String[questionTypeArray.length];

                //ask each question questionArray, and store each answer in answerArray
                for (int i = 0; i < questionTypeArray.length; i++) {
                    Question question = qFactory.getCourseQuestion(questionTypeArray[i],service);
                    String answer = reader.readLine();
                    while (!question.validAnswer(answer)) {answer = reader.readLine();}
                    answerArray[i] = answer;
                }
                Course newCourse = service.createCourse(answerArray);
                logger.info("new course "+ newCourse.getCourseName()+" instantiation complete");

                service.registerCourse(newCourse, "new");
                System.out.println("Registration successful!");
                break;
            case "2":
                //Forced solution. Overloaded question validation method since finding an existing course IS a valid answer, here.
                CourseTagQuestion question = (CourseTagQuestion) qFactory.getCourseQuestion("coursetag", service);
                String tag = reader.readLine();
                if (question.validAnswer(tag, "delete"))
                    service.removeCourse(tag);
                break;
            case "3":
                
                break;
            case "4":
                router.previousScreen();
        }
    }
}
