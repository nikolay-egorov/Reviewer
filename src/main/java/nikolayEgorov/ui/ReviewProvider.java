package nikolayEgorov.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import org.jetbrains.annotations.Nullable;

@State(
        name = "ReviewProvider"
)
public class ReviewProvider implements PersistentStateComponent<ReviewProvider>, ApplicationComponent {

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

    }
}
