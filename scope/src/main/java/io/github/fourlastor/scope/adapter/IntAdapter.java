package io.github.fourlastor.scope.adapter;

import imgui.ImGui;
import imgui.type.ImInt;
import io.github.fourlastor.scope.FieldScope;
import io.github.fourlastor.scope.Scope;
import java.lang.reflect.Field;

public class IntAdapter implements Scope.Adapter {
    @Override
    public Scope create(String name, Object instance, Field field) {
        return new IntFieldScope(name, instance, field);
    }

    private static class IntFieldScope extends FieldScope {

        public ImInt value;

        protected IntFieldScope(String name, Object instance, Field field) {
            super(name, instance, field);
            try {
                this.value = new ImInt(field.getInt(instance));

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void display() {
            ImGui.inputInt(name, value);
        }

        @Override
        public void apply() {
            try {
                field.setInt(instance, value.intValue());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
