public class IEEEReference {
    private int referenceIndex;
    private String publication;

    public IEEEReference(int referenceIndex, String publication) {
        this.referenceIndex = referenceIndex;
        this.publication = publication;
    }

    public String getPublication() {
        return publication;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }
}
