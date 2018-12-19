package nikolayEgorov.processing;

public class FqnMinerResult {

    private String elementFqn;
    private String elementName;
    private String elementLine;
    private String elementColoumn;

    public FqnMinerResult(final String elementFqn) {
        this.elementFqn = elementFqn;
    }

    public FqnMinerResult(final String elementFqn,final String elementName) {
        this.elementFqn = elementFqn;
        this.elementName = elementName;
    }

    public FqnMinerResult() {
    }

    public String getElementFqn() {
        return elementFqn!=null ? elementFqn : "";
    }

    public void setElementFqn(final String elementFqn) {
        this.elementFqn = elementFqn;
    }

    public String getElementName() {
        return elementName!=null ? elementName : "";
    }

    public String getElementLine() {
        return elementLine == null ? "" : elementLine;
    }


    public void setElementLine(final String aElementLine) {
        elementLine = aElementLine;
    }


    public boolean isValid() {
        return isElementFqnValid() && isElementLineValid();
    }


    public boolean isElementLineValid() {
        return elementLine != null;
    }


    public boolean isElementFqnValid() {
        return elementFqn != null;
    }

}
