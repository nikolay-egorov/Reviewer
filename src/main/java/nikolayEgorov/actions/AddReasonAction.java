package nikolayEgorov.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import nikolayEgorov.listenInterfaces.Interaction;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.processing.ReasonFormatter;

/**
 * AnAction class to AddReason
 * synched with Interaction
 */

public class AddReasonAction extends AnAction implements Interaction{

    private final ReasonFormatter reasonFormatter;
    private ReasonListener reasonListener;

    public AddReasonAction() {
        reasonFormatter=new ReasonFormatter();
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
    }

    @Override
    public void setReasonListener(ReasonListener aReasonListener) {
        reasonListener=aReasonListener;
    }


    /**
     * sets UI element enabled to show
     * @param anEvent
     */
    @Override
    public void update(final AnActionEvent anEvent) {
        anEvent.getPresentation().setEnabled(true);
    }
}
