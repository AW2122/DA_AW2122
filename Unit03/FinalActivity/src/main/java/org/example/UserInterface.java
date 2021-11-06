package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JComboBox comboBox3;
    private JTextPane tpReports;
    private JButton btnPrint;
    private JLabel lblStudentR;
    private JButton btnImport;
    private JLabel lblStatus;
    private Database db = new Database();

    public UserInterface() {
        UpdateCombo();
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfFirstName.getText().isBlank() || tfLastName.getText().isBlank() || tfIdCard.getText().isBlank() || tfMail.getText().isBlank() || tfPhone.getText().isBlank())
                    lblStatus.setText("Some fields are missing.");
                else {
                    Student student = new Student(tfFirstName.getText(), tfLastName.getText(), tfIdCard.getText(), tfMail.getText(), tfPhone.getText());
                    if (db.addStudent(student) == 1)
                        lblStatus.setText("The student already exists.");
                    else
                        lblStatus.setText("Student added correctly.");
                }

            }
        });
        btnEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = cbCourse.getSelectedItem().toString();
                String courseCode = db.getData("courses", 1, courseName, "name").get(0);
                db.enrollStudent(cbStudent.getSelectedItem().toString(), Integer.parseInt(courseCode));
            }
        });
    }
    public void UpdateCombo() {
        for (String StudentId: db.getData("student", 3, "","idCard")) {
            cbStudent.addItem(StudentId);
        }
        for (String courseId: db.getData("courses", 2, "", "*")) {
            cbCourse.addItem(courseId);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
