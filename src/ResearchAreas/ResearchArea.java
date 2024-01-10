package ResearchAreas;

/**
 * Class to hold data related to a research area
 */
public class ResearchArea {
    private String name;

    /**
     * Constructor to initialize the object
     *
     * @param name -- String name of this research area
     */
    public ResearchArea(String name) {
        this.name = name;
    }

    /**
     * Getter method for name
     *
     * @return -- String name of this research area
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name
     *
     * @param name -- String name of this research area
     */
    public void setName(String name) {
        this.name = name;
    }
}
