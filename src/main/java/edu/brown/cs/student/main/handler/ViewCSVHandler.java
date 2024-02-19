package edu.brown.cs.student.main.handler;

import edu.brown.cs.student.main.input.FileInput;
import edu.brown.cs.student.main.parser.CSVParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Spark;

public class ViewCSVHandler implements spark.Route{
  @Override
  public Object handle(spark.Request req, spark.Response res) throws Exception {
    String name = req.queryParams("name").replace("%20", " ");
    String num_lines = req.queryParamOrDefault("num_lines", "5");
    String hasHeader = req.queryParams("hasHeader");
    Map<String, Object> responseMap = new HashMap<>();
    try {
      FileInput fileInput = new FileInput(name);
      CSVParser parser = new CSVParser(fileInput.file_reader, Boolean.parseBoolean(hasHeader));
      parser.parse();
      responseMap.put("result", "success");
      responseMap.put("viewcsv", parser.view_content(parser.Content, Integer.parseInt(num_lines)));
      return responseMap;
    } catch (IOException e) {
      responseMap.put("result", "Exception");
    }
    return responseMap;
  }

  public List<FileInput> file_list;
  public ViewCSVHandler(List<FileInput> file_list) {
    this.file_list = file_list;
  }

}
