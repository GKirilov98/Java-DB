package softuni.shop.util;

public interface FileUtil {
    String fileReader(String filePath);
    boolean fileWriter(String filePath, String content);
}
