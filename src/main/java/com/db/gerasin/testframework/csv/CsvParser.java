package com.db.gerasin.testframework.csv;

import com.db.gerasin.testframework.TestFrameworkApplication;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CsvParser {

    /*@SneakyThrows
    public static void main(String[] args) {
        File file = new File(getPathName());
        List<Map<String, String>> readList = read(file);
        System.out.println(readList);
    }*/

    protected static String getPathName() {
        return TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/expected.csv";
    }

    @SneakyThrows
    public List<Map<String, String>> readFromFile() {
        List<Map<String, String>> response = new LinkedList<>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        MappingIterator<Map<String, String>> iterator = mapper.readerFor(Map.class)
                .with(schema)
                .readValues(new File(getPathName()));
        while (iterator.hasNext()) {
            response.add(iterator.next());
        }
        return response;
    }
}
