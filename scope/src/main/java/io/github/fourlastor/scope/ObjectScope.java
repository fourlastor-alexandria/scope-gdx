package io.github.fourlastor.scope;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectScope extends Scope {

    public final List<Scope> fields;

    public ObjectScope(String name, Object instance) {
        super(name);
        Class<?> clazz = instance.getClass();
        fields = new ArrayList<>();
        for (Field field : clazz.getFields()) {
            if (field.isAnnotationPresent(Editable.class)) {
                Editable editable = field.getAnnotation(Editable.class);
                Class<?> type = field.getType();
                String fieldName = editable.name().isEmpty() ? field.getName() : editable.name();
                if (type == float.class) {
                    fields.add(new FloatFieldScope(fieldName, instance, field));
                } else if (type == int.class) {
                    fields.add(new IntFieldScope(fieldName, instance, field));
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

    @Override
    public void display(Visitor visitor) {
        visitor.visitDisplay(this);
    }

    @Override
    public void apply(Visitor visitor) {
        visitor.visitApply(this);
    }
}
