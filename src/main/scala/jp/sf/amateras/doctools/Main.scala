package jp.sf.amateras.doctools

import Processor._
import java.io._
import Utils._

object Main extends App {
      
  new File(".").listFiles.filter(_.isDirectory).foreach{ dir =>
    dir.listFiles.filter(_.getName.endsWith(".md")).foreach { file =>
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
</html>""".format(page, process(file, source))
    
      write(new File(file.getParent, page + ".html"), html)
    }
  }
}