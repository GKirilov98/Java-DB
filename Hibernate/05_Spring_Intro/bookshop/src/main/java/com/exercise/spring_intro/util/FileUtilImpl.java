package com.exercise.spring_intro.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtilImpl implements FileUtil {

    @Override
    public String[] fileContent(String path) {
        File file = new File(path);
        List<String> fileInfo = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null){
                fileInfo.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return fileInfo.stream()
                .filter(l -> !l.equals(""))
                .toArray(String[]::new);
    }
}
