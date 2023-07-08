package io.github.fourlastor.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.fourlastor.scope.Group;
import io.github.fourlastor.scope.ObjectScope;
import io.github.fourlastor.scope.Scope;
import io.github.fourlastor.scope.ScopeRenderer;

public class ExampleGame extends ApplicationAdapter {

    private ScopeRenderer renderer;
    private final Settings settings = new Settings();
    private final Group group1 = new Group(new ObjectScope("Settings", settings));
    private final Group group2 = new Group(new ObjectScope("Other", new OtherSettings()));

    @Override
    public void create() {
        super.create();
        renderer = new ScopeRenderer(48);
    }

    @Override
    public void render() {
        ScreenUtils.clear(settings.color, true);
        renderer.start();
        renderer.render(group1);
        renderer.render(group2);
        renderer.end();
    }

    public static class Settings {
        public float floatVal = 12.3f;


        @Scope.Lens(name = "a name")
        public int intVal = 99;

        public InnerSettings innerSettings = new InnerSettings();

        public Color color = new Color(Color.DARK_GRAY);
    }

    public static class InnerSettings {
        public int another;

        @Scope.Lens(name = "Anything goes")
        public boolean anythingGoes;
    }

    public static class OtherSettings {
        public int iterations;

        public float zoom;
    }
}
