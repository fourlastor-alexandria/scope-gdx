package io.github.fourlastor.scope;

public abstract class Scope {

    public final String name;

    protected Scope(String name) {
        this.name = name;
    }

    public abstract void display(Visitor visitor);

    public abstract void apply(Visitor visitor);

    public interface Visitor {
        void visitDisplay(FloatScope control);

        void visitDisplay(IntScope intControl);

        void visitDisplay(ObjectScope control);

        void visitApply(ObjectScope control);
    }
}
