package scalacask.engine.twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SpeedLog {
  long initTime;
  long startTime;
  String logName;
  List<String> records;
  int count = 0;

  SpeedLog(String logName) {
    this.logName = logName;
    records = new ArrayList<String>();
    this.initTime = System.nanoTime();
  }

  // Begin a time record
  public void startRecord() {
    startTime = System.nanoTime();
  }

  // End the current time record
  public void endRecord() {
    long curTime = System.nanoTime();
    long recordTime = curTime - startTime;
    StringBuilder sb = new StringBuilder();
    count++;
    sb.append(count);
    sb.append(',');
    sb.append(recordTime);
    sb.append('\n');
    records.add(sb.toString());
  }

  // Dump all records from memory to file
  public void dump() {
    try {
      PrintWriter pw = new PrintWriter(new File(this.logName + ".csv"));
      pw.write("count,record\n");
      for (String record : records) {
        pw.write(record);
      }
      pw.close();
      System.out.println("Log " + logName + " complete.");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
