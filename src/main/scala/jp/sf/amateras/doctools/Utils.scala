package jp.sf.amateras.doctools

import java.io._
import java.nio.file._
import scala.util.matching.Regex
import scala.collection.mutable.ListBuffer

object Utils {
  
  val INLINE_PLUGIN_REGEX = new Regex("\\{\\{([a-z_]+?)(\\s+(.*?))?\\}\\}")

  val BLOCK_PLUGIN_REGEX = new Regex("^\\{\\{([a-z_]+?)(\\s+(.*?))?$")
  
  def error(message: String) = "<span class=\"error\">%s</span>".format(message)
  
  def argumentError(pluginName: String) = error("%sプラグインの引数が不正です。".format(escape(pluginName)))
  
  def splitArgs(value: String) = value.split(",").map(_.trim).toSeq
  
  def escape(value: String) = value
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&gt;")
    .replaceAll(">", "&lt;")
    .replaceAll("\"", "&quot;")

  def read(file: File): String = {
    if(!file.exists){
      ""
    } else {
      val in = new FileInputStream(file)
      val buffer = new Array[Byte](in.available())
      in.read(buffer)
      in.close
      new String(buffer, "UTF-8")
    }
  }
  
  def write(file: File, value: String): Unit = Files.write(file.toPath, value.getBytes("UTF-8"))
  
  val ANCHOR_REGEX = new Regex("\\{\\{((anchor)|(caption))(\\s+(.*?))?\\}\\}")
  
  def detectAnchor(label: String, source: String): Option[String] = {
    source.lines.map { line =>
      ANCHOR_REGEX.findAllMatchIn(line).map{ m =>
        (m.group(1), splitArgs(m.group(5)))
      }.collectFirst { 
        case ("anchor" , args) if(args.size >= 2 && args(0) == label) => args(1)
        case ("caption", args) if(args.size >= 3 && args(2) == label) => args(1)
      }
    }.collectFirst {
      case Some(title) => title
    }
  }
  
}