package Authors;

/**
 * Class to store an author
 */
public class Author {
    private String name;

    /**
     * Constructor to initialize the object
     *
     * @param name -- String name of this author
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * Getter method for the author's name
     *
     * @return -- String name of this author
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the author's name
     *
     * @param name -- String name of this author
     */
    public void setName(String name) {
        this.name = name;
    }
}
