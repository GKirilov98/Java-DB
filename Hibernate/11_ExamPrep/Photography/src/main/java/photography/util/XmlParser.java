package photography.util;

public interface XmlParser {

     <T> T objectFromFile(Class<T> tClass, String path);
     <T> void objectToFile(T obj, String path);
}
