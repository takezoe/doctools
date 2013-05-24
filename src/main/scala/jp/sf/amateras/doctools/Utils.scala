package jp.sf.amateras.doctools

import java.io._
import scala.util.matching.Regex

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
    val in = new FileInputStream(file)
    val buffer = new Array[Byte](in.available())
    in.read(buffer)
    in.close
    new String(buffer, "UTF-8")
  }
  
  def write(file: File, value: String): Unit = {
    val out = new FileOutputStream(file)
    out.write(value.getBytes("UTF-8"))
    out.close
  }
    
}