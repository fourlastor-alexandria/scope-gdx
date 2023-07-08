package io.github.fourlastor.scope;

import imgui.ImGui;

public class Group {

    public final Scope scope;

    public Group(Scope scope) {
        this.scope = scope;
    }

    public void display() {
        if (ImGui.begin(scope.name)) {
            boolean shouldReload = ImGui.button("Apply");
            scope.display();
            if (shouldReload) {
                scope.apply();
            }
        }
        ImGui.end();
    }
}
