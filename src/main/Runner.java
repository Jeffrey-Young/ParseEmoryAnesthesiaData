package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Runner {

  public static void main(String[] args) {
    String line = "";
    ArrayList<Subject> subjects = new ArrayList<Subject>();
    Subject currentSubject = null;
    try {
      BufferedReader reader = new BufferedReader(
          new FileReader("data/EESND_Anesthesia_History_for_Martin.csv"));
      // read in data
      reader.readLine(); // discard header line
      while (reader.ready()) {
        line = reader.readLine();
        if (line.split(",")[0].startsWith("R")) {
          // we have encountered a new subject
          if (currentSubject != null) {
            // if this isn't the first subject
            subjects.add(currentSubject);
          }
          currentSubject = new Subject(line.split(",")[0]);
        }
        if (line.split(",")[3].equals("Ketamine")) {
          // -2 on length to subtract "mg"
          currentSubject.addKetamine(Double.parseDouble(
              line.split(",")[4].substring(0, line.split(",")[4].length() - 2))
              / Double.parseDouble(line.split(",")[5]));
        } else if (line.split(",")[3].equals("Telazol")) {
          // -2 on length to subtract "mg"
          currentSubject.addTelazol(Double.parseDouble(
              line.split(",")[4].substring(0, line.split(",")[4].length() - 2))
              / Double.parseDouble(line.split(",")[5]));
        } else if (line.split(",")[3].equals("Isoflurane")) {
          double isoflurane = Double.parseDouble(line.split(",")[4].split("%")[0]);
          // now normalize dose and divide by 1.5 so we can compare to Wisc data
          isoflurane = (isoflurane
              * Double.parseDouble(line.split(",")[4].split("[(]")[1].split(" ")[0])) / 1.5;
          currentSubject.addIsoflurane(isoflurane);
        } else { // we've got a problem
          System.err.println(line);
          throw new RuntimeException("Unrecognized anesthetic");
        }
      }
      // add the last subject to the list
      subjects.add(currentSubject);
      reader.close();
      //now compute the normalized weighted sum
      for (Subject s : subjects){
        s.setNormalizedTotalKetamineIsoflurane((s.getIsoflurane() / 1.733333333333333) + (s.getKetamine() / 2.666666666667));
      }
      // write out data
      FileWriter output = new FileWriter("data/EmoryAnesthesiaData.csv");
      output.write("Subject,Ketamine,Telazol,Isoflurane,NormalizedTotalKetamineIsoflurane\n");
      for (Subject s : subjects) {
        output.write(s + "\n");
      }
      output.close();
    } catch (IOException | NumberFormatException e) {
      System.err.println(line);
      e.printStackTrace();
    }

  }

}
