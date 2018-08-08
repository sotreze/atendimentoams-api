package com.atendimentoams.api.seguranca.constraint;

import org.apache.commons.beanutils.BeanUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String campoPrimeiroNome;
    private String campoSobreNome;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        campoPrimeiroNome = constraintAnnotation.primeiro();
        campoSobreNome = constraintAnnotation.segundo();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Object primeiroObj = BeanUtils.getProperty(value, campoPrimeiroNome);
            final Object segundoObj = BeanUtils.getProperty(value, campoSobreNome);
            return primeiroObj == null && segundoObj == null || primeiroObj != null && primeiroObj.equals(segundoObj);
        } catch (final Exception ignore) {}
        return true;
    }
}
