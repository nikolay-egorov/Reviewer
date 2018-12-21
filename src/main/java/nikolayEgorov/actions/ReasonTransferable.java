package nikolayEgorov.actions;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Class needed to make Reasons being able to transfer, Hence, they would be available from clipboard in @CopyReasons
 */

public class ReasonTransferable implements Transferable {

    private final String fqn;

    public ReasonTransferable(String fqn) {
        this.fqn = fqn;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.stringFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return false;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            return fqn;
        }
        return null;
    }
}
