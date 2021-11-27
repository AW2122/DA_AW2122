package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLReader extends DefaultHandler {
    ArrayList<Student> studentList = new ArrayList<>();
    ArrayList<Subject> subjectList = new ArrayList<>();
    ArrayList<Course> courseList = new ArrayList<>();
    protected boolean isSubject;
    protected Student student;
    protected Subject subject;
    protected Course course;
    protected String tagContent;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("student")) {
            student = new Student();
            student.setIdCard(attributes.getValue("id"));
        }
        if (qName.equals("course")) {
            course = new Course();
            course.setId(attributes.getValue("id"));
        }
        if (qName.equals("subject")) {
            subject = new Subject();
            isSubject = true;
            subject.setId(attributes.getValue("id"));
            subject.setCourseId(attributes.getValue("course"));
        }
    }
    public void characters(char[] ch, int start, int length) throws SAXException {
        tagContent = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "firstname": student.setFirstName(tagContent);break;
            case "lastname": student.setLastName(tagContent);break;
            case "email": student.setEmail(tagContent); break;
            case "phone": student.setPhone(tagContent); break;
            case "student": studentList.add(student); break;
            case "hours": subject.setHours(tagContent); break;
            case "year": subject.setYear(tagContent); break;
            case "subject": subjectList.add(subject); break;
            case "course": courseList.add(course); break;
        }
        if (qName.equals("name")) {
            if (isSubject)
                subject.setName(tagContent);
            else
                course.setName(tagContent);
        }
    }
}
