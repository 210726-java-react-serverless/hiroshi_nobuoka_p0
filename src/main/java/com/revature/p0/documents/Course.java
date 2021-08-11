package com.revature.p0.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    @JsonProperty("_id")private String courseId;
    private String courseTag;
    private String courseName;
    private String instructor;
    private String instructorId;
    private List<String> enrolled = new ArrayList<>();

    public Course(String courseTag, String courseName, AppUser instructor) {
        this.courseTag = courseTag;
        this.courseName = courseName;
        this.instructor = instructor.getFirstName()+" "+instructor.getLastName();
        this.instructorId = instructor.getId();
    }

    public Course(String courseId, String courseTag, String courseName, AppUser instructor,  List<String> enrolled) {
        this.courseId = courseId;
        this.courseTag = courseTag;
        this.courseName = courseName;
        this.instructor = instructor.getFirstName()+" "+instructor.getLastName();
        this.instructorId = instructor.getId();
        this.enrolled = enrolled;
    }
    public Document toDocument(){
        Document newUserDoc = new Document("coursetag", this.getCourseTag())
                .append("coursename", this.getCourseName())
                .append("instructor", this.getInstructor())
                .append("instructorId", this.getInstructorId())
                .append("enrolled", this.getEnrolled());
        return newUserDoc;
    }

    public String getInstructorId() {return instructorId;}

    public void setInstructorId(String instructorId) {this.instructorId = instructorId;}

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTag() {
        return courseTag;
    }

    public void setCourseTag(String courseTag) {
        this.courseTag = courseTag;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public List<String> getEnrolled() {
        return enrolled;
    }
    public void setEnrolled(List<String> enrolled) {
        this.enrolled = enrolled;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) && Objects.equals(courseTag, course.courseTag) && Objects.equals(courseName, course.courseName) && Objects.equals(instructor, course.instructor) && Objects.equals(instructorId, course.instructorId) && Objects.equals(enrolled, course.enrolled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseTag, courseName, instructor, instructorId, enrolled);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseTag='" + courseTag + '\'' +
                ", courseName='" + courseName + '\'' +
                ", instructor=" + instructor +
                ", enrolled=" + enrolled +
                '}';
    }
}
