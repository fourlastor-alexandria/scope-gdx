package io.github.fourlastor.scope.adapter;

import imgui.ImGui;
import imgui.type.ImBoolean;
import io.github.fourlastor.scope.FieldScope;
import io.github.fourlastor.scope.Scope;
import java.lang.reflect.Field;

public class BooleanAdapter implements Scope.Adapter {
    @Override
    public Scope create(String name, Object instance, Field field) {
        return new BooleanFieldScope(name, instance, field);
    }

    private static class BooleanFieldScope extends FieldScope {

        public ImBoolean value;

        protected BooleanFieldScope(String name, Object instance, Field field) {
            super(name, instance, field);
            try {
                this.value = new ImBoolean(field.getBoolean(instance));

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void display() {
            ImGui.checkbox(name, value);
        }

        @Override
        public void apply() {
            try {
                field.setBoolean(instance, value.get());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
