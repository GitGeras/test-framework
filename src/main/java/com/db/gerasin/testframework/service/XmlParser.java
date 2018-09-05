package com.db.gerasin.testframework.service;

import com.db.gerasin.testframework.TestFrameworkApplication;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XmlParser {

    private List<Map<String, String>> predefinedPeople = new ArrayList<>();
    private XStream xStream = new XStream();

    @PostConstruct
    private void init() {
        xStream.registerConverter(new MapEntryConverter());
        DataFactory dataFactory = new DataFactory();

        for (int i = 1; i < 5; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(i));
            map.put("name", dataFactory.getName());
            map.put("salary", dataFactory.getNumberText(4));
            predefinedPeople.add(map);
        }
    }

    @SneakyThrows
    public List<Map<String, String>> readFromFile() {
        File file = new File(getPathName());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        return fromXmlToMap(result);
    }

    protected static String getPathName() {
        return TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/source.xml";
    }

    private List<Map<String, String>> fromXmlToMap(String xml) throws IOException {

//        XmlMapper mapper = new XmlMapper();
//        ArrayList<Person> arrayList = mapper.readValue(xml, new TypeReference<List<Person>>(){});
//        return ((PersonList) xStream.fromXML(xml)).getList();
        return (List<Map<String, String>>) xStream.fromXML(xml);
    }

    @SneakyThrows
    public void writeToXml() {
//        XmlMapper mapper = new XmlMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(getPathName());
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("Write to file:");
        xStream.toXML(predefinedPeople, new FileWriter(file));
//        mapper.writeValue(file, predefinedPeople);

    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "chris");
        map.put("island", "faranga");

// convert to XML
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("map", java.util.Map.class);
        String xml = xStream.toXML(map);

// from XML, convert back to map
        Map<String, Object> map2 = (Map<String, Object>) xStream.fromXML(xml);

        System.out.println(map2);
    }


    public static class MapEntryConverter implements Converter {

        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if (null != val) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }

        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

            Map<String, String> map = new HashMap<String, String>();

            while (reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }
}


