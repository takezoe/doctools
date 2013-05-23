package jp.sf.amateras.doctools

import Utils._
import Processor._

object Plugins {
  
  val inlinePlugins = Map(
      "anchor" -> ((args: Seq[String], source: String) => {
        if(args.size == 0){
          error("ラベル名を指定してください")
        } else {
          "<a name=\"%s\"></a>".format(escape(args(0)))
        }
      }),
      "ref" -> ((args: Seq[String], source: String) => {
        ""
      }),
      "keyword" -> ((args: Seq[String], source: String) => {
        "<div class=\"keyword\"><span>%s</span></div>".format(args.map(escape).mkString(" | "))
      }),
      "version" -> ((args: Seq[String], source: String) => {
        "<div class=\"version\"><span>%s</span></div>".format(args.map(escape).mkString(" | "))
      })
  )
  
  val blockPlugins = Map(
      "column" -> ((args: Seq[String], source: String) => {
        if(args.size < 2){
          error("引数が不足しています。")
        } else {
          "<div class=\"column\">" +
          "<div class=\"header\">COLUMN %s</div>".format(escape(args(0))) +
          "<div class=\"content\">%s</div>".format(process(args(1))) +
          "</div>"
        }
      }),
      "box" -> ((args: Seq[String], source: String) => {
        if(args.size < 2){
          error("引数が不足しています。")
        } else {
          "<table class=\"box\"><tr>" +
          "<th>%s</th>".format(escape(args(0))) +
          "<td>%s</td>".format(process(args(1))) +
          "</tr></table>"
        }
      })
  )
}