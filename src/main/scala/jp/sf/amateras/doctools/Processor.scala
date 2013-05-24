package jp.sf.amateras.doctools

import org.pegdown._
import org.pegdown.LinkRenderer.Rendering
import org.pegdown.ast.WikiLinkNode
import scala.util.matching.Regex
import scala.collection.mutable.ListBuffer
import Utils._
import Plugins._

object Processor {
  
  case class PluginContext(source: String, memo: scala.collection.mutable.Map[String, Any])
  
  case class PluginNode(isBlock: Boolean, name: String, args: Seq[String])
  
  def process(value: String): String = {
    var plugins = new ListBuffer[PluginNode]()
    
    var blockPluginName: String = null
    var blockPluginArgs: Seq[String] = Nil
    var blockPluginBody: String = ""
    var blockPluginCount = 0
    
    // detect plugins
    val markdown = value.lines.map { line =>
      val sb = new StringBuilder()
  
      BLOCK_PLUGIN_REGEX.findFirstMatchIn(line) match { 
        case Some(m) if(!line.endsWith("}}")) => {
          blockPluginCount = blockPluginCount + 1
          if(blockPluginCount == 1){
            blockPluginName = m.group(1)
            blockPluginArgs = if(m.group(2) == null) Nil else splitArgs(m.group(2))
          }
          sb.append("\n{{{{" + plugins.length + "}}}}\n")
        }
        case _ if(line == "}}" && blockPluginCount > 0) => {
          blockPluginCount = blockPluginCount - 1
          if(blockPluginCount == 0){
            plugins.append(PluginNode(true, blockPluginName, blockPluginArgs :+ blockPluginBody))
            blockPluginName = null
            blockPluginArgs = Nil
            blockPluginBody = ""
          }
        }
        case _ if(blockPluginCount > 0) => {
          blockPluginBody = blockPluginBody + line + "\n"
        }
        case _ => {
          val m = INLINE_PLUGIN_REGEX.findAllIn(line)
          var i = 0
          while(m.hasNext){
            m.next
            val name = m.group(1)
            val args = m.group(2)
            sb.append(line.substring(i, m.start))
            sb.append("{{{{" + plugins.length + "}}}}")
            plugins.append(PluginNode(false, name, splitArgs(args)))
            i = m.end
          }
          if(i < line.length){
            sb.append(line.substring(i))
          }
        }
      }
      sb.toString
    }.mkString("\n")
    
    // process markdown
    var html = new PegDownProcessor(Extensions.AUTOLINKS|Extensions.WIKILINKS|Extensions.FENCED_CODE_BLOCKS)
      .markdownToHtml(markdown, new LinkRenderer(){
        override def render(node: WikiLinkNode): Rendering = {
          try {
            val text = node.getText
            val (label, page) = if(text.contains('|')){
              val i = text.indexOf('|')
              (text.substring(0, i), text.substring(i + 1))
            } else {
              (text, text)
            }
            val url = "%s.html".format(java.net.URLEncoder.encode(page.replace(' ', '-'), "UTF-8"))
            new Rendering(url, label);
          } catch {
            case e: java.io.UnsupportedEncodingException => throw new IllegalStateException();
          }
        }      
      })
    
    val context = new PluginContext(value, new scala.collection.mutable.HashMap[String, Any]())
    
    // replace plugins
    plugins.zipWithIndex.foreach { case (plugin, i) =>
      if(inlinePlugins.contains(plugin.name)){
          html = html.replace("{{{{" + i + "}}}}", inlinePlugins(plugin.name)(plugin.args, context))
      } else if(blockPlugins.contains(plugin.name)){
          html = html.replace("<p>{{{{" + i + "}}}}</p>", blockPlugins(plugin.name)(plugin.args, context))
      } else {
        if(plugin.isBlock){
          html = html.replace("<p>{{{{" + i + "}}}}</p>", "<p>" + error(plugin.name + "プラグインは存在しません。") + "</p>")
        } else {
          html = html.replace("{{{{" + i + "}}}}", error(plugin.name + "プラグインは存在しません。"))
        }
      }
    }
    
    html
  }
}