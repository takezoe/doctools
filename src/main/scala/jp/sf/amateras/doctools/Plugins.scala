package jp.sf.amateras.doctools

import Utils._
import Processor._

object Plugins {
  
  val inlinePlugins = Map(
      "anchor" -> ((args: Seq[String], source: String) => {
        if(args.size == 0){
          error("���x�������w�肵�Ă�������")
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
          error("�������s�����Ă��܂��B")
        } else {
          "<div class=\"column\">" +
          "<div class=\"header\">COLUMN %s</div>".format(escape(args(0))) +
          "<div class=\"content\">%s</div>".format(process(args(1))) +
          "</div>"
        }
      })
  )
}