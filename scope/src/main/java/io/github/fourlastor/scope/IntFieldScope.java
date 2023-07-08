package io.github.fourlastor.scope;

import java.lang.reflect.Field;

public class IntFieldScope extends FieldScope {

    private final IntScope control;

    protected IntFieldScope(String name, Object instance, Field field) {
        super(name, instance, field);
        try {
            control = new IntScope(name, field.getInt(instance));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void display(Visitor visitor) {
        control.display(visitor);
    }

    @Override
    public void apply(Visitor visitor) {
        control.apply(visitor);
        try {
            field.setInt(instance, control.value.intValue());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
