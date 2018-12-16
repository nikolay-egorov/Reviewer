package nikolayEgorov;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.wm.ToolWindowFactory;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.processing.StatusBarInfo;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ToolWindow implements ToolWindowFactory,ReasonListener {

    private final ActionManager actionManager;
    private Project project;
    private ConsoleViewImpl console;

    public ToolWindow() {
        this.actionManager = ActionManager.getInstance();
        //TODO: REGISTER Listeners

    }


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull com.intellij.openapi.wm.ToolWindow toolWindow) {
        this.project = project;
    }

    @Override
    public void AddReason(String Reason) {
        if (console == null) {
            computeNullCons();
            return;
        }
        console.print(Reason + "\n", ConsoleViewContentType.NORMAL_OUTPUT);

    }

    private void computeNullCons() {
        StatusBarInfo.setStatusBarInfo(project, "The action cannot be completed dut to null console");
    }


    @Override
    public void resetReasons() {
        if(console==null){
            computeNullCons();
            return;
        }
        console.clear();
    }

    @Override
    public String getGeneratedReasons() {
        if(console==null){
            computeNullCons();
            return null;
        }

        final Object editor=console.getEditor();
        if(editor instanceof Editor){
            final String text =((Editor) editor).getDocument().getText();
            if(text!=null){
                return text.replaceAll(FileUtil.toSystemIndependentName(project.getBasePath()),"");
            }
        }
        return null;
    }


    private ActionToolbar initActionBar(final JPanel aJPanel) {
        return null;
    }


}
