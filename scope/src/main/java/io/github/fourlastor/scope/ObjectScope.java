package io.github.fourlastor.scope;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectScope extends Scope {

    public final List<Scope> fields;

    public ObjectScope(String name, Object instance) {
        this(name, instance, Scope.Adapter.createDefaultAdapters());
    }

    public ObjectScope(String name, Object instance, Map<Class<?>, Adapter> adapters) {
        super(name);
        Class<?> clazz = instance.getClass();
        fields = new ArrayList<>();
        for (Field field : clazz.getFields()) {
            if (field.isAnnotationPresent(Editable.class)) {
                Editable editable = field.getAnnotation(Editable.class);
                Class<?> type = field.getType();
                String fieldName = editable.name().isEmpty() ? field.getName() : editable.name();
                Adapter adapter = adapters.get(type);
                if (adapter != null) {
                    fields.add(adapter.create(fieldName, instance, field, adapters));
                } else if (!type.isPrimitive()) {
                    try {
                        fields.add(new ObjectScope(fieldName, field.get(instance), adapters));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void display() {
        if (ImGui.treeNodeEx(name, ImGuiTreeNodeFlags.DefaultOpen)) {
            for (Scope field : fields) {
                field.display();
            }
            ImGui.treePop();
        }
    }

    @Override
    public void apply() {
        for (Scope field : fields) {
            field.apply();
        }
    }
}
