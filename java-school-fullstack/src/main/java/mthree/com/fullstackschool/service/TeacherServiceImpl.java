package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherServiceInterface {

    //YOUR CODE STARTS HERE
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }
    //YOUR CODE ENDS HERE

    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        // Simple pass-through method as specified in requirements
        return teacherDao.getAllTeachers();
        //YOUR CODE ENDS HERE
    }

    public Teacher getTeacherById(int id) {
        //YOUR CODE STARTS HERE
        try {
            // Attempt to find the teacher by ID
            return teacherDao.findTeacherById(id);
        } catch (DataAccessException ex) {
            // If teacher not found, return a Teacher object with "Not Found" messages
            Teacher notFound = new Teacher();
            notFound.setTeacherFName("Teacher Not Found");
            notFound.setTeacherLName("Teacher Not Found");
            return notFound;
        }
        //YOUR CODE ENDS HERE
    }

    public Teacher addNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        // Validate first name and last name
        if (teacher.getTeacherFName() == null || teacher.getTeacherFName().trim().isEmpty()) {
            teacher.setTeacherFName("First Name blank, teacher NOT added");
        }
        if (teacher.getTeacherLName() == null || teacher.getTeacherLName().trim().isEmpty()) {
            teacher.setTeacherLName("Last Name blank, teacher NOT added");
        }
        
        // Only create the teacher if both names are valid
        if (!teacher.getTeacherFName().equals("First Name blank, teacher NOT added") && 
            !teacher.getTeacherLName().equals("Last Name blank, teacher NOT added")) {
            return teacherDao.createNewTeacher(teacher);
        }
        
        return teacher;
        //YOUR CODE ENDS HERE
    }

    public Teacher updateTeacherData(int id, Teacher teacher) {
        //YOUR CODE STARTS HERE
        // Verify that the ID in the path matches the ID in the teacher object
        if (id != teacher.getTeacherId()) {
            teacher.setTeacherFName("IDs do not match, teacher not updated");
            teacher.setTeacherLName("IDs do not match, teacher not updated");
            return teacher;
        }

        // Update the teacher and return the updated version
        teacherDao.updateTeacher(teacher);
        return teacher;
        //YOUR CODE ENDS HERE
    }

    public void deleteTeacherById(int id) {
        //YOUR CODE STARTS HERE
        // Simple pass-through method as specified in requirements
        teacherDao.deleteTeacher(id);
        //YOUR CODE ENDS HERE
    }
}