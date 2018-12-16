package nikolayEgorov;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

public class ToolWindow implements ToolWindowFactory {

    private final ActionManager actionManager;
    private Project project;

    public ToolWindow() {
        this.actionManager = ActionManager.getInstance();
        //TO REGISTER Listeners

    }


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull com.intellij.openapi.wm.ToolWindow toolWindow) {
        this.project = project;
    }
}
