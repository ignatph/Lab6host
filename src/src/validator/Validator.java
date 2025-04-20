package client.main.server.src.validator;
import java.io.Serializable;
/**
 * Common interface for all validators
 */
public interface Validator extends Serializable {

    static boolean validate(Object value) {
        return false;
    }
}
