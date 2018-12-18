package nikolayEgorov.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import nikolayEgorov.listenInterfaces.Interaction;
import nikolayEgorov.listenInterfaces.ReasonListener;
import nikolayEgorov.processing.ReasonFormatter;

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

    @Override
    public void update(final AnActionEvent aActionEvent) {
        //TODO: implement update
    }
}
