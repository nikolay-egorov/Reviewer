package nikolayEgorov;

import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.RegexpFilter;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import nikolayEgorov.listenInterfaces.Interaction;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.processing.StatusBarInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class myToolWindow implements ToolWindowFactory,ReasonListener {

    private final ActionManager actionManager;
    private Project project;
    private ConsoleViewImpl console;

    public myToolWindow() {
        this.actionManager = ActionManager.getInstance();
        registerActionAsListener("reviewer.add_reason");
        registerActionAsListener("reviewer.clear_reasons");
    }

    private void registerActionAsListener(final String anAction){
        final AnAction action = actionManager.getAction(anAction);
        if(action instanceof Interaction)
            ((Interaction) action).setReasonListener(this);
    }

    private void initConsole(){
        console=new ConsoleViewImpl(project, GlobalSearchScope.allScope(project),true,false);
        console.addMessageFilter(new RegexpFilter(project, RegexpFilter.FILE_PATH_MACROS + ":" + RegexpFilter.LINE_MACROS) {
            @Nullable
            @Override
            protected HyperlinkInfo createOpenFileHyperlink(final String fileName, final int line, final int column) {
                return super.createOpenFileHyperlink(fileName, line, column);
            }
        });
    }



    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull com.intellij.openapi.wm.ToolWindow toolWindow) {
        this.project = project;
        initConsole();
        initAll(toolWindow);

    }

    @Override
    public void AddReason(String Reason) {
        if (console == null) {
            proceedNullCons();
            return;
        }
        console.print(Reason + "\n", ConsoleViewContentType.NORMAL_OUTPUT);

    }

    private void proceedNullCons() {
        StatusBarInfo.setStatusBarInfo(project, "The action cannot be completed due to null console");
    }


    @Override
    public void resetReasons() {
        if(console==null){
            proceedNullCons();
            return;
        }
        console.clear();
    }

    @Override
    public String getGeneratedReasons() {
        if(console==null){
            proceedNullCons();
            return null;
        }

        final Object editor=console.getEditor();
        if(editor !=null){
            final String text =((Editor) editor).getDocument().getText();
            if(text!=null){
                return text.replaceAll(FileUtil.toSystemIndependentName(project.getBasePath()),"");
            }
        }
        return null;
    }

    private void initAll(final ToolWindow toolWindow){
        final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        final Content content= contentFactory.createContent(initMainPanel(),"",false);
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
