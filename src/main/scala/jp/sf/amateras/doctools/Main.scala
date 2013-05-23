package jp.sf.amateras.doctools

import Processor._
import java.io._
import Utils._

object Main extends App {
      
  new File(".").listFiles.filter(_.isDirectory).foreach{ dir =>
    dir.listFiles.filter(_.getName.endsWith(".md")).foreach { file =>
      println("Processing %s...".format(file.getAbsolutePath))
      val source = read(file)
      val html = 
"""<!DOCTYPE html>
<html>
  <head>
    <title>%s</title>
  </head>
  <body>%s</body>
</html>""".format(file.getName.replaceFirst("\\.md$", ""), process(source))
    
      write(new File(file.getParent, file.getName.replaceFirst("\\.md$", ".html")), html)
    }
  }
}