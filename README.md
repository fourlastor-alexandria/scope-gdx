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

clicking "apply" will update the field values of the object.

