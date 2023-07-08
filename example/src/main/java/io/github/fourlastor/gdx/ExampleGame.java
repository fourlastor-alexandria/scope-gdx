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
    private final Group group1 = new Group(new ObjectScope("Settings", new Settings()));
    private final Group group2 = new Group(new ObjectScope("Other", new OtherSettings()));

    @Override
    public void create() {
        super.create();
        renderer = new ScopeRenderer(48);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY, true);
        renderer.start();
        renderer.render(group1);
        renderer.render(group2);
        renderer.end();
    }

    public static class Settings {
        @Editable
        public float floatVal = 12.3f;


        @Editable(name = "a name")
        public int intVal = 99;

        @Editable
        public InnerSettings innerSettings = new InnerSettings();
    }

    public static class InnerSettings {
        @Editable
        public int another;

        @Editable(name = "Anything goes")
        public boolean anythingGoes;
    }

    public static class OtherSettings {
        @Editable
        public int iterations;

        @Editable
        public float zoom;
    }
}
