package nikolayEgorov.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import nikolayEgorov.listenInterfaces.Interaction;
import nikolayEgorov.listenInterfaces.ReasonListener;

public class CopyReasonsAction extends AnAction implements Interaction {

    private ReasonListener reasonListener;


    @Override
    public void actionPerformed(AnActionEvent e) {

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
