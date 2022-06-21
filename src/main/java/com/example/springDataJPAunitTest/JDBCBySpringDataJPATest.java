package com.example.springDataJPAunitTest;

import com.example.SpringDataJpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

// If the Unit test and Application class are not in the same package, must add the classes.
// After start the Application, the unit test start to run.
@SpringBootTest(classes = SpringDataJpaApplication.class)
public class JDBCBySpringDataJPATest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void JDBC_Insert1(){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlString = "INSERT INTO Animal(Name) VALUES('Bird222')";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            return ps;
        }, keyHolder);

        System.out.println("id="+keyHolder.getKey().longValue());
    }

    @Test
    public void JDBC_Update(){
        jdbcTemplate.execute("Update Animal set Name='hello Bird' where id='35' ");
    }

    @Test
    public void JDBC_Delete(){
        jdbcTemplate.execute("delete from Animal where id='35' ");
    }

    @Test
    public void JDBC_Select(){
        String sqlString = "select * from Animal ";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlString);
        for (Map<String, Object> entity: rows) {
            System.out.println("ID=" + entity.get("ID")  + " ## Name="+entity.get("Name"));
        }
    }

}
