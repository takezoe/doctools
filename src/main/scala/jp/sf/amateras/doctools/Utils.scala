package jp.sf.amateras.doctools

import java.io._
import java.nio.file._
import scala.util.matching.Regex
import org.supercsv.io.CsvListReader
import org.supercsv.prefs.CsvPreference
import scala.collection.JavaConverters._

object Utils {
  
  val INLINE_PLUGIN_REGEX = new Regex("\\{\\{([a-z_]+?)(\\s+(.*?))?\\}\\}")

  val BLOCK_PLUGIN_REGEX = new Regex("^\\{\\{([a-z_]+?)(\\s+(.*?))?$")
  
  def error(message: String) = "<span class=\"error\">%s</span>".format(message)
  
  def argumentError(pluginName: String) = error("%sプラグインの引数が不正です。".format(escape(pluginName)))
  
  def splitArgs(value: String): Seq[String] = {
    val reader = new CsvListReader(new StringReader(value), CsvPreference.EXCEL_PREFERENCE)
    try {
      reader.read.asScala.map(_.trim).toSeq
    } finally {
      reader.close
    }
  }
  
  def escape(value: String) = value
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll("\"", "&quot;")

  def read(file: File): String = {
    if(!file.exists){
      ""
    } else {
      val in = new FileInputStream(file)
      try {
        val buffer = new Array[Byte](in.available())
        in.read(buffer)
        new String(buffer, "UTF-8")
      } finally {
        in.close
      }
    }
  }
  
  def write(file: File, value: String): Unit = Files.write(file.toPath, value.getBytes("UTF-8"))
  
  val ANCHOR_REGEX = new Regex("\\{\\{((code)|(table)|(figure)|(anchor))(\\s+([^}]*))?")
  
  def detectAnchor(label: String, source: String): Option[String] = {
    source.lines.map { line =>
      ANCHOR_REGEX.findAllMatchIn(line).map{ m =>
        (m.group(1), splitArgs(m.group(7)))
      }.collectFirst { 
        case (_, args) if(args.size >= 2 && args(1) == label) => args(0)
      }
    }.collectFirst {
      case Some(title) => title
    }
  }
  
}