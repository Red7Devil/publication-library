package Helpers;

import Authors.Author;
import Authors.AuthorsService;
import PublicationAuthors.PublicationAuthor;
import PublicationAuthors.PublicationAuthorService;
import Publications.Publication;
import Publications.PublicationsService;

import java.util.Map;
import java.util.Set;

public class PublicationsHelper {
    /**
     * Helper method to store the given publication to the database
     *
     * @param publication -- Publication object to be stored
     * @return -- true if added successfully, else return false
     */
    public static boolean addPublication(Publication publication) {
        PublicationsService publicationsService = new PublicationsService();
        String identifier = publication.getPublicationID();

        boolean publicationIdentifierExists = publicationsService.getById(identifier) != null;
        if (publicationIdentifierExists) {
            return false;
        } else {
            try {
                publicationsService.save(publication);
            } catch (RuntimeException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Helper method to store the authors of a publication and link it to the publication in the database
     *
     * @param publicationID -- String ID of the publication
     * @param authors       -- Set of Authors to be stored
     * @return -- true if all authors added and linked to the publication
     */
    public static boolean addPublicationAuthors(String publicationID, Set<Author> authors) {
        AuthorsService authorsService = new AuthorsService();
        PublicationAuthorService publicationAuthorService = new PublicationAuthorService();

        // Add each unique author to the database and then link it to the publication
        for (Author author : authors) {
            boolean authorExists = authorsService.getById(author.getName()) != null;
            if (!authorExists) {
                try {
                    authorsService.save(author);
                } catch (RuntimeException e) {
                    return false;
                }
            }

            try {
                publicationAuthorService.save(new PublicationAuthor(publicationID, author.getName()));
            } catch (RuntimeException e) {
                return false;
            }
        }

        return true;
    }

    public static void extractPublicationInfo(Map<String, String> publicationInformation, Publication publication, Set<Author> authors) {
        for (String key : publicationInformation.keySet()) {
            //If a value is null, convert it to an empty string
            String value = publicationInformation.get(key);
            if (value == null) {
                value = "";
            }

            switch (key) {
                case "title":
                    publication.setTitle(value);
                    break;
                case "pages":
                    publication.setPages(value);
                    break;
                case "volume":
                    publication.setVolume(value);
                    break;
                case "issue":
                    publication.setIssue(value);
                    break;
                case "month":
                    publication.setMonth(value);
                    break;
                case "year":
                    publication.setYear(value);
                    break;
                case "location":
                    publication.setLocation(value);
                    break;
                case "journal":
                case "conference":
                    publication.setVenue(value);
                    break;
                case "authors":
                    for (String author : value.split(",")) {
                        authors.add(new Author(author));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static boolean isPublicationIdValid(String identifier) {
        PublicationsService publicationsService = new PublicationsService();
        boolean identifierExists = false;
        try {
            Publication publications = publicationsService.getById(identifier);
            if (publications != null) {
                identifierExists = true;
            }
        } catch (RuntimeException e) {
            return false;
        }

        boolean validIdentifier = identifier != null && !identifier.isEmpty() && identifierExists;

        return validIdentifier;
    }
}
