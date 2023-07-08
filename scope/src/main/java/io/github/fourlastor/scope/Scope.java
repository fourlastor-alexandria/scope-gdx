package io.github.fourlastor.scope;

import com.badlogic.gdx.graphics.Color;
import io.github.fourlastor.scope.adapter.BooleanAdapter;
import io.github.fourlastor.scope.adapter.ColorAdapter;
import io.github.fourlastor.scope.adapter.FloatAdapter;
import io.github.fourlastor.scope.adapter.IntAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Scope {

    public final String name;

    protected Scope(String name) {
        this.name = name;
    }

    public abstract void display();

    public abstract void apply();

    public interface Adapter {
        Scope create(String name, Object instance, Field field, Map<Class<?>, Adapter> adapters);

        static Map<Class<?>, Adapter> createDefaultAdapters() {
            HashMap<Class<?>, Adapter> adapters = new HashMap<>();
            adapters.put(int.class, new IntAdapter());
            adapters.put(float.class, new FloatAdapter());
            adapters.put(boolean.class, new BooleanAdapter());
            adapters.put(Color.class, new ColorAdapter());
            return adapters;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Lens {
        String name() default "";
    }
}
