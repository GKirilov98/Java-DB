package softuni.shop.util.impl;

import softuni.shop.util.FileUtil;

import java.io.*;

public class FileUtilImpl implements FileUtil {

    @Override
    public String fileContent(String filePath) {
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
    public boolean fileWriter(String filePath,String jsonString) {

        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);
            fileWriter.close();
            return true;
        } catch (IOException e) {
           e.getMessage();
        }

        return false;
    }
}
