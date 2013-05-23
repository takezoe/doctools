package jp.sf.amateras.doctools

import Processor._

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
  println(
"""<!DOCTYPE html>
<html>
  <head>
    <title>%s</title>
  </head>
  <body>%s</body>
</html>""".format("test", process(value)))

}