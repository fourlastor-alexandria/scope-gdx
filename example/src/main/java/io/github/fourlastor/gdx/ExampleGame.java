package io.github.fourlastor.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.fourlastor.scope.Editable;
import io.github.fourlastor.scope.Group;
import io.github.fourlastor.scope.ObjectScope;
import io.github.fourlastor.scope.ScopeRenderer;

public class ExampleGame extends ApplicationAdapter {

    private ScopeRenderer renderer;

    @Override
    public void create() {
        super.create();
        renderer = new ScopeRenderer(new Group(new ObjectScope("Settings", new Settings())), 48);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY, true);
        renderer.render();
    }

    public static class Settings {
        @Editable
        public float floatVal = 12.3f;


        @Editable(name = "a name")
        public int intVal = 99;

        @Editable
        public InnerSettings innerSettings = new InnerSettings();

        @Override
        public String toString() {
            return "Settings{" +
                    "floatVal=" + floatVal +
                    ", intVal=" + intVal +
                    '}';
        }
    }

    public static class InnerSettings {
        @Editable
        public int another;

        @Editable(name = "Anything goes")
        public float anythingGoes;
    }
}
