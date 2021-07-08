package net.kaaass.kmall.constraints;

import lombok.val;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UuidValidator implements ConstraintValidator<Uuid, String> {

    private Uuid uuid;

    @Override
    public void initialize(Uuid uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (s == null) {
            return uuid.nullable();
        }

        if (s.length() != 32) {
            return false;
        }

        for (val ch : s.toCharArray()) {
            val digit = '0' <= ch && ch <= '9';
            val alphabet = 'a' <= ch && ch <= 'z';
            if (!(digit || alphabet)) {
                return false;
            }
        }

        return true;
    }
}
