package edu.brown.cs.student.main.handler;

import edu.brown.cs.student.main.input.FileInput;
import edu.brown.cs.student.main.parser.CSVParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCSVHandler implements spark.Route{
  @Override
  public Object handle(spark.Request req, spark.Response res) throws Exception {
    String name = req.queryParams("name").replace("%20", " ");
    String hasHeader = req.queryParamOrDefault("hasHeader", "true");
    String search = req.queryParams("search").replace("%20", " ");
    Map<String, Object> responseMap = new HashMap<>();
    try {
      FileInput fileInput = new FileInput(name);
      CSVParser parser = new CSVParser(fileInput.file_reader, Boolean.parseBoolean(hasHeader));
      parser.parse();
      responseMap.put("result", "success");
      responseMap.put("searchcsv", parser.view_content(parser.search(search)));
      return responseMap;
    } catch (IOException e) {
      responseMap.put("result", "Exception");
    }
    return responseMap;
  }

  public List<FileInput> file_list;
  public SearchCSVHandler(List<FileInput> file_list) {
    this.file_list = file_list;
  }

}
