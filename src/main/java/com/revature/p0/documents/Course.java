package com.revature.p0.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import java.util.Objects;

public class Course {
    @JsonProperty("_id")private String courseId;
    private String courseTag;
    private String courseName;
    private String instructor;
    private int enrolled;

    public Course(String courseTag, String courseName, String instructor) {
        this.courseTag = courseTag;
        this.courseName = courseName;
        this.instructor = instructor;
    }
    public Document toDocument(){

        Document newUserDoc = new Document("coursetag", this.getCourseTag())
                .append("coursename", this.getCourseName())
                .append("instructor", this.getInstructor());
        return newUserDoc;
    }

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

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return enrolled == course.enrolled && Objects.equals(courseId, course.courseId) && Objects.equals(courseTag, course.courseTag) && Objects.equals(courseName, course.courseName) && Objects.equals(instructor, course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseTag, courseName, instructor, enrolled);
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
