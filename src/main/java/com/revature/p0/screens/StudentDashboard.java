package com.revature.p0.screens;

import com.revature.p0.documents.Course;
import com.revature.p0.questions.NavigateScreenQuestion;
import com.revature.p0.services.CourseService;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;


import java.io.BufferedReader;
import java.util.List;

public class StudentDashboard extends Screen{
    private ScreenRouter router;
    private BufferedReader reader;
    private CourseService service;
    private UserSession session;

    public StudentDashboard(BufferedReader reader, ScreenRouter router, CourseService service, UserSession session) {
        super("Student Dashboard", "/sdash", reader, router);
        this.service = service;
        this.session = session;
        this.reader = reader;
        this.router = router;
    }

    @Override
    public void render() throws Exception {
        System.out.println("\n**STUDENT DASHBOARD**\n");
        String menu = "1)View course catalog\n"+
                "2)Print schedule\n"+
                "3)Update personal info\n"+
                "4)Back\n";
        System.out.println(menu);

        NavigateScreenQuestion prompt = new NavigateScreenQuestion(4);
        String userInput = reader.readLine();
        while(!prompt.validAnswer(userInput))
            userInput = reader.readLine();

        switch(userInput){
            case "1":
                router.navigate("/catalog");
                break;
            case "2":
                List<Course> myCourses = service.getCourses(session.getCurrentUser());
                System.out.println("\n*SCHEDULE*: \n");
                for (Course course : myCourses)
                    System.out.println(course.getCourseTag() + "\t" + course.getCourseName() + "\t" + course.getInstructor());
                break;
            case "3":
                router.navigate("/update");
                break;
            case "4":
                router.navigate("/login");
        }
    }
}
