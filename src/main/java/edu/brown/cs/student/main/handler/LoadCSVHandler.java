package edu.brown.cs.student.main.handler;

import edu.brown.cs.student.main.input.FileInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class LoadCSVHandler implements spark.Route{
  @Override
  public Object handle(spark.Request req, spark.Response res) throws Exception {
    String name = req.queryParams("name").replace("%20", " ");

    Map<String, Object> responseMap = new HashMap<>();
    // check if the file is already loaded
    for (FileInput file : this.file_list) {
      if (file.file_name.equals(name)) {
        responseMap.put("result", "file already loaded");
        responseMap.put("csv name", name);
        responseMap.put("number of files loaded", this.file_list.size());
        return responseMap;
      }
    }
    try {
      FileInput fileInput = new FileInput(name);
      // if the file is loaded successfully, add it to the list of existing files
      this.file_list.add(fileInput);
      System.out.println("Successfully loaded " + name + " into the list of files");
      responseMap.put("result", "success");
      responseMap.put("csv name", name);
      responseMap.put("number of files loaded", this.file_list.size());
      return responseMap;
    } catch (IOException e) {
      responseMap.put("result", "Exception");
    }
    return responseMap;
  }
  public List<FileInput> file_list;
  public LoadCSVHandler(List<FileInput> file_list) {
    this.file_list = file_list;
  }
}
