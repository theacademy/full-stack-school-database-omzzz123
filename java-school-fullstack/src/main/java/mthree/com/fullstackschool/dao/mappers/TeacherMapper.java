package mthree.com.fullstackschool.dao.mappers;

import mthree.com.fullstackschool.model.Teacher;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

// Maps database rows to Teacher objects for Spring JDBC
public class TeacherMapper implements RowMapper<Teacher> {
    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        //YOUR CODE STARTS HERE

        // Create new Teacher object and set its properties from the result set
        Teacher teacher = new Teacher();
        teacher.setTeacherId(rs.getInt("tid"));
        teacher.setTeacherFName(rs.getString("tFName"));
        teacher.setTeacherLName(rs.getString("tLName"));
        teacher.setDept(rs.getString("dept"));
        return teacher;

        //YOUR CODE ENDS HERE
    }
}