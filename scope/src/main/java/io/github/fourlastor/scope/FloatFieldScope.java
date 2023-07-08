package io.github.fourlastor.scope;

import java.lang.reflect.Field;

public class FloatFieldScope extends FieldScope {

    private final FloatScope control;

    protected FloatFieldScope(String name, Object instance, Field field) {
        super(name, instance, field);
        try {
            control = new FloatScope(name, field.getFloat(instance));
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
            field.setFloat(instance, control.value.floatValue());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
