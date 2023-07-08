package io.github.fourlastor.scope;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

class ImguiVisitor implements Scope.Visitor {

    @Override
    public void visitDisplay(FloatScope control) {
        ImGui.inputFloat(control.name, control.value);
    }

    @Override
    public void visitDisplay(IntScope control) {
        ImGui.inputInt(control.name, control.value);
    }

    @Override
    public void visitDisplay(ObjectScope control) {
        if (ImGui.treeNodeEx(control.name, ImGuiTreeNodeFlags.DefaultOpen)) {
            for (Scope field : control.fields) {
                field.display(this);
            }
            ImGui.treePop();
        }
    }

    @Override
    public void visitApply(ObjectScope control) {
        for (Scope field : control.fields) {
            field.apply(this);
        }
    }
}
