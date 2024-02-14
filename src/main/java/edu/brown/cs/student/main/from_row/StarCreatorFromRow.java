package edu.brown.cs.student.main.from_row;

import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.main.object_classes.Star;
import java.util.List;

public class StarCreatorFromRow implements CreatorFromRow<Star> {
  @Override
  public Star create(List<String> row) throws FactoryFailureException {
    try {
      int id = Integer.parseInt(row.get(0));
      String name = row.get(1);
      float x = Float.parseFloat(row.get(2));
      float y = Float.parseFloat(row.get(3));
      float z = Float.parseFloat(row.get(4));
      Star star = new Star(id, name, x, y, z);
      return star;
    } catch (NumberFormatException e) {
      throw new FactoryFailureException("Invalid number format in row", row);
    }
  }
}
