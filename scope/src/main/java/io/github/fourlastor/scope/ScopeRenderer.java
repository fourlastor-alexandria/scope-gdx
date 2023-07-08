package io.github.fourlastor.scope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.utils.Disposable;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCond;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class ScopeRenderer implements Disposable {

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private final ImguiVisitor visitor = new ImguiVisitor();


    private boolean manualStart = false;

    public ScopeRenderer(int sizePixels) {
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        ImFontConfig imFontConfig = new ImFontConfig();
        imFontConfig.setSizePixels(sizePixels);
        io.getFonts().addFontDefault(imFontConfig);
        io.getFonts().build();

        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init("#version 110");
    }

    public void render(Group group) {
        if (!manualStart) {
            startInternal();
        }
        group.display(visitor);
        if (!manualStart) {
            endInternal();
        }
    }

    public void start() {
        manualStart = true;
        startInternal();
    }

    public void end() {
        endInternal();
        manualStart = false;
    }

    private void startInternal() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
        float width = ImGui.getMainViewport().getSizeX();
        float height = ImGui.getMainViewport().getSizeY();
        ImGui.setNextWindowSize(width * 0.3f, height * 0.8f, ImGuiCond.Once);
        ImGui.setNextWindowPos(width * 0.6f, height * 0.1f, ImGuiCond.Once);
    }

    private void endInternal() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    @Override
    public void dispose() {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
    }
}
