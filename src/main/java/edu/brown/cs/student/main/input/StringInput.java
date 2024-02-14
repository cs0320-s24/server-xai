package edu.brown.cs.student.main.input;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Scanner;

// this class allows user to take in variable lines of keyboard input and parse them line by line
// to use this class with CSVParaser, just pass the reader to a CSVParaser constructor
public class StringInput {
  public BufferedReader reader;
  public String input;

  public StringInput() {}

  // read in standard input and concatenate lines with new lines as separator
  public void get_input() {
    StringBuilder inputBuilder = new StringBuilder();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter text (type 'exit' on a new line to finish):");

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if ("exit".equalsIgnoreCase(line.trim())) { // Stop reading on "exit"
        break;
      }
      inputBuilder.append(line).append("\n"); // Append each line with a newline
    }
    // Convert StringBuilder to String
    this.input = inputBuilder.toString();
    scanner.close();
  }

  public void get_input(String input) {
    this.input = input;
  }

  // create a reader from inputs
  // requires get_input called first
  public void create_reader() {
    this.reader = new BufferedReader(new StringReader(this.input));
  }
}
