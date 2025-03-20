package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    //YOUR CODE STARTS HERE
    private final CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    //YOUR CODE ENDS HERE

    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE
        // Simple pass-through method as specified in requirements
        return courseDao.getAllCourses();
        //YOUR CODE ENDS HERE
    }

    public Course getCourseById(int id) {
        //YOUR CODE STARTS HERE
        try {
            // Attempt to find the course by ID
            return courseDao.findCourseById(id);
        } catch (DataAccessException ex) {
            // If course not found, return a Course object with "Not Found" messages
            Course notFound = new Course();
            notFound.setCourseName("Course Not Found");
            notFound.setCourseDesc("Course Not Found");
            return notFound;
        }
        //YOUR CODE ENDS HERE
    }

    public Course addNewCourse(Course course) {
        //YOUR CODE STARTS HERE
        // Validate course name and description
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            course.setCourseName("Name blank, course NOT added");
        }
        if (course.getCourseDesc() == null || course.getCourseDesc().trim().isEmpty()) {
            course.setCourseDesc("Description blank, course NOT added");
        }
        
        // Only create the course if both name and description are valid
        if (!course.getCourseName().equals("Name blank, course NOT added") && 
            !course.getCourseDesc().equals("Description blank, course NOT added")) {
            return courseDao.createNewCourse(course);
        }
        
        return course;
        //YOUR CODE ENDS HERE
    }

    public Course updateCourseData(int id, Course course) {
        //YOUR CODE STARTS HERE
        // Verify that the ID in the path matches the ID in the course object
        if (id != course.getCourseId()) {
            // When IDs don't match, we need to modify the passed course object
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
            return course;
        }

        // Update the course and return the updated version
        courseDao.updateCourse(course);
        return course;
        //YOUR CODE ENDS HERE
    }

    public void deleteCourseById(int id) {
        //YOUR CODE STARTS HERE
        // Delete the course
        courseDao.deleteCourse(id);
        // Print confirmation message to server console as specified
        System.out.println("Course ID: " + id + " deleted");
        //YOUR CODE ENDS HERE
    }
}