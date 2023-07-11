# Scope

[![Scope on Maven Central](https://img.shields.io/maven-central/v/io.github.fourlastor.gdx/scope?label=scope)](https://search.maven.org/artifact/io.github.fourlastor.gdx/scope)

An easy-to-use debug display.

## Setup

### Gradle

Scope is hosted at Maven Central, you can get it by adding the following dependencies:

```kts
implementation("io.github.fourlastor.gdx:scope:$version")
```

## Usage

Create your settings class:

Java
```java
public static class Settings {
    public float characterSpeed = 12.3f;

    // Optional - set a custom name for this field
    @Scope.Lens(name = "Jump height")
    public int tilesJumpHeight = 99;
}
```
Kotlin
```kotlin
class Settings {
    @JvmField // Mandatory in Kotlin
    var characterSpeed: Float = 12.3f;
    @JvmField
    // Optional - set a custom name for this field
    @field:Scope.Lens(name = "Jump height")
    var tilesJumpHeight: Int = 99
}
```


Fields must be public and non-final.

Create a `Group` to render your settings, and a `ScopeRenderer` (you can use multiple `Group`s with the same renderer):

```java
class MyGame {
    Settings settings;
    Group group;
    ScopeRenderer renderer;

    public void create() {
        settings = new Settings();
        group = new Group(new ObjectScope("Settings", settings));
        renderer = new ScopeRenderer(48);
    }
    
    public void render() {
        // start/stop are optional when rendering a single group
        renderer.start();
        renderer.render(group);
        renderer.end();
    }
}
```

:warning: There must be only one instance of `ScopeRender` in your game, multiple instances will mess with input detection.

[scope.webm](https://github.com/fourlastor-alexandria/scope-gdx/assets/1263058/556eb628-5038-41b5-8de6-f9589392a4f5)

> _Recording curtesy of [Sandra Moen](https://sandramoen.no/) as seen in [Trans Agent X](https://store.steampowered.com/app/2314960/Trans_Agent_X/)_

Clicking "Apply" will update the field values of the object.

### Apply callback

You can pass a `Runnable` to `Group` to be notified when changed are applied:

```java
new Group("Settings", settings, () -> System.out.println("settings changed!"));
```

### Custom adapters

Custom adapters can be created to treat classes in a special way:

```java
// ColorAdapter is part of the default adapters already, let's create one that shows also the alpha of the color
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
            // colorEdit4 has the alpha
            ImGui.colorEdit4(name, value, ImGuiColorEditFlags.AlphaBar);
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
        ObjectScope scope = new ObjectScope("Scope with custom adapters", settings, adapters);
    }
}
```
