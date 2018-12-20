package nikolayEgorov;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class myDialogMessage extends DialogWrapper{
    private final JTextArea textField;
    private final JPanel panel;


    public myDialogMessage() {
        super(null);
        setTitle("Provide a Comment, if Any");

        textField = new JTextArea();
        panel=new JPanel();
        textField.setPreferredSize(new Dimension(200, 100));
        textField.setLineWrap(true);
        panel.add(textField, BorderLayout.CENTER);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return textField;
    }


    public String getComment(){
        return textField.getText();
    }

}
