package softuni.carshop.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import softuni.carshop.util.ValidatorUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorUtilImpl implements ValidatorUtil {
    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }


    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).size() == 0;
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        return this.validator.validate(entity);
    }
}
