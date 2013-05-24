package jp.sf.amateras.doctools

import Utils._
import Processor._

object Plugins {
  
  val inlinePlugins = Map(
      "anchor" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size != 2){
          argumentError("anchor")
        } else {
          "<a name=\"%s\">%s</a>".format(escape(args(0)), escape(args(1)))
        }
      }),
      "link" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 1){
          argumentError("link")
        } else if(args.size == 1){
          // in the same page
          val label = args(0)
          val source = read(context.file)
          detectAnchor(label, source) match {
            case Some(title) => "<a href=\"#%s\">%s</a>".format(label, title)
            case None => error("%sは存在しません。".format(label))
          }
        } else {
          // in the other name
          val page  = args(0)
          val label = args(1)
          val source = read(new java.io.File(page + ".md"))
          detectAnchor(label, context.source) match {
            case Some(title) => "<a href=\"#%s\">%s</a>".format(label, title)
            case None => error("%sは存在しません。".format(label))
          }
        }
      }),
      "caption" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          argumentError("caption")
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
          argumentError("column")
        } else {
          "<div class=\"column\">" +
          "<div class=\"header\">COLUMN %s</div>".format(escape(args(0))) +
          "<div class=\"content\">%s</div>".format(process(context.file, args(1))) +
          "</div>"
        }
      }),
      "note" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 1){
          argumentError("note")
        } else {
          "<div class=\"note\">" +
          "<div class=\"content\">NOTE: %s</div>".format(process(context.file, args(0))) +
          "</div>"
        }
      }),      "box" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          argumentError("box")
        } else {
          "<table class=\"box\"><tr>" +
          "<th>%s</th>".format(escape(args(0))) +
          "<td>%s</td>".format(process(context.file, args(1))) +
          "</tr></table>"
        }
      }),
      "code" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 1){
          argumentError("code")
        } else {
          "<pre>%s</pre>".format(escape(args(0)))
        }
      })
  )
}