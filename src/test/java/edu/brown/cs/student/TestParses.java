package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.brown.cs.student.main.from_row.CreatorFromRow;
import edu.brown.cs.student.main.from_row.StarCreatorFromRow;
import edu.brown.cs.student.main.from_row.StringCreatorFromRow;
import edu.brown.cs.student.main.input.FileInput;
import edu.brown.cs.student.main.object_classes.Star;
import edu.brown.cs.student.main.parser.CSVParser;
import edu.brown.cs.student.main.parser.StarCSVParser;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestParses {
  @Test
  public void test_parse_census() {
    List<String> file_names =
        List.of(
            "dol_ri_earnings_disparity.csv", "income_by_race.csv", "postsecondary_education.csv");
    List<Integer> file_lengths = List.of(6, 323, 16);
    // for each file in the filename list, parse the csv file and perform sanity check
    for (int i = 0; i < file_names.size(); ++i) {
      String fn = file_names.get(i);

      try {
        FileInput file_input = new FileInput(fn);
        CSVParser parser = new CSVParser(file_input.file_reader, true);
        parser.parse();
        //                for (List<String> ls : parser.Content) {
        //                  for (String n : ls) {
        //                    System.out.print(n);
        //                  }
        //                  System.out.println();
        //                }
        assertEquals(parser.num_lines, file_lengths.get(i), "number of data should equal");
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  @Test
  public void test_parse_malformed() {
    List<Integer> file_lengths = List.of(12);
    try {
      FileInput file_input = new FileInput("malformed_signs.csv");
      CSVParser parser = new CSVParser(file_input.file_reader, true);
      parser.parse();
      System.out.println(parser.num_lines);
      assertEquals(parser.num_lines, file_lengths.get(0), "number of data should equal");
    } catch (IOException e) {
      System.err.println("Error in opening the file");
    }
  }

  @Test
  public void test_strings_with_quotes() {
    String file_name = "dol_ri_earnings_disparity.csv";

    try {
      FileInput file_input = new FileInput(file_name);
      CSVParser parser = new CSVParser(file_input.file_reader, true);
      parser.parse();
      for (int i = 0; i < parser.Content.size(); ++i) {
        // not recommended doing explicit type casting
        // figure out a way to avoid this
        List<String> line = (List<String>) parser.Content.get(i);
        for (String s : line) {
          System.out.print(s + "");
        }
        System.out.println();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void test_for_creator_from_row_string() {
    String file_name = "dol_ri_earnings_disparity.csv";

    try {
      FileInput file_input = new FileInput(file_name);
      CSVParser parser = new CSVParser(file_input.file_reader, true);
      parser.parse();
      CreatorFromRow creator = new StringCreatorFromRow();
      parser.create_from_row(creator);
      for (int i = 0; i < parser.ConvertedContent.size(); ++i) {
        String line = (String) parser.ConvertedContent.get(i);
        System.out.println(line);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void test_for_search() {
    String file_name = "dol_ri_earnings_disparity.csv";
    String value_to_search = "Black";
    try {
      FileInput file_input = new FileInput(file_name);
      CSVParser parser = new CSVParser(file_input.file_reader, true);
      parser.parse();
      // test the search function without column ID provided
      List<List<String>> result1 = parser.search(value_to_search);
      assertEquals(
          result1, List.of(List.of("RI", "Black", "$770.26", "30424.80376", "$0.73", "6%")));
      // test print_result functuon
      parser.print_result(result1);
      // test the search function with column name provided
      List<List<String>> result2 = parser.search(value_to_search, "Data Type");
      assertEquals(
          result2, List.of(List.of("RI", "Black", "$770.26", "30424.80376", "$0.73", "6%")));
      // test the search function with invalid column name provided
      List<List<String>> result3 = parser.search(value_to_search, "Data-Type");
      assertEquals(
          result1, List.of(List.of("RI", "Black", "$770.26", "30424.80376", "$0.73", "6%")));
      // test the search function with wrong but valid column name provided
      List<String> result4 = parser.search(value_to_search, "State");
      assert (result4.isEmpty());
      assertEquals(
          result2, List.of(List.of("RI", "Black", "$770.26", "30424.80376", "$0.73", "6%")));
      // test the search function with column index provided
      List<List<String>> result5 = parser.search(value_to_search, 1);
      assertEquals(
          result5, List.of(List.of("RI", "Black", "$770.26", "30424.80376", "$0.73", "6%")));
      // test the search function with valid but wrong column index provided
      List<List<String>> result6 = parser.search(value_to_search, 4);
      assert (result6.isEmpty());

      file_input.close();
    } catch (IOException e) {
      System.out.println("Something is wrong when access the file");
    }
  }

  @Test
  public void test_for_iterable() {
    String file_name = "stardata.csv";
    try {
      FileInput file_input = new FileInput(file_name);
      StarCSVParser parser = new StarCSVParser(file_input.file_reader, true);
      //      CSVParser<Star> parser = new CSVParser<>(file_input.file_reader, true);
      parser.parse();
      parser.create_from_row(new StarCreatorFromRow(), 100);
      int num_stars = 0;
      for (Star s : parser) {
        num_stars++;
        System.out.println("Star number: " + num_stars + " Star Profile: " + s);
      }
      System.out.println(num_stars);
    } catch (IOException e) {
      System.err.println("Errors occurred when opening the file");
    }
  }
}
