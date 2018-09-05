package com.db.gerasin.testframework.dao;

import com.db.gerasin.testframework.service.SqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SourceDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlParser sqlParser;

    @PostConstruct
    public void init() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:mem");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void saveAll(String tableName, List<Map<String, String>> sources) {
        for (Map<String, String> source : sources) {
            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for (Map.Entry<String, String> entry : source.entrySet()) {
                keys.add(entry.getKey());
                values.add(entry.getValue());
            }

            jdbcTemplate.update(
                    "INSERT INTO ?(?) values(?)",
                    tableName,
                    String.join(",", keys),
                    String.join(",", values));
        }
    }

    public List<Map<String, String>> findAll(String tableName) {
        return jdbcTemplate.query("select * from ?",
                new Object[]{tableName},
                (rs, rowNum) -> convertResultSetToList(rs));
    }

    private Map<String, String> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        Map<String, String> row = new HashMap<>(columns);
        while (rs.next()) {
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getString(i));
            }
        }

        return row;
    }
}
