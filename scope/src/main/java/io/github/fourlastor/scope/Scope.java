package io.github.fourlastor.scope;

import java.lang.reflect.Field;

public abstract class Scope {

    public final String name;

    protected Scope(String name) {
        this.name = name;
    }

    public abstract void display();

    public abstract void apply();

    public interface Adapter {
        Scope create(String name, Object instance, Field field);
    }
}
