package app.retake.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import app.retake.util.FileUtil;
import app.retake.util.ValidatorUtil;
import app.retake.util.XmlParser;
import app.retake.util.impl.FileUtilImpl;
import app.retake.util.impl.ValidatorUtilImpl;
import app.retake.util.impl.XmlParserImpl;

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
