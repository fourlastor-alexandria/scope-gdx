package io.github.fourlastor.scope;

import imgui.ImGui;

public class Group {

    private final Scope scope;
    private final Runnable onApply;

    public Group(Scope scope) {
        this(scope, null);
    }

    public Group(Scope scope, Runnable onApply) {
        this.scope = scope;
        this.onApply = onApply;
    }

    public void display() {
        if (ImGui.begin(scope.name)) {
            boolean shouldReload = ImGui.button("Apply");
            scope.display();
            if (shouldReload) {
                scope.apply();
                if (onApply != null) {
                    onApply.run();
                }
            }
        }
        ImGui.end();
    }
}
