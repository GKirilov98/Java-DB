package softuni.shop.util;

public interface FileUtil {
    String fileContent(String filePath);
    boolean fileWriter(String filePath, String jsonString);
}
