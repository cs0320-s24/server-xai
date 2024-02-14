package edu.brown.cs.student.main.from_row;

import java.util.List;

public class StringCreatorFromRow implements CreatorFromRow<String> {

  @Override
  public String create(List<String> row) {
    String result = "";
    for (String cell : row) {
      result += cell;
    }
    return result;
  }
}
