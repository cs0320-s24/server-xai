package edu.brown.cs.student.main.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

// The FileInput takes in a file_name, and verify if the file name is valid by searching for it in
// the data directory.
// if the CSV file with the file_name is found, open the file and read its content using FileReader
// and BufferedReader.
public class FileInput {
  private String file_name;
  private Path file_path;
  private File file;
  public BufferedReader file_reader;

  public FileInput(String file_name) throws FileNotFoundException, IOException {
    this.file_name = file_name;
    // try to find the path of the file_name provided
    try {
      Optional<Path> file_path = FileInput.get_path(file_name);
      if (file_path.isPresent()) {
        this.file_path = file_path.get().toAbsolutePath();
      }
    } catch (IOException e) {
      System.err.println("File {" + file_name + "}not found in the data directory");
      throw e;
    }
    // open the file with the full path
    this.file = new File(this.file_path.toString());
    // create a buffer from the opened file
    try {
      this.file_reader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      System.err.println("Reader cannot access the file");
      throw e;
    }
  }
  // for the sake of other process trying to access
  public void close() throws IOException {
    try {
      this.file_reader.close();
    } catch (IOException e) {
      System.err.println("Fail to close the file reader");
      throw e;
    }
  }

  // search for the CSV file with the file named in the data directory
  // rational: users usually only provide the name of csv file
  // we need to search for the file in the `data` directory
  public static Optional<Path> get_path(String file_name) throws IOException {
    Path startPath = Paths.get("data");
    Optional<Path> foundPath = null;
    try {
      foundPath =
          Files.walk(startPath)
              .filter(Files::isRegularFile)
              .filter(path -> path.getFileName().toString().equals(file_name))
              .findFirst();
    } catch (IOException e) {
      System.err.println("Unable the find the complete path of the file" + file_name);
      throw e;
    }
    return foundPath;
  }
  // for the sake of possible other process trying to read the same file

}
