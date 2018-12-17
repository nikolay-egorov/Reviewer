package nikolayEgorov.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Reviewer implements Configurable {
    private JPanel generalPanel;
    private JTextArea formatArea;
    private final ReviewProvider reviewProvider;


    public Reviewer(){
        reviewProvider=ReviewProvider.getInstance();
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
        return  reviewProvider.OUTPUT_REG_FORM.equals(formatArea.getText()) ? false : true;
    }

    @Override
    public void apply() throws ConfigurationException {
        reviewProvider.OUTPUT_REG_FORM=formatArea.getText();
    }

    private void createUIComponents() {
        generalPanel = new JPanel();
        formatArea = new JTextArea();
    }

    @Override
    public void reset() {
        formatArea.setText(reviewProvider.OUTPUT_REG_FORM);
    }
}
