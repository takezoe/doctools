package jp.sf.amateras.doctools

import Processor._

object Main extends App {
      
  val value = """
#{{anchor section2, bbb, ccc}}��2�� xxxx
##{{anchor introduction}} xxxxx
###{{anchor run_java_app}} Java�v���O���������s������
    
{{column JDK�ɕt������c�[���̎g����
javac�͂ق��ق�
```
�\�[�X�����ď����܂�
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