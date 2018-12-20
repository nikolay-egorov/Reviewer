package nikolayEgorov.processing;

import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.ide.actions.QualifiedNameProvider;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.LogicalRoot;
import com.intellij.util.LogicalRootsManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.*;

public class FqnMiner {

    public FqnMinerResult mineFqnAtCaret(final AnActionEvent aEvent){
        final DataContext dataContext = aEvent.getDataContext();

        final Project project = PlatformDataKeys.PROJECT.getData(dataContext);
        final Editor editor = PlatformDataKeys.EDITOR.getData(dataContext);

        final PsiElement elementAt =getElement(editor,dataContext);
        final FqnMinerResult fqnMinerResult = getElementFqn(editor, elementAt);
         if (editor != null && project != null) {
            final Document document = editor.getDocument();
            final PsiFile file = PsiDocumentManager.getInstance(project).getCachedPsiFile(document);
            if (file != null) {
                fqnMinerResult.setElementFqn(getFileFqn(file));
                fqnMinerResult.setElementLine(getLineNumber(editor));
                fqnMinerResult.setElementColumn(getColumn(editor));
            }
        }
        return  fqnMinerResult;
    }

    /**
     * Base method to seek through basic PSI-tree and search for using element
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

    //TODO: implement fileSearch for PSI
    private FqnMinerResult getElementFqn(final Editor aEditor, final PsiElement anElement) {
        FqnMinerResult result=getQualifiedNameFromPsi(anElement);
        if (result != null) {
            return result;
        }
        if (anElement instanceof PsiFile) {
            final PsiFile file = (PsiFile)anElement;
            result = new FqnMinerResult(FileUtil.toSystemIndependentName(getFileFqn(file)));
        }
        return result == null ? new FqnMinerResult() : result;

    }



    @NotNull
    private String getColumn(final Editor aEditor) {
        return "" + (aEditor.getCaretModel().getOffset());
    }

    @NotNull
    private String getLineNumber(final Editor aEditor) {
        return "" + (aEditor.getCaretModel().getLogicalPosition().line + 1);
    }


    @Nullable
    private PsiElement getMemberOf(final PsiElement element) {
        if (element instanceof PsiMember || element instanceof PsiReference) {
            return element;
        }
        if (!(element instanceof PsiIdentifier)) {
            return null;
        }
        final PsiElement parent = element.getParent();
        PsiMember member = null;
        if (parent instanceof PsiJavaCodeReferenceElement) {
            final PsiElement resolved = ((PsiJavaCodeReferenceElement)parent).resolve();
            if (resolved instanceof PsiMember) {
                member = (PsiMember)resolved;
            }
        } else if (parent instanceof PsiMember) {
            member = (PsiMember)parent;
        }
        return member;
    }

    @Nullable
    private FqnMinerResult getQualifiedNameFromPsi(@Nullable PsiElement element) {
        if (element instanceof PsiPackage) {
            return new FqnMinerResult(((PsiPackage) element).getQualifiedName());
        }
        element = getMemberOf(element);
        if (element instanceof PsiClass) {
            return new FqnMinerResult(((PsiClass) element).getQualifiedName());

        } else if (element instanceof PsiMember) {
            final PsiMember member = (PsiMember) element;
            PsiClass containingClass = member.getContainingClass();
            if (containingClass instanceof PsiAnonymousClass) {
                containingClass = ((PsiAnonymousClass) containingClass).getBaseClassType().resolve();
            }
            if (containingClass == null) {
                return null;
            }
            final String classFqn = containingClass.getQualifiedName();
            if (classFqn == null) {
                return new FqnMinerResult(member.getName());  // refer to member of anonymous class by simple name
            }
            return new FqnMinerResult(classFqn, member.getName());
        }
        return null;
    }

    public String getFileFqn(final PsiFile file) {
        final VirtualFile virtualFile = file.getVirtualFile();
        if (virtualFile == null) {
            return file.getName();
        }
        final Project project = file.getProject();
//        final LogicalRoot logicalRoot = LogicalRootsManager.getLogicalRootsManager(project).findLogicalRoot(virtualFile);

        final LogicalRoot logicalRoot;
        final PsiFileSystemItem fileSystemItem= FileReferenceHelper.getPsiFileSystemItem(PsiManager.getInstance(project),virtualFile);
         if (fileSystemItem != null) {
          //  final String logical = FileUtil.toSystemIndependentName(VfsUtil.virtualToIoFile(logicalRoot.getVirtualFile()).getPath());
            return FileUtil.toSystemIndependentName(VfsUtil.virtualToIoFile(virtualFile).getPath());
        }

        final VirtualFile contentRoot = ProjectRootManager.getInstance(project).getFileIndex().getContentRootForFile(virtualFile);
        if (contentRoot != null) {
            return FileUtil.toSystemIndependentName(VfsUtil.virtualToIoFile(virtualFile).getAbsolutePath());
        }
        return FileUtil.toSystemIndependentName(virtualFile.getPath());
    }





}
