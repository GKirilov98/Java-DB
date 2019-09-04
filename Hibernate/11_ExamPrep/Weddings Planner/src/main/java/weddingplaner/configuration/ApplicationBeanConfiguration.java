package weddingplaner.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import weddingplaner.util.FileUtil;
import weddingplaner.util.ValidatorUtil;
import weddingplaner.util.XmlParser;
import weddingplaner.util.impl.FileUtilImpl;
import weddingplaner.util.impl.ValidatorUtilImpl;
import weddingplaner.util.impl.XmlParserImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@Configuration
public class ApplicationBeanConfiguration    {
    @Bean
    public Gson gson (){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls() // Show null values!!!!!!!!!!!!!
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }

    @Bean
    public FileUtil fileUtil(){
        return new FileUtilImpl();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public BufferedReader bufferedReader(){
        return  new BufferedReader( new InputStreamReader(System.in));
    }
}
