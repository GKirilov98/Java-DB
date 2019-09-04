package app.ccb.util.impl;

import app.ccb.util.FileUtil;

import java.io.*;

public class FileUtilImpl implements FileUtil {

    @Override
    public String fileReader(String filePath) {
        File file  = new File(filePath);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader( new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line =  reader.readLine()) != null ){
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString().trim();
    }

    @Override
    public boolean fileWriter(String filePath,String content) {

        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
           e.getMessage();
        }

        return false;
    }
}
