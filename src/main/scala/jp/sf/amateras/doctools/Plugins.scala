package jp.sf.amateras.doctools

import Utils._
import Processor._

object Plugins {
  
  val inlinePlugins = Map(
      "anchor" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size == 0){
          error("ラベル名を指定してください")
        } else {
          "<a name=\"%s\"></a>".format(escape(args(0)))
        }
      }),
      "ref" -> ((args: Seq[String], context: PluginContext) => {
        ""
      }),
      "caption" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          error("captionプラグインの引数が不足しています。")
        } else {
          val category = args(0)
          val title    = args(1)
          val label    = if(args.size > 2) args(2) else ""
          
          val captions = (context.memo.getOrElse("captions", 
              new scala.collection.mutable.HashMap[String, Int]())
          ).asInstanceOf[scala.collection.mutable.Map[String, Int]]
          context.memo.put("captions", captions)
          
          val count = captions.getOrElse(category, 0)
          captions.put(category, count + 1)
          
          if(label == ""){
            "<span class=\"caption\">%s%d: %s</span>".format(
              escape(category), (count + 1), escape(title))
          } else {
            "<span class=\"caption\"><a name=\"%s\">%s%d: %s</a></span>".format(
              escape(label), escape(category), (count + 1), escape(title))
          }
        }
      }),
      "keyword" -> ((args: Seq[String], context: PluginContext) => {
        "<div class=\"keyword\"><span>%s</span></div>".format(args.map(escape).mkString(" | "))
      }),
      "version" -> ((args: Seq[String], context: PluginContext) => {
        "<div class=\"version\"><span>%s</span></div>".format(args.map(escape).mkString(" | "))
      })
  )
  
  val blockPlugins = Map(
      "column" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          error("columnプラグインの引数が不足しています。")
        } else {
          "<div class=\"column\">" +
          "<div class=\"header\">COLUMN %s</div>".format(escape(args(0))) +
          "<div class=\"content\">%s</div>".format(process(args(1))) +
          "</div>"
        }
      }),
      "box" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          error("boxプラグインの引数が不足しています。")
        } else {
          "<table class=\"box\"><tr>" +
          "<th>%s</th>".format(escape(args(0))) +
          "<td>%s</td>".format(process(args(1))) +
          "</tr></table>"
        }
      }),
      "code" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 1){
          error("codeプラグインの引数が不足しています。")
        } else {
          "<pre>%s</pre>".format(escape(args(0)))
        }
      })
  )
}