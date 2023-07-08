package io.github.fourlastor.scope.adapter;

import imgui.ImGui;
import imgui.type.ImFloat;
import io.github.fourlastor.scope.FieldScope;
import io.github.fourlastor.scope.Scope;
import java.lang.reflect.Field;

public class FloatAdapter implements Scope.Adapter {
    @Override
    public Scope create(String name, Object instance, Field field) {
        return new FloatFieldScope(name, instance, field);
    }

    private static class FloatFieldScope extends FieldScope {

        private final ImFloat value;

        FloatFieldScope(String name, Object instance, Field field) {
            super(name, instance, field);
            try {
                this.value = new ImFloat(field.getFloat(instance));

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void display() {
            ImGui.inputFloat(name, value);
        }

        @Override
        public void apply() {
            try {
                field.setFloat(instance, value.floatValue());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
