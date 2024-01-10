package ResearchAreaParents;

/**
 * Class to hold data related to a research area and its parent area
 */
public class ResearchAreaParent {
    private String researchArea;
    private String parentResearchArea;

    /**
     * Constructor to initialize the object
     *
     * @param researchArea       -- String name of this research area
     * @param parentResearchArea -- String name of the parent research area
     */
    public ResearchAreaParent(String researchArea, String parentResearchArea) {
        this.researchArea = researchArea;
        this.parentResearchArea = parentResearchArea;
    }

    /**
     * Getter method for parent research area
     *
     * @return -- String parent research area
     */
    public String getParentResearchArea() {
        return parentResearchArea;
    }

    /**
     * Setter method for parent research area
     *
     * @param parentResearchArea -- String name of the parent research area
     */
    public void setParentResearchArea(String parentResearchArea) {
        this.parentResearchArea = parentResearchArea;
    }

    /**
     * Getter method for research area
     *
     * @return -- String research area
     */
    public String getResearchArea() {
        return researchArea;
    }

    /**
     * Setter method for research area
     *
     * @param researchArea -- String name of the research area
     */
    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }
}
