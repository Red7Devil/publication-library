package DatabaseComm;

import java.util.List;

/**
 * Interface for generic operations on a  database table
 *
 * @param <T> -- Type of data to be passed
 */
public interface Dao<T> {
    T getById(String id);

    List<T> getAll();

    boolean save(T t);

    boolean update(String id, T t);
}
