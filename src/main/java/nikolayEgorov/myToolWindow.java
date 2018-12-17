package nikolayEgorov;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.processing.StatusBarInfo;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class myToolWindow implements ToolWindowFactory,ReasonListener {

    private final ActionManager actionManager;
    private Project project;
    private ConsoleViewImpl console;

    public myToolWindow() {
        this.actionManager = ActionManager.getInstance();
        //TODO: REGISTER Listeners

    }


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull com.intellij.openapi.wm.ToolWindow toolWindow) {
        this.project = project;
        initAll(toolWindow);
        //TODO add console!
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

    private void initAll(final ToolWindow toolWindow){
        final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        final Content content= contentFactory.createContent(initMainPanel(),"test",false);
        toolWindow.getContentManager().addContent(content);
    }


    private JPanel initMainPanel(){
        final JPanel jPanel = new JPanel(new BorderLayout());
        final ActionToolbar actionToolbar = actionManager.createActionToolbar(ActionPlaces.UNKNOWN, (ActionGroup)actionManager.getAction(
                "reviewer.toolbar_acts"), false);
        actionToolbar.setTargetComponent(jPanel);
        jPanel.add(actionToolbar.getComponent(), BorderLayout.WEST);
        jPanel.add(console.getComponent(), BorderLayout.CENTER);
        return jPanel;
    }


}
