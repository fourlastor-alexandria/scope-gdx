package io.github.fourlastor.scope;

import imgui.type.ImInt;

public class IntScope extends Scope {

    public ImInt value;

    public IntScope(String name, int value) {
        super(name);
        this.value = new ImInt(value);
    }

    @Override
    public void display(Visitor visitor) {
        visitor.visitDisplay(this);
    }

    @Override
    public void apply(Visitor visitor) {}
}
