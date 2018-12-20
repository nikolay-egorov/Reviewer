package nikolayEgorov.processing;

import nikolayEgorov.ui.ReviewProvider;

public class ReasonFormatter {
    public ReasonFormatter(){
    }

    public String getFormattedReason(final FqnMinerResult result,final String comment){
        return String.format(ReviewProvider.getInstance().OUTPUT_REG_FORM, result.getElementFqn(), result.getElementName(),
                result.getElementLine(),comment);
    }

}
