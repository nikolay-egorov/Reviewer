package nikolayEgorov.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import nikolayEgorov.listenInterfaces.Interaction;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.processing.StatusBarInfo;

public class CopyReasonsAction extends AnAction implements Interaction {

    private ReasonListener reasonListener;


    @Override
    public void actionPerformed(AnActionEvent e) {
        final String fqn = reasonListener.getGeneratedReasons();
        CopyPasteManager.getInstance().setContents(new ReasonTransferable(fqn));
        StatusBarInfo.setStatusBarInfo(e.getProject(), "All reasons copied to the clipboard.");

    }

    @Override
    public void setReasonListener(ReasonListener aReasonListener) {
        reasonListener=aReasonListener;
    }

    @Override
    public void update(final AnActionEvent e){
        final String rejectReasons = reasonListener.getGeneratedReasons();
        e.getPresentation().setEnabled(rejectReasons != null && !rejectReasons.isEmpty());
    }
}
