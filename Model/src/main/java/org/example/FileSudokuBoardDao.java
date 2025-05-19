package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final Path directory;
    ObjectOutputStream outputStream; // Przykładowy zasób do zamykania
    ObjectInputStream inputStream;
    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);

    public FileSudokuBoardDao(String directoryPath) {  // nazwa katalogu jako parametr konstruktora
        this.directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
                //System.out.println("Directory created: " + directory.toAbsolutePath());
                logger.info("Directory created: " + directory.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("I can't create directory: " + directoryPath, e);
            }
        } else {
            //System.out.println("Using existing directory: " + directory.toAbsolutePath());
            logger.info("Directory already exists: " + directoryPath);
        }
    }

    @Override
    public void write(String name, SudokuBoard board) throws SudokuFileWriteException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory.resolve(name).toFile()))) {
            oos.writeObject(board);
        } catch (IOException e) {
            throw new SudokuFileWriteException("Can't write sudoku board!", e);
        }
    }

    @Override
    public SudokuBoard read(String name) throws SudokuFileReadException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directory.resolve(name).toFile()))) {
            return (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SudokuFileReadException("Can't read sudoku board!", e);
        }
    }


    @Override
    public List<String> names() {
        try {
            return Files.list(directory)
                    .filter(path -> path.toString().endsWith(".sudoku"))
                    .map(path -> path.getFileName().toString())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error listing files in directory: " + directory, e);
        }
    }


    @Override
    public void close() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            //System.out.println("Streams successfully closed.");
            logger.info("Streams successfully closed.");
        } catch (IOException e) {
            //System.err.println("Failed to close streams: " + e.getMessage());
            logger.error("Error closing streams: " + e.getMessage());
        }
    }
}
