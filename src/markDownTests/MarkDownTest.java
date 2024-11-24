package markDownTests;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class MarkDownTest{
  Path path; List<String> content;
  public  MarkDownTest(Path path){
    this.path= path;
    this.content= new LoadFile().loadLines(path);
  }
  private boolean omitting = false;
  private boolean notOmit(String line) {
    if (line.contains("OMIT_START")){ omitting = true; }
    try{ return !omitting; }
    finally{ if (line.contains("OMIT_END")){ omitting = false; } }
  }
  public String toMarkdown(){
    return content.stream()
      .dropWhile(line -> !line.contains("/*START"))
      .skip(1)
      .map(this::lineToCode)
      .filter(this::notOmit)
      .takeWhile(line -> !line.contains("END*/"))
      .collect(Collectors.joining("\n"));
  }
  String lineToCode(String line){
    boolean code= line.contains("-----*/") || line.contains("/*-----");
    if (code){ return "`````"; }
    return line;
  }
}


