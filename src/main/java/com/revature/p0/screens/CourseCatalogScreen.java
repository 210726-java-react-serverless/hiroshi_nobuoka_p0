package com.revature.p0.screens;

import com.revature.p0.documents.Course;
import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.questions.Question;
import com.revature.p0.questions.courseQuestions.CourseTagQuestion;
import com.revature.p0.services.CourseService;
import com.revature.p0.util.QuestionFactory;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class CourseCatalogScreen extends Screen{
    static final Logger logger = LogManager.getLogger(RegisterScreen.class);
    private final CourseService service;
    private UserSession session;
    QuestionFactory qFactory = QuestionFactory.getInstance();

    public CourseCatalogScreen(BufferedReader reader, ScreenRouter router, CourseService service, UserSession session) {
        super("Course Catalog Screen", "/catalog", reader, router);
        this.service = service;
        this.session = session;
    }

    @Override
    public void render() {
        try {
            System.out.println("\n**COURSE CATALOG**\n");
            List<Course> courses = service.getCourses(session.getCurrentUser(), "all");
            System.out.println("Below is a list of all available courses:\n");
            for (Course course : courses)
                System.out.println(course.getCourseTag() + "\t" + course.getCourseName() + "\t" + course.getInstructor());
            String menu =
                    "\n1)Register for a course\n" +
                            "2)Drop a course\n" +
                            "3)Back\n";

            System.out.println(menu);

            Question prompt = new NavigateScreenQuestion(3);
            String userInput = reader.readLine();
            while (!prompt.validAnswer(userInput)) {
                userInput = reader.readLine();
            }


        switch(userInput) {
            case "1":
                System.out.println("Please enter the tag for the course you would like to register for.");
                CourseTagQuestion questionOne = (CourseTagQuestion) qFactory.getCourseQuestion("coursetag", service);
                String registerFor = reader.readLine();
                if (!questionOne.validAnswer(registerFor, "register")) {
                    System.out.println("Tag entered not found. Please try again.");
                    break;
                }
                service.registerStudent(registerFor, session);
                break;
            case "2":
                System.out.println("Below is a list of courses you can drop:");
                List<Course> myCourses = service.getCourses(session.getCurrentUser());
                for (Course course : myCourses)
                    System.out.println(course.getCourseTag() + "\t" + course.getCourseName() + "\t" + course.getInstructor());
                System.out.println("");
                CourseTagQuestion questionTwo = (CourseTagQuestion) qFactory.getCourseQuestion("coursetag", service);
                String dropCourse = reader.readLine();
                if (!questionTwo.validAnswer(dropCourse, "drop")) {
                    System.out.println("Tag entered not found. Please try again.");
                    break;
                }
                service.dropCourse(dropCourse, session);
                break;
            case "3":
                router.previousScreen();
        }
        } catch (NullPointerException npe) {
                logger.debug("Courses not rendered due to " + npe);
        } catch (IOException ioe) {
                ioe.printStackTrace();
            }

    }
}
