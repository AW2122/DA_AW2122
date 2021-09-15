import java.util.ArrayList;

public class Exercise02 {
    public static void main(String[] args) {

    }
    public static class Student {
        private String name;
        private int grade;

        public String getName() {
            return name;
        }
        public void setName (String name) {
            this.name = name;
        }
        public int getGrade() {
            return grade;
        }
        public void setGrade (int grade) {
            if (grade >= 0 && grade <= 10) {
                this.grade = grade;
            }
        }
        public boolean Passed() {
            if (grade >= 5) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    public static class Students {
        private ArrayList studentList = new ArrayList();
        private Object Student;

        // Adds new student to list.
        public void Add(Student stu) {
            studentList.add(stu);
        }
        // Returns the student in position number num
        public Student Obtain(int num) {
            if (num >= 0 && num <= studentList.size()) {
                return (Student)studentList.get(num);
            }
            return null;
        }
        // Returns the average grade of the students
        public float AverageGrade() {
            if (studentList.size() == 0) {
                return 0;
            }
            else {
                float average = 0;
                for (int i = 0; i < studentList.size(); i++) {
                    average += ((Student)studentList.get(i)).grade;
                }
                return (average / studentList.size());
            }
        }
    }
}
