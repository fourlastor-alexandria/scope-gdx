# Scope

An easy-to-use debug display.

## Setup

### Gradle

Scope is hosted at Maven Central, you can get it by adding the following dependencies:

```kts
implementation("io.github.fourlastor.gdx:scope:$version")
```

## Usage

Create your settings class:

```java
public static class Settings {
    @Editable
    public float floatVal = 12.3f;

    @Editable(name = "a name")
    public int intVal = 99;
}
```

Fields must be public, non-final and annotated with `@Editable`.

![image](https://github.com/fourlastor-alexandria/scope-gdx/assets/1263058/9d90766e-5227-4ab5-859d-764e71fa02c8)

Clicking "apply" will update the field values of the object.

### Custom adapters

Custom adapters can be created to treat classes in a special way:

```java
// ColorAdapter is part of the default adapters already, let's pretend it wasn't
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

public class Main {
    public static void main(String[] args) {
        // Create the default adapters
        Map<Class<?>, Adapter> adapters = Scope.Adapter.createDefaultAdapters();
        // Add a custom adapter to them
        adapters.put(Color.class, new ColorAdapter());
        // Use the custom map of adapters when creating ObjectScope
        ObjectScope scope = new ObjectScope("My scope with custom adapters", settings, adapters);
    }
}
```
