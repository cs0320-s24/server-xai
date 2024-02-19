package edu.brown.cs.student.main.object_classes;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class CountyBroadbandAccess extends County{
  public double broadband_access;
  public CountyBroadbandAccess(String full_name, int state_code, int county_code, double broadband_access) {
    super(full_name, state_code, county_code);
    this.broadband_access = broadband_access;
  }

  public static CountyBroadbandAccess deserializeCountyBroadbandAccess(String json) throws IOException {
    try {
      Moshi moshi = new Moshi.Builder().build();
      return moshi.adapter(CountyBroadbandAccess.class).fromJson(json);
    } catch (IOException e) {
      System.err.println("CountyBroadbandAccess: Error deserializing CountyBroadbandAccess");
      throw e;
    } catch (JsonDataException e) {
      System.err.println("CountyBroadbandAccess: Json was not in the correct format");
      throw e;
    }
  }

  public static String serializeCountyBroadbandAccess(CountyBroadbandAccess cba) {
    Moshi moshi = new Moshi.Builder().build();
    return moshi.adapter(CountyBroadbandAccess.class).toJson(cba);
  }

  public static List<CountyBroadbandAccess> deserializeCountyBroadbandAccessList(String json_list) throws IOException {
    try {
      Moshi moshi = new Moshi.Builder().build();
      Type listType = Types.newParameterizedType(List.class, CountyBroadbandAccess.class);
      JsonAdapter<List<CountyBroadbandAccess>> adapter = moshi.adapter(listType);
      return adapter.fromJson(json_list);
    } catch (IOException e) {
      System.err.println("CountyBroadbandAccess: Error deserializing CountyBroadbandAccess list");
      throw e;
    } catch (JsonDataException e) {
      System.err.println("CountyBroadbandAccess: Json was not in the correct format");
      throw e;
    }
  }

  public static String serializeCountyBroadbandAccessList(List<CountyBroadbandAccess> cbas) {
    Moshi moshi = new Moshi.Builder().build();
    Type listType = Types.newParameterizedType(List.class, CountyBroadbandAccess.class);
    JsonAdapter<List<CountyBroadbandAccess>> adapter = moshi.adapter(listType);
    return adapter.toJson(cbas);
  }
}
