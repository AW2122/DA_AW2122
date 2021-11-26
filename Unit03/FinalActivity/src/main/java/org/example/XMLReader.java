package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLReader extends DefaultHandler {
    ArrayList<Student> studentList = new ArrayList<>();
    protected Student student;
    protected String studentId;
    protected String courseId;
    protected String courseName;
    protected String subjectId;
    protected String subjectCourse;
    protected String subjectName;
    protected String subjectHours;
    protected String subjectYear;
    protected boolean isFirstName = false;
    protected boolean isLastName = false;
    protected boolean isEmail = false;
    protected boolean isPhone = false;
    protected boolean isCourse = false;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("student")) {
            student = new Student();
            studentId = attributes.getValue("id");
        }
        switch (qName) {
            case "firstname":
                isFirstName = true;
                break;
            case "lastname":
                isLastName = true;
                break;
            case "email":
                isEmail = true;
                break;
            case "phone":
                isPhone = true;
                break;
        }
        if (qName.equals("course")) {
            courseId = attributes.getValue("id");
        }
        if (qName.equals("subject")) {
            subjectId = attributes.getValue("id");
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }
}
