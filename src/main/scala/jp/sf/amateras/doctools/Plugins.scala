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
      })
  )
}