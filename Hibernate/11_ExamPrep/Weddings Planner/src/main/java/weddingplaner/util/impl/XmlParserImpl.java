package weddingplaner.util.impl;

import org.springframework.stereotype.Component;
import weddingplaner.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class XmlParserImpl implements XmlParser {

    public XmlParserImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T objectFromFile(Class<T> tClass, String path) {
        try (final InputStream inputStream = new FileInputStream(path)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T) unmarshaller.unmarshal(inputStream);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> void objectToFile(T obj, String path) {
        try (final OutputStream outputStream = new FileOutputStream(path)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.displayName());
            marshaller.marshal(obj, outputStream);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}