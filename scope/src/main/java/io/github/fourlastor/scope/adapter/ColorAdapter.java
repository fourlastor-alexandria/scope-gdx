package io.github.fourlastor.scope.adapter;

import com.badlogic.gdx.graphics.Color;
import imgui.ImGui;
import imgui.type.ImBoolean;
import io.github.fourlastor.scope.FieldScope;
import io.github.fourlastor.scope.Scope;

import java.lang.reflect.Field;
import java.util.Map;

public class ColorAdapter implements Scope.Adapter {
    @Override
    public Scope create(String name, Object instance, Field field, Map<Class<?>, Scope.Adapter> adapters) {
        return new ColorFieldScope(name, instance, field);
    }

    private static class ColorFieldScope extends Scope {

        private final float[] value;
        private final Color color;

        ColorFieldScope(String name, Object instance, Field field) {
            super(name);
            try {
                color = ((Color) field.get(instance));
                this.value = new float[] {color.r, color.g, color.b, color.a};
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void display() {
            ImGui.colorEdit3(name, value);
        }

        @Override
        public void apply() {
            color.set(value[0], value[1], value[2], value[3]);
        }
    }
}
