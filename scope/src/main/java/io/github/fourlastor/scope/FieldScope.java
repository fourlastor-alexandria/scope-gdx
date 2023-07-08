package io.github.fourlastor.scope;

import java.lang.reflect.Field;

public abstract class FieldScope extends Scope {
    protected final Object instance;
    protected final Field field;

    public FieldScope(String name, Object instance, Field field) {
        super(name);
        this.instance = instance;
        this.field = field;
    }
}
