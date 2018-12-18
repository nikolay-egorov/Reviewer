package nikolayEgorov.processing;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class FqnMiner {

    public void mineFqnAtCaret(final AnActionEvent aEvent){
        final DataContext dataContext = aEvent.getDataContext();

        final Project project = PlatformDataKeys.PROJECT.getData(dataContext);
        final Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        //TODO: PSI ??
        final PsiElement elementAt ; //getElement() from editor and context??
    }


    public PsiElement getElement(final Editor editor, final  DataContext context){
        PsiElement element = null;
        return element;
    }

    @NotNull
    private String getColumn(final Editor aEditor) {
        return "" + (aEditor.getCaretModel().getOffset());
    }

    @NotNull
    private String getLineNumber(final Editor aEditor) {
        return "" + (aEditor.getCaretModel().getLogicalPosition().line + 1);
    }


}
