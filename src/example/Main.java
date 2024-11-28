package example;

import java.nio.file.Path;
import java.io.IOException;
import markDownTests.DocumentProcessor;
import markDownTests.HtmlCreator;

public class Main {
  public static void main(String[] args) throws IOException{
    //Remember to set the working directory to \\GitHub\\MarkDownTests in the run configurations
    var root=     Path.of("src","tour");//Path.of("src","exampleTests");
    var dest=     Path.of("htmlOut2");
    var creator=  new HtmlCreator(dest);
    var chapters= new DocumentProcessor().processFiles(root);
    creator.generateHtmlPages(chapters);
    
    }
}