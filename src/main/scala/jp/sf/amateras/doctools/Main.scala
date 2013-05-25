package jp.sf.amateras.doctools

import Processor._
import java.io._
import Utils._

object Main extends App {
  
  val indices = new File(".").listFiles.filter(_.isDirectory).sortBy(_.getName).map{ dir =>
    dir.listFiles.filter(_.getName.endsWith(".md")).map { file =>
      println("Processing %s...".format(file.getAbsolutePath))
      val source = read(file)
      val page = file.getName.replaceFirst("\\.md$", "")
      val html = 
"""<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <link href="../style.css" media="all" rel="stylesheet" type="text/css" />
    <title>%s</title>
  </head>
  <body>%s</body>
</html>""".format(page, process(file, source, new Plugins()))
    
      write(new File(file.getParent, page + ".html"), html)
      
      // correct information for the index page
      val index = source.lines.filter { line =>
        line.matches("^#[^#]+") || line.matches("^##[^#]+") || line.matches("^###[^#]+")
      }.map { line =>
        if(line.startsWith("###")){
          val anchor = extractAnchor(line.substring(3).trim)
          (3, anchor._1, anchor._2)
        } else if(line.startsWith("##")){
          val anchor = extractAnchor(line.substring(2).trim)
          (2, anchor._1, anchor._2)
        } else {
          val anchor = extractAnchor(line.substring(1).trim)
          (1, anchor._1, anchor._2)
        }
      }
      (file, index)
    }
  }
  
  // generate the index page
  println("Generating index.html...")
  val html = 
"""<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <link href="style.css" media="all" rel="stylesheet" type="text/css" />
    <title>Index</title>
  </head>
  <body>
    <h1>Index</h1>
    %s
  </body>
</html>""".format(indices.flatten.map { case (file, index) =>
    processMarkdown((index.map { case (level, label, title) =>
      level match {
        case 1 => "##[%s](%s/%s#%s)\n".format(title, file.getParentFile.getName, file.getName.replaceFirst("\\.md$", ".html"), label)
        case 2 => "- [%s](%s/%s#%s)\n".format(title, file.getParentFile.getName, file.getName.replaceFirst("\\.md$", ".html"), label)
        case 3 => "    - [%s](%s/%s#%s)\n".format(title, file.getParentFile.getName, file.getName.replaceFirst("\\.md$", ".html"), label)
      }
    }.mkString("")))
  }.mkString(""))
    
  write(new File("index.html"), html)
  
  def extractAnchor(value: String): (String, String) = {
    if(value.startsWith("{{anchor ") && value.endsWith("}}")){
      val args = splitArgs(value.substring("{{anchor ".length, value.length - 2).trim)
      (args(0), args(1))
    } else {
      ("", value)
    }
  }
  
  println("Completed!")
}