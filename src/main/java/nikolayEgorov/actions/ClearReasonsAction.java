package nikolayEgorov.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import nikolayEgorov.listenInterfaces.Interaction;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.processing.ReasonFormatter;

/**
 * AnAction class to ClearAll Reasons
 * synched with Interaction
 */

public class ClearReasonsAction extends AnAction implements Interaction {

     private ReasonListener reasonListener;


    @Override
    public void actionPerformed(AnActionEvent e) {
        reasonListener.resetReasons();
    }

    @Override
    public void setReasonListener(final ReasonListener aReasonListener) {
        reasonListener = aReasonListener;
    }


    /**
    update method; shows element on layout only when reasons are exist
     */
    @Override
    public void update(final AnActionEvent aActionEvent) {
        final String reasons = reasonListener.getGeneratedReasons();
        aActionEvent.getPresentation().setEnabled(reasons != null && !reasons.isEmpty());

    }
}
