package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;

public class UserInterface {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JLabel lblFirstName;
    private JPanel pnlStudent;
    private JPanel pnlEnrollments;
    private JPanel pnlReports;
    private JPanel pnlUtilities;
    private JLabel lblLastName;
    private JLabel lblIdCard;
    private JLabel lblEmail;
    private JLabel lblPhone;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfIdCard;
    private JTextField tfMail;
    private JTextField tfPhone;
    private JButton btnAdd;
    private JComboBox cbStudent;
    private JComboBox cbCourse;
    private JButton btnEnroll;
    private JLabel lblStudentE;
    private JLabel lblCourse;
    private JComboBox cbStudentId;
    private JTextPane tpReports;
    private JLabel lblStudentR;
    private JButton btnImport;
    private JLabel lblStatus;
    private JButton btnSave;
    private JLabel lblError;
    private Database db = new Database();

    public UserInterface() {
        UpdateCombo();
        tpReports.setText(db.GetScores(cbStudentId.getSelectedItem().toString()));

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfFirstName.getText().isBlank() || tfLastName.getText().isBlank() || tfIdCard.getText().isBlank())
                    lblStatus.setText("Some important fields are missing.");
                else {
                    Student student = new Student(tfFirstName.getText(), tfLastName.getText(), tfIdCard.getText());
                    if (!tfMail.getText().isBlank()) {
                        student.setPhone(tfMail.getText());
                    }
                    if (!tfPhone.getText().isBlank()) {
                        student.setPhone(tfPhone.getText());
                    }
                    if (db.addStudent(student) == 23505)
                        lblStatus.setText("The student already exists.");
                    else {
                        lblStatus.setText("Student added correctly.");
                        tpReports.setText(db.GetScores(cbStudentId.getSelectedItem().toString()));
                        UpdateCombo();
                    }
                }
            }
        });

        btnEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = cbStudent.getSelectedItem().toString();
                String courseName = cbCourse.getSelectedItem().toString();
                int courseCode = Integer.parseInt(db.getData("SELECT code FROM courses WHERE name = '" + courseName + "'").get(0));
                if(db.getData("SELECT * FROM enrollment WHERE student = '" + studentId + "' AND course = " + courseCode).size() > 0) {
                    lblStatus.setText("Student is already enrolled in this course.");
                }
                else if (Integer.parseInt(db.getData("SELECT COUNT(*) FROM enrollment INNER JOIN scores s on enrollment.code = s.enrollmentid WHERE score <= 5 AND student = '" + studentId + "'").get(0)) > 0) {
                     lblStatus.setText("Student hasn't passed all subjects in currently enrolled course.");
                }
                else {
                    db.enrollStudent(studentId, courseCode);
                    lblStatus.setText("Student enrolled correctly.");
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = db.getData("SELECT firstname FROM student WHERE idcard = '" + cbStudentId.getSelectedItem().toString() + "'").get(0);
                String studentSurname = db.getData("SELECT lastname FROM student WHERE idcard = '" + cbStudentId.getSelectedItem().toString() + "'").get(0);
                String reportCardName =  studentName + "_" + studentSurname + "_ReportCard.txt";
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("*.TXT", "txt");
                fileChooser.setFileFilter(txtFilter);
                fileChooser.setSelectedFile(new File(reportCardName));
                int selection = fileChooser.showSaveDialog(new JFrame());
                if (selection == JFileChooser.APPROVE_OPTION) {
                    try {
                        File reportCard = fileChooser.getSelectedFile();
                        FileWriter fw = new FileWriter(reportCard);
                        fw.write(db.GetScores(cbStudentId.getSelectedItem().toString()));
                        fw.flush();
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                UpdateCombo();
                lblStatus.setText("");
            }
        });

        btnImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                    XMLReader xmlReader = new XMLReader();
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    int selection = fileChooser.showOpenDialog(new JFrame());
                    if (selection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        saxParser.parse(selectedFile, xmlReader);
                        if (db.XMLTransaction(xmlReader.getStudentList(), xmlReader.getCourseList(), xmlReader.getSubjectList()) == 23505) {
                            lblStatus.setText("XML import failed. Duplicate key value detected.");
                        }
                        else
                            lblStatus.setText("XML data imported correctly.");
                    }
                } catch (ParserConfigurationException | SQLException | SAXException ex ) {
                    lblStatus.setText("XML import failed.");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        cbStudentId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbStudentId.getSelectedItem() != null)
                    tpReports.setText(db.GetScores(cbStudentId.getSelectedItem().toString()));
                else
                    UpdateCombo();
            }
        });
    }

    public void UpdateCombo() {
        DefaultComboBoxModel studentComboBoxModel = new DefaultComboBoxModel();
        DefaultComboBoxModel courseComboBoxModel = new DefaultComboBoxModel();
        for (String StudentId: db.getData("SELECT idCard FROM student")) {
            studentComboBoxModel.addElement(StudentId);
        }
        for (String courseName: db.getData("SELECT name FROM courses")) {
            courseComboBoxModel.addElement(courseName);
        }
        cbCourse.setModel(courseComboBoxModel);
        cbStudent.setModel(studentComboBoxModel);
        cbStudentId.setModel(studentComboBoxModel);
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}