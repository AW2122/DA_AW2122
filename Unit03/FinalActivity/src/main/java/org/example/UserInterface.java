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
                    if (db.addStudent(student) == 1)
                        lblStatus.setText("The student already exists.");
                    else
                        lblStatus.setText("Student added correctly.");
                }
                UpdateCombo();
            }
        });
        btnEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = cbStudent.getSelectedItem().toString();
                String courseName = cbCourse.getSelectedItem().toString();
                int courseCode = Integer.parseInt(db.getData("SELECT code FROM courses WHERE name = '" + courseName + "'").get(0));
                if(db.getData("SELECT * FROM enrollment WHERE student = '" + studentId + "' AND course = " + courseCode).size() > 0) {
                    // Error alumno ya matriculado
                    System.out.println("error");
                }
                else if (Integer.parseInt(db.getData("SELECT COUNT(*) FROM enrollment INNER JOIN scores s on enrollment.code = s.enrollmentid WHERE score <= 5 AND student = '" + studentId + "'").get(0)) > 0) {
                     // Error asignaturas suspensas
                    System.out.println("error");
                }
                else {
                    db.enrollStudent(studentId, courseCode);
                }
            }
        });
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tpReports.setText(db.GetScores(comboBox3.getSelectedItem().toString()));
            }
        });
    }
    public void UpdateCombo() {
        cbCourse.removeAllItems();
        cbStudent.removeAllItems();
        for (String StudentId: db.getData("SELECT idCard FROM student")) {
            cbStudent.addItem(StudentId);
            comboBox3.addItem(StudentId);
        }
        for (String courseName: db.getData("SELECT name FROM courses")) {
            cbCourse.addItem(courseName);
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
