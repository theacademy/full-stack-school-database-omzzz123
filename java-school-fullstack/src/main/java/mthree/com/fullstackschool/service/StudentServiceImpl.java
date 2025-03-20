package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE
    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE
        // Simple pass-through method as specified in requirements
        return studentDao.getAllStudents();
        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE
        try {
            // Attempt to find the student by ID
            return studentDao.findStudentById(id);
        } catch (DataAccessException ex) {
            // If student not found, return a Student object with "Not Found" messages
            Student notFound = new Student();
            notFound.setStudentFirstName("Student Not Found");
            notFound.setStudentLastName("Student Not Found");
            return notFound;
        }
        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE
        // Validate first name and last name
        if (student.getStudentFirstName() == null || student.getStudentFirstName().trim().isEmpty()) {
            student.setStudentFirstName("First Name blank, student NOT added");
        }
        if (student.getStudentLastName() == null || student.getStudentLastName().trim().isEmpty()) {
            student.setStudentLastName("Last Name blank, student NOT added");
        }
        
        // Only create the student if both names are valid
        if (!student.getStudentFirstName().equals("First Name blank, student NOT added") && 
            !student.getStudentLastName().equals("Last Name blank, student NOT added")) {
            return studentDao.createNewStudent(student);
        }
        
        return student;
        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE
        // Verify that the ID in the path matches the ID in the student object
        if (id != student.getStudentId()) {
            // When IDs don't match, modify the original student object
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }

        // Update the student and return the updated version
        studentDao.updateStudent(student);
        return studentDao.findStudentById(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE
        // Simple pass-through method as specified in requirements
        studentDao.deleteStudent(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        // Get student and course to verify they exist
        Student student = getStudentById(studentId);
        Course course = new CourseServiceImpl(null).getCourseById(courseId);

        // Check if student exists
        if (student.getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
            return;
        }

        // Check if course exists
        if (course.getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
            return;
        }

        // Delete student from course and print confirmation
        studentDao.deleteStudentFromCourse(studentId, courseId);
        System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        // Get student and course to verify they exist
        Student student = getStudentById(studentId);
        Course course = new CourseServiceImpl(null).getCourseById(courseId);

        // Check if student exists
        if (student.getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
            return;
        }

        // Check if course exists
        if (course.getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
            return;
        }

        try {
            // Attempt to add student to course
            studentDao.addStudentToCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " added to course: " + courseId);
        } catch (Exception ex) {
            // Handle case where student is already enrolled
            System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
        }
        //YOUR CODE ENDS HERE
    }
}