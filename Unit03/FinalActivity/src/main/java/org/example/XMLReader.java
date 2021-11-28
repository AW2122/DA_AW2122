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

    /**
     * Getters for the object lists so that they can be accessed by other classes.
     * @return the different object lists.
     */
    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public ArrayList<Subject> getSubjectList() {
        return subjectList;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    /**
     * This method reads the opening tags in the XML file.
     * @param uri
     * @param localName
     * @param qName the XML tag names.
     * @param attributes
     * @throws SAXException
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("student")) {
            student = new Student();
            student.setIdCard(attributes.getValue("id"));
        }
        if (qName.equals("course")) {
            course = new Course();
            course.setId(Integer.parseInt(attributes.getValue("id")));
        }
        if (qName.equals("subject")) {
            subject = new Subject();
            isSubject = true;
            subject.setId(Integer.parseInt(attributes.getValue("id")));
            subject.setCourseId(Integer.parseInt(attributes.getValue("course")));
        }
    }

    /**
     * Creates a string with the current tag's content.
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        tagContent = new String(ch, start, length);
    }

    /**
     * This method reads the closing tags and when they match the switch cases it adds the value of tagContent to the
     * specified object attribute or adds an object to its corresponding list.
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "firstname": student.setFirstName(tagContent);break;
            case "lastname": student.setLastName(tagContent);break;
            case "email": student.setEmail(tagContent); break;
            case "phone": student.setPhone(tagContent); break;
            case "student": studentList.add(student); break;
            case "hours": subject.setHours(Integer.parseInt(tagContent)); break;
            case "year": subject.setYear(Integer.parseInt(tagContent)); break;
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
