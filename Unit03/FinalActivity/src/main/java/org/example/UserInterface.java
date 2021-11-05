package org.example;

import javax.swing.*;

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
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton btnAdd;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton btnEnroll;
    private JLabel lblStudentE;
    private JLabel lblCourse;
    private JComboBox comboBox3;
    private JTextPane tpReports;
    private JButton btnPrint;
    private JLabel lblStudentR;
    private JButton btnImport;

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
