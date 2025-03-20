package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.TeacherMapper;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher createNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        //create new teacher, generate key for teacher, store teacher info
        final String sql = "INSERT INTO teacher(tFName, tLName, dept) VALUES(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, teacher.getTeacherFName());
            statement.setString(2, teacher.getTeacherLName());
            statement.setString(3, teacher.getDept());
            return statement;
        }, keyHolder);

        teacher.setTeacherId(keyHolder.getKey().intValue());
        return teacher;
    }
    //YOUR CODE ENDS HERE

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        //get all teachers
        final String sql = "SELECT tid, tFName, tLName, dept FROM teacher";
        return jdbcTemplate.query(sql, new TeacherMapper());
    }
        //YOUR CODE ENDS HERE

    @Override
    public Teacher findTeacherById(int id) {
        //YOUR CODE STARTS HERE
        //find teacher by id, return teacher
        final String sql = "SELECT tid, tFName, tLName, dept FROM teacher WHERE tid = ?";
        return jdbcTemplate.queryForObject(sql, new TeacherMapper(), id);
    }
        //YOUR CODE ENDS HERE

    @Override
    public void updateTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        //update teacher information, personal and professional
        final String sql = "UPDATE teacher SET tFName = ?, tLName = ?, dept = ? WHERE tid = ?";
        jdbcTemplate.update(sql,
            teacher.getTeacherFName(),
            teacher.getTeacherLName(),
            teacher.getDept(),
            teacher.getTeacherId());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteTeacher(int id) {
        //YOUR CODE STARTS HERE
        // First update courses to set teacherId to null or reassign
        final String updateCourses = "UPDATE course SET teacherId = NULL WHERE teacherId = ?";
        jdbcTemplate.update(updateCourses, id);

        // Then delete the teacher
        final String deleteTeacher = "DELETE FROM teacher WHERE tid = ?";
        jdbcTemplate.update(deleteTeacher, id);
        //YOUR CODE ENDS HERE
    }
}