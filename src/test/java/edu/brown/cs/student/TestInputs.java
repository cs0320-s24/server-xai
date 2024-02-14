package edu.brown.cs.student;

import edu.brown.cs.student.main.input.FileInput;
import edu.brown.cs.student.main.input.StringInput;
import edu.brown.cs.student.main.parser.CSVParser;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestInputs {
  // this tests the basic functions of the FileInput class including
  // (1) searching for the path given the file name
  // (2) open the file using the path
  // (3) open a BufferedReader with the file
  @Test
  public void test_file_input() {
    String file_name = "income_by_race.csv";
    try {
      FileInput file_input = new FileInput(file_name);
      file_input.close();
    } catch (IOException e1) {
      System.out.println("Error in searching for the file");
    }
  }

  // this tests whether the FileInput class is compatible with the CSVParser class
  @Test
  public void test_file_input_with_parser() {
    String file_name = "income_by_race.csv";
    try {
      FileInput file_input = new FileInput(file_name);
      CSVParser parser = new CSVParser(file_input.file_reader, true);
      parser.parse();
      System.out.println(parser.num_lines);
      file_input.close();
    } catch (IOException e) {
      System.out.println("Error in searching for the file");
    }
  }

  // test the compatibility of StringInput and CSVParser
  @Test
  public void test_standard_input() {
    StringInput type_input = new StringInput();
    // instead of input something from terminal, let's just assign some string to the type_input
    // object
    //    type_input.get_input();
    type_input.input =
        "State,Data Type,Average Weekly Earnings,Number of Workers,Earnings Disparity,Employed Percent\n"
            + "RI,White,\" $1,058.47 \",395773.6521, $1.00 ,75%";
    type_input.create_reader();
    try {
      CSVParser parser = new CSVParser(type_input.reader, true);
      parser.parse();
      System.out.println(
          "Number of lines :" + parser.num_lines + " Number of columns: " + parser.num_cols);
      for (int i = 0; i < parser.num_lines; ++i) {
        List<String> line = (List<String>) parser.Content.get(i);
        for (int j = 0; j < line.size(); ++j) {
          System.out.print(line.get(j) + " | ");
        }
        System.out.println();
      }
    } catch (IOException e) {
      System.err.println("Something went wrong when parsing the input");
    }
  }
}
