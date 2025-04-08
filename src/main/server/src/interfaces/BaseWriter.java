package interfaces;

import java.io.FileNotFoundException;
/**
 * Contains method for writing in  file
 * writes to File
 *
 */
public interface BaseWriter {
    void write(String path) throws FileNotFoundException;
}
