package jp.sf.amateras.doctools

import scala.util.matching.Regex

object Utils {
  
  val INLINE_PLUGIN_REGEX = new Regex("\\{\\{([a-z_]+?)\\s+(.*?)\\}\\}")

  val BLOCK_PLUGIN_REGEX = new Regex("^\\{\\{([a-z_]+?)\\s+(.*?)$")
  
  def error(message: String) = "<span class=\"error\">%s</span>".format(message)
  
  def escape(value: String) = value
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&gt;")
    .replaceAll(">", "&lt;")
    .replaceAll("\"", "&quot;")

}