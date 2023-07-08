package io.github.fourlastor.scope;

import imgui.type.ImFloat;

public class FloatScope extends Scope {

    public ImFloat value;

    public FloatScope(String name, float value) {
        super(name);
        this.value = new ImFloat(value);
    }

    @Override
    public void display(Visitor visitor) {
        visitor.visitDisplay(this);
    }

    @Override
    public void apply(Visitor visitor) {}
}
