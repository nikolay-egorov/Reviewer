package nikolayEgorov.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Main extention-point all-in-one impl and interface;
 * implements PersistentStateComponent for storing data through @Storage
 */

@State(
        name = "ReviewProvider",
        storages = {@Storage(id = "other", file = StoragePathMacros.APP_CONFIG + "/review.xml")}
)
public class ReviewProvider implements PersistentStateComponent<ReviewProvider>, ApplicationComponent {


    public String OUTPUT_REG_FORM = "***%1$s:%3$s[%2$s]***\n%4$s";

    public static ReviewProvider getInstance() {
        return ApplicationManager.getApplication().getComponent((ReviewProvider.class));
    }

    @Nullable
    @Override
    public ReviewProvider getState() {
        return this;
    }

    @Override
    public void loadState(ReviewProvider state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "ReviewProvider";
    }

    @Override
    public void initComponent() {
    }


    @Override
    public void disposeComponent() {
    }
}
