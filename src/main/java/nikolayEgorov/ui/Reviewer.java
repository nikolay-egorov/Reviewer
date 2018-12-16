package nikolayEgorov.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Reviewer implements Configurable {
    private JPanel generalPanel;
    private JTextArea formatArea;


    public Reviewer(){

    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Review Helper";

    }

    @Nullable
    @Override
    public JComponent createComponent() {
         return generalPanel;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    private void createUIComponents() {
        generalPanel = new JPanel();
        formatArea = new JTextArea();
    }

    @Override
    public void reset() {

    }
}
