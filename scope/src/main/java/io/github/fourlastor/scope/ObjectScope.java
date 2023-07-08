package io.github.fourlastor.scope;

import com.badlogic.gdx.utils.Null;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import io.github.fourlastor.scope.adapter.BooleanAdapter;
import io.github.fourlastor.scope.adapter.FloatAdapter;
import io.github.fourlastor.scope.adapter.IntAdapter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectScope extends Scope {

    public final List<Scope> fields;

    public ObjectScope(String name, Object instance) {
        this(name, instance, null);
    }

    public ObjectScope(String name, Object instance, @Null Map<Class<?>, Adapter> extraAdapters) {
        super(name);
        Map<Class<?>, Adapter> adapters = createDefaultAdapters();
        if (extraAdapters != null) {
            adapters.putAll(extraAdapters);
        }
        Class<?> clazz = instance.getClass();
        fields = new ArrayList<>();
        for (Field field : clazz.getFields()) {
            if (field.isAnnotationPresent(Editable.class)) {
                Editable editable = field.getAnnotation(Editable.class);
                Class<?> type = field.getType();
                String fieldName = editable.name().isEmpty() ? field.getName() : editable.name();
                Adapter adapter = adapters.get(type);
                if (adapter != null) {
                    fields.add(adapter.create(fieldName, instance, field));
                } else if (!type.isPrimitive()) {
                    try {
                        fields.add(new ObjectScope(fieldName, field.get(instance)));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private static Map<Class<?>, Adapter> createDefaultAdapters() {
        Map<Class<?>, Adapter> adapters = new HashMap<>();
        adapters.put(int.class, new IntAdapter());
        adapters.put(float.class, new FloatAdapter());
        adapters.put(boolean.class, new BooleanAdapter());
        return adapters;
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
