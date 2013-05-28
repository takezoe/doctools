package jp.sf.amateras.doctools

import Utils._
import Processor._

class Plugins {
  
  def getInlinePlugin(name: String): Option[(Seq[String], PluginContext) => String] = {
    DefaultPlugins.inlinePlugins.get(name)
  }
  
  def getBlockPlugin(name: String): Option[(Seq[String], PluginContext) => String] = {
    DefaultPlugins.blockPlugins.get(name)
  }
  
}

object DefaultPlugins {
  
  val inlinePlugins = Map(
      "anchor" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size != 2){
          argumentError("anchor")
        } else {
          "<a name=\"%s\">%s</a>".format(escape(args(0)), escape(args(1)))
        }
      }),
      "memo" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size == 0){
          argumentError("memo")
        } else {
          val count = (context.memo.getOrElse("memo", 0)).asInstanceOf[Int]
          context.memo.put("memo", count + 1)
          
          if(args.size == 1){
            "<span class=\"memo\"><a name=\"memo-%d\">%s</a></span>".format(count + 1, escape(args(0)))
          } else {
            "<span class=\"memo %s\"><a name=\"memo-%d\">%s: %s</a></span>".format(
              escape(args(0)), count + 1, escape(args(0)), escape(args(1)))
          }
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
            case None => error("#%sは存在しません。".format(label))
          }
        } else {
          // in the other name
          val page  = args(0)
          val label = args(1)
          val source = read(new java.io.File(page + ".md"))
          detectAnchor(label, context.source) match {
            case Some(title) => "<a href=\"#%s\">%s</a>".format(label, title)
            case None => error("%s#%sは存在しません。".format(page + ".html", label))
          }
        }
      }),
//      "caption" -> ((args: Seq[String], context: PluginContext) => {
//        if(args.size < 2){
//          argumentError("caption")
//        } else {
//          val category = args(0)
//          val title    = args(1)
//          val label    = if(args.size > 2) args(2) else ""
//          
//          val captions = (context.memo.getOrElse("captions", 
//              new scala.collection.mutable.HashMap[String, Int]())
//          ).asInstanceOf[scala.collection.mutable.Map[String, Int]]
//          context.memo.put("captions", captions)
//          
//          val count = captions.getOrElse(category, 0)
//          captions.put(category, count + 1)
//          
//          if(label == ""){
//            "<span class=\"caption\">%s%d: %s</span>".format(
//              escape(category), (count + 1), escape(title))
//          } else {
//            "<span class=\"caption\"><a name=\"%s\">%s%d: %s</a></span>".format(
//              escape(label), escape(category), (count + 1), escape(title))
//          }
//        }
//      }),
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
          "<div class=\"content\">%s</div>".format(process(context.file, args(1), context.plugins)) +
          "</div>"
        }
      }),
      "note" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 1){
          argumentError("note")
        } else {
          "<div class=\"note\">" +
          "<div class=\"content\">NOTE: %s</div>".format(process(context.file, args(0), context.plugins)) +
          "</div>"
        }
      }),
      "box" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          argumentError("box")
        } else {
          "<table class=\"box\"><tr>" +
          "<th>%s</th>".format(escape(args(0))) +
          "<td>%s</td>".format(process(context.file, args(1), context.plugins)) +
          "</tr></table>"
        }
      }),
      "figure" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          argumentError("code")
        } else {
          val category = "図"
          val title    = args(0)
          val label    = if(args.size > 1) args(1) else ""
          val count    = getCaptionCount(context, category)
          
          (if(label == ""){
            "<div class=\"caption\">%s%d %s</div>".format(escape(category), count + 1, escape(title))
          } else {
            "<div class=\"caption\"><a name=\"%s\">%s%d %s</a></div>".format(escape(label), escape(category), count + 1, escape(title))
          }) +
          "<div><img src=\"%s\"></div>".format(escape(args.last))
        }
      }),
      "code" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          argumentError("code")
        } else {
          val category = "リスト"
          val title    = args(0)
          val label    = if(args.size > 1) args(1) else ""
          val count    = getCaptionCount(context, category)
          
          (if(label == ""){
            "<div class=\"caption\">%s%d %s</div>".format(escape(category), count + 1, escape(title))
          } else {
            "<div class=\"caption\"><a name=\"%s\">%s%d %s</a></div>".format(escape(label), escape(category), count + 1, escape(title))
          }) +
          "<pre>" + escape(args.last) + "</pre>"
        }
      }),
      "table" -> ((args: Seq[String], context: PluginContext) => {
        if(args.size < 2){
          argumentError("table")
        } else {
          val category = "表"
          val title    = args(0)
          val label    = if(args.size > 1) args(1) else ""
          val count    = getCaptionCount(context, category)
          
          (if(label == ""){
            "<div class=\"caption\">%s%d %s</div>".format(escape(category), count + 1, escape(title))
          } else {
            "<div class=\"caption\"><a name=\"%s\">%s%d %s</a></div>".format(escape(label), escape(category), count + 1, escape(title))
          }) +
          "<table>" +
          (args.last.lines.toList match {
            case header :: rest => {
              "<tr>" + (splitArgs(header).map { x => "<th>%s</th>".format(escape(x)) }.mkString("")) + "</tr>" +
              (rest.map { row =>
                "<tr>" + (splitArgs(row).map { x => "<td>%s</td>".format(escape(x)) }.mkString("")) + "</tr>"
              }.mkString(""))
            }
            case Nil => ""
          }) + "</table>"
        }
      })
  )
  
  
  def getCaptionCount(context: PluginContext, category: String): Int = {
    val captions = (context.memo.getOrElse("captions", 
        new scala.collection.mutable.HashMap[String, Int]())
    ).asInstanceOf[scala.collection.mutable.Map[String, Int]]
    context.memo.put("captions", captions)
    
    val count = captions.getOrElse(category, 0)
    captions.put(category, count + 1)
    count
  }
  
}