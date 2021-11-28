package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
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
        if (cbStudentId.getSelectedItem() != null)
            tpReports.setText(db.getScores(cbStudentId.getSelectedItem().toString()));

        /**
         * When the Add Student (btnAdd) button is clicked, a new instance of Student is created and added to the
         * database using the addStudent method.
         */
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checks if any of the required fields are empty
                if (tfFirstName.getText().isBlank() || tfLastName.getText().isBlank() || tfIdCard.getText().isBlank())
                    lblStatus.setText("Some important fields are missing.");
                else {
                    // If the required fields are correct the Student constructor is called
                    Student student = new Student(tfFirstName.getText(), tfLastName.getText(), tfIdCard.getText());
                    // If any of the non required fields are filled in, they're assigned to the class' corresponding
                    // attribute
                    if (!tfMail.getText().isBlank()) {
                        student.setPhone(tfMail.getText());
                    }
                    if (!tfPhone.getText().isBlank()) {
                        student.setPhone(tfPhone.getText());
                    }
                    // If there's a duplicate primary key, it displays an error text in the status label
                    if (db.addStudent(student) == 23505)
                        lblStatus.setText("The student already exists.");
                    // If everything went well the comboboxes are updated and the status label displays a new message
                    else {
                        lblStatus.setText("Student added correctly.");
                        tpReports.setText(db.getScores(cbStudentId.getSelectedItem().toString()));
                        UpdateCombo();
                    }
                }
            }
        });
        /**
         * The enroll button (btnEnroll) when clicked gets the selected values in the comboboxes of the Enrollment pane,
         * gets the course code with a select from the database (course name has unique constraint). It checks wether
         * the student is already enrolled in the course and if the student has passed all the subjects in another course.
         */
        btnEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = cbStudent.getSelectedItem().toString();
                String courseName = cbCourse.getSelectedItem().toString();
                int courseCode = Integer.parseInt(db.getData("SELECT code FROM courses WHERE name = '" + courseName + "'").get(0));
                if(db.getData("SELECT * FROM enrollment WHERE student = '" + studentId + "' AND course = " + courseCode).size() > 0) {
                    lblStatus.setText("Student is already enrolled in this course.");
                }
                else if (Integer.parseInt(db.getData("SELECT COUNT(*) FROM enrollment INNER JOIN scores s on enrollment.code = s.enrollmentid WHERE score < 5 AND student = '" + studentId + "'").get(0)) > 0) {
                     lblStatus.setText("Student has not passed all the subjects in a currently enrolled course.");
                }
                else {
                    db.enrollStudent(studentId, courseCode);
                    lblStatus.setText("Student enrolled correctly.");
                }
            }
        });
        /**
         * When the Save button (btnSave) is clicked the getScores method is called to retrieve a specific student's
         * scores and writes them in a .txt file.
         */
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
                        Boolean save = true;
                        if (reportCard.exists()) {
                            if (JOptionPane.showConfirmDialog(fileChooser,
                                    "This file already exists. Do you wish to overwrite it?", "Overwrite",
                                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
                               save = false;
                            }
                        }
                        if (save) {
                            FileWriter fw = new FileWriter(reportCard);
                            fw.write(db.getScores(cbStudentId.getSelectedItem().toString()));
                            fw.flush();
                            fw.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        /**
         * When the Import XML button (btnImport) is clicked it creates an instance of SAXParser and XMLReader,
         * lets the user choose a XML file to import, calls the xmlTransaction method and passes the lists it
         * obtains from XMLReader. With this, students, courses and subjects found in the XML file will be added to
         * the database.
         */
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
                        if (db.xmlTransaction(xmlReader.getStudentList(), xmlReader.getCourseList(),
                                xmlReader.getSubjectList()) == 0)
                            lblStatus.setText("XML data imported successfully.");
                        else
                            lblStatus.setText("XML import failed.");
                    }
                } catch (ParserConfigurationException | SQLException | SAXException ex ) {
                    lblStatus.setText("XML import failed.");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        /**
         * Updates the textPane when the combobox value changes
         */
        cbStudentId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbStudentId.getSelectedItem() != null)
                    tpReports.setText(db.getScores(cbStudentId.getSelectedItem().toString()));
                else
                    UpdateCombo();
            }
        });
        /**
         * Updates the comboboxes and the status label everytime there's a pane change
         */
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                UpdateCombo();
                lblStatus.setText("");
            }
        });
    }

    /**
     * This method updates the comboboxes using DefaultComboBoxModel
     */
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