package jp.sf.amateras.doctools

import Processor._
import java.io._
import Utils._
import scala.collection.mutable.ListBuffer

object Main extends App {
  
  val indices = new File("doc").listFiles.filter(_.isDirectory).sortBy(_.getName).map{ dir =>
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
  <body>
    <div class="main">%s</div>
    <div class="sidebar"><ul>%s</ul></div>
  </body>
</html>""".format(page, process(file, source, new Plugins()), 
    extractMemo(source).zipWithIndex.map { case ((name, message), i) =>
      name match {
        case Some(name) => "<li><a href=\"#memo-%d\">%s: %s</a></li>".format(i + 1, name, message)
        case None => "<li><a href=\"#memo-%d\">%s</a></li>".format(i + 1, message)
      }
    }.mkString("\n"))
    
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
    
  write(new File("doc/index.html"), html)
  
  def extractAnchor(value: String): (String, String) = {
    if(value.startsWith("{{anchor ") && value.endsWith("}}")){
      val args = splitArgs(value.substring("{{anchor ".length, value.length - 2).trim)
      (args(0), args(1))
    } else {
      ("", value)
    }
  }
  
  def extractMemo(value: String): Seq[(Option[String], String)] = {
    value.lines.map { line =>
      mapMatched(INLINE_PLUGIN_REGEX, line){ m =>
        val name = m.group(1)
        val args = splitArgs(m.group(2))
        name match {
          case "memo" if(args.size == 1) => Some((None, args(0)))
          case "memo" if(args.size >= 2) => Some((Some(args(0)), args(1)))
          case _ => None
        }
      }.flatten
    }.flatten.toSeq
  }
  
  println("Completed!")
}