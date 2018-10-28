package com.yourfounds.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

//    private String email;

//    @Override
//    public void initialize(ValidEmail constraint) {
//       email = constraint.value();
//    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email != null) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }

    }
}
