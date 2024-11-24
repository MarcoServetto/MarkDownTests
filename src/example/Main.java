package example;
//public class Main {    public static void main(String[] args) {
//        System.out.println("Hello World");
//    }
//}

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

import markDownTests.MarkDownTest;

public class Main {
    public static void main(String[] args) throws IOException{
        var test= new MarkDownTest(Path.of("src","exampleTests","ExampleTestMarkDown.java"));
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        Node document = parser.parse(test.toMarkdown());
        String html = renderer.render(document);
        System.out.println(test.toMarkdown());
        String wrappedHtml = String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Markdown Documentation</title>
                <link rel="stylesheet" href="styles.css">
            </head>
            <body>
                <div class="markdown-content">
                    %s
                </div>
            </body>
            </html>
            """, html);
            
        // Write to file instead of printing
        Files.writeString(Path.of("htmlOut","output.html"), wrappedHtml);
    }
}