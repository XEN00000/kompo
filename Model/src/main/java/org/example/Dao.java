package org.example;

import java.util.List;

public interface Dao<T> {
    T read(String name) throws SudokuFileReadException;

    void write(String name, T value) throws SudokuFileWriteException;

    List<String> names();

    void close() throws Exception;
}
