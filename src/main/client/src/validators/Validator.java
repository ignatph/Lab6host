package validators;
import java.io.Serializable;
/**
 * Common interface for all validators
 */
public interface Validator<T> extends Serializable {
     boolean validate(T value) ;
}
