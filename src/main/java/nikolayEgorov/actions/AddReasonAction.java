package nikolayEgorov.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import nikolayEgorov.listenInterfaces.Interaction;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.myDialogMessage;
import nikolayEgorov.processing.FqnMiner;
import nikolayEgorov.processing.FqnMinerResult;
import nikolayEgorov.processing.ReasonFormatter;

/**
 * AnAction class to AddReason
 * synched with Interaction
 */

public class AddReasonAction extends AnAction implements Interaction{

    private final ReasonFormatter reasonFormatter;
    private ReasonListener reasonListener;
    private final FqnMiner fqnMiner;

    public AddReasonAction() {
        reasonFormatter=new ReasonFormatter();
        fqnMiner = new FqnMiner();
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final FqnMinerResult result=fqnMiner.mineFqnAtCaret(e);
        final myDialogMessage dlg = new myDialogMessage();
        dlg.show();
        if (dlg.isOK()) {
             reasonListener.AddReason(reasonFormatter.getFormattedReason(result, dlg.getComment()));
        }
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
