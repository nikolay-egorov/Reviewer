package nikolayEgorov.processing;

import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.ide.actions.QualifiedNameProvider;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FqnMiner {

    public void mineFqnAtCaret(final AnActionEvent aEvent){
        final DataContext dataContext = aEvent.getDataContext();

        final Project project = PlatformDataKeys.PROJECT.getData(dataContext);
        final Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);
        //TODO: PSI ??
        final PsiElement elementAt =getElement(editor,dataContext);

    }

    /**
     * Main method to seek through basic PSI-tree and search for using element
     * @param editor an instance of an editor is using under
     * @param context context from which data is shown
     * @return PsiElement
     */

    @Nullable
    public PsiElement getElement(final Editor editor, final  DataContext context){
        PsiElement element = null;
        if (editor != null) {
            final PsiReference reference = TargetElementUtil.findReference(editor);
            element=reference.getElement();
        }

        if(element == null){
            element = LangDataKeys.PSI_ELEMENT.getData(context);
        }
        if (element == null && editor == null) {
            final VirtualFile virtualFile = PlatformDataKeys.VIRTUAL_FILE.getData(context);
            final Project project = PlatformDataKeys.PROJECT.getData(context);
            if (virtualFile != null && project != null) {
                element = PsiManager.getInstance(project).findFile(virtualFile);
            }
        }
        if (element instanceof PsiFile && !((PsiFile)element).getViewProvider().isPhysical()) {
            return null;
        }


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
