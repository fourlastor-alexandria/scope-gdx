package io.github.fourlastor.scope;

import imgui.ImGui;

public class Group {

    public final Scope scope;

    public Group(Scope scope) {
        this.scope = scope;
    }

    public void display(Scope.Visitor visitor) {
        if (ImGui.begin(scope.name)) {
            boolean shouldReload = ImGui.button("Apply");
            scope.display(visitor);
            if (shouldReload) {
                scope.apply(visitor);
            }
        }
        ImGui.end();
    }
}
