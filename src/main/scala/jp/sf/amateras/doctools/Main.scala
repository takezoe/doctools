package jp.sf.amateras.doctools

import org.pegdown._
import org.pegdown.LinkRenderer.Rendering
import org.pegdown.ast.WikiLinkNode
import scala.util.matching.Regex

object Main extends App {
    
  val value = """
#{{anchor section2, bbb, ccc}}第2章 xxxx
##{{anchor introduction}} xxxxx
###{{anchor run_java_app}} Javaプログラムを実行したい
    
{{column JDKに付属するツールの使い方
  javacはほげほげ
```
ソースだって書けます
```
}}
"""
  
  def error(message: String) = "<span class=\"error\">%s</span>".format(message)
    
  val inlinePlugins = Map(
      "anchor" -> ((args: Seq[String]) => {
        if(args.size == 0){
          error("ラベル名を指定してください")
        } else {
          "<a name=\"%s\"></a>".format(args(0))
        }
      })
  )
    
  val html = new PegDownProcessor(Extensions.AUTOLINKS|Extensions.WIKILINKS|Extensions.FENCED_CODE_BLOCKS)
    .markdownToHtml(value, new LinkRenderer(){
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
    
    // process inline　plugins
    val r = new Regex("\\{\\{([a-z]+?)\\s(.*?)\\}\\}")
    println(r.replaceAllIn( html, { m =>
      val name = m.group(1)
      val args = m.group(2) // TODO ダブルクォートも考慮して分割する
      inlinePlugins(name)(args.split(",").map(_.trim).toSeq)
    }))
}