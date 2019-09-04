package softuni.exam.util;

public interface XmlParser {
        <T> T objectFromFile(Class<T> tClass, String path);
}
