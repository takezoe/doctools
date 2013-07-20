#{{anchor 第1章　準備, SECTION1}}
##{{anchor セットアップ, SETUP}}

###{{anchor Scalaのセットアップ, SETUP_SCALA}}

{{box 関連
- {{link SETUP_ECLIPSE}}
}}
{{box 利用例
- コマンドラインツールでScalaによる開発を行う場合
}}

Scalaの動作にはJava 5以上が必要なので事前にインストールしておく必要があります。Javaの実行環境は以下のURLから入手することができます。{{memo URLはあとで直す}}

- http://www.java.com/ja/

Scalaの実行環境は以下のURLから入手することができます。

- http://www.scala-lang.org/downloads

{{figure Scalaのダウンロードページ,DOWNLOAD_PAGE
ScalaDownload.png
}}

詳細については{{link DOWNLOAD_PAGE}}を参照のこと。

インストーラも用意されていますが、ここではアーカイブファイルから手動でインストールする方法を紹介します。

まずは上記のページからプラットフォームに応じて以下のいずれかのファイルをダウンロードします。

- scala-2.9.1-1.zip（Windowsの場合）
- scala-2.9.1-1.tgz（Linux、MacOS Xの場合）

ダウンロードしたアーカイブを適当なディレクトリに展開します。Scalaを使用するために必要なコマンド群は展開したディレクトリ配下のbinディレクトリに格納されているので、このディレクトリを環境変数PATHに追加するだけでインストールは完了です。

{{note ScalaIDE for Eclipseの動作環境
ScalaIDE for Eclipse（{{link SETUP_ECLIPSE}}参照）を使用する場合、ScalaIDE for EclipseにScalaのランタイムが含まれているため予めScalaをインストールしておく必要はありません。また、sbt（[[Chapter11]]参照）を使用する場合もsbtの実行時にScalaランタイムがダウンロードされるため、Scalaのインストールは不要です（sbt自身の動作に必要なScalaランタイムはsbtのjarファイル内に含まれています）。
}}

###{{anchor Eclipseのセットアップ, SETUP_ECLIPSE}}

{{keyword Eclipse,ScalaIDE for Eclipse}}
{{version 2.9, 2.10}}

{{box 関連
}}
{{box 利用例
- 統合開発環境を使用してScalaによる開発を行いたい場合
}}

EclipseはJava向けの統合開発環境として広く利用されていますが、プラグインによる拡張が可能でJava以外の言語での開発にも対応することができます。EclipseにScalaIDE for Eclipse（http://scala-ide.org/）をインストールすることでEclipse上でScalaによるプログラミングを行うことができます。

本書の執筆時点での最新版であるScalaIDE for Eclipse 2.0.0は以下の環境で動作します。

- JDK 5もしくは6
- JDTを含むEclipse 3.6 Helios（Eclipse 3.7 Indigoにインストールすることも可能ですが、一部の機能が動作しないようです）

####ScalaIDE for Eclipseのインストール

まずはEclipseをインストールします。本家eclipse.orgで配布されているパッケージを使用する場合は以下のURLからEclipse Classic、Eclipse IDE for Java DevelopersなどJDTが含まれているパッケージをダウンロードします。

- http://www.eclipse.org/downloads/

日本語化されたEclipseを使用したい場合はPleiadesがおすすめです。Pleiadesは以下のURLから入手することができます。

- http://mergedoc.sourceforge.jp/#pleiades.html

ScalaIDE for EclipseはEclipseの更新マネージャを使用してインストールできます。使用するScalaのバージョンによって更新サイトのURLが異なるので注意してください。

- Scala 2.9系向け ... http://download.scala-ide.org/releases-29/stable/site
- Scala 2.8系向け ... http://download.scala-ide.org/releases-28/stable/site

###{{anchor ScalaのAPIリファレンスを参照したい, API_DOC}}

{{keyword Scaladoc}}
{{version 2.9, 2.10}}

{{box 関連
- {{link ../chapter08/chapter08.md,SCALADOC}}
}}
{{box 利用例
- Scalaの標準APIを知りたい場合
}}

Scalaのダウンロードページ（http://www.scala-lang.org/downloads）からAPIリファレンス（scala-2.9.1-1-devel-docs.tgz）をダウンロードすることができます。このアーカイブにはScalaのサンプルコードも含まれています。

また、APIリファレンスはオンラインでも公開されており、以下のURLで参照することができます。

- http://www.scala-lang.org/api/current/index.html

{{figure Scala標準APIのリファレンス
API_Reference.png
}}

##{{anchor コンパイルと実行, COMPILE_AND_RUN}}

###{{anchor Scalaプログラムを実行したい, HELLO_WORLD}}

{{keyword scalacコマンド,scalaコマンド}}
{{version 2.9, 2.10}}

{{box 関連
- {{link REPL}}
}}
{{box 利用例
- Scalaプログラムをコマンドラインで実行したい場合
}}

ScalaプログラムはJavaのクラスファイルにコンパイルしてから実行します。

例として以下のようなプログラムをHelloWorld.scalaというファイル名で適当な場所に保存します。日本語を含む場合、ファイルの文字コードはUTF-8で保存する必要があります。

{{code HelloWorld.scala
object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello World!")
  }
}
}}

{{note 1行に複数の文を記述する
ScalaではJavaと違って行末に;（セミコロン）を記述する必要はありません（セミコロンを記述しても問題ありません）が、1行に複数の文を記述したい場合はセミコロンで区切ります。

```
print("Hello "); println("Scala!")
```
}}

このソースファイルを以下のようにしてscalacコマンドでコンパイルします。

```
C:\helloworld>scalac HelloWorld.scala

C:\helloworld>
```

コンパイルが成功するとJavaVM上で動作するクラスファイル（*.classファイル）が生成されます。

```
C:\helloworld>dir
 ドライブ C のボリューム ラベルは Windows7_OS です
 ボリューム シリアル番号は 0880-19E9 です

 C:\helloworld のディレクトリ

2012/04/08  17:23    <DIR>          .
2012/04/08  17:23    <DIR>          ..
2012/04/08  17:23               605 HelloWorld$.class
2012/04/08  17:23               637 HelloWorld.class
2012/04/08  17:23               101 HelloWorld.scala
               3 個のファイル               1,343 バイト
               2 個のディレクトリ  58,631,909,376 バイトの空き領域

C:\helloworld>
```

{{note コンパイル時の警告を表示する
scalacコマンドでのコンパイル時、コンパイルエラーではないものの利用が推奨されないAPIを使用している場合などコンパイラが警告を報告してくれる場合があります。scalacコマンドはデフォルトでは以下のように警告の件数のみ表示されます。

```
C:\helloworld>scalac HelloWorld.scala
warning: there were 1 deprecation warnings; re-run with -deprecation for details

one warning found

C:\helloworld>
```

scalacコマンドに-deprecationオプションを付与してコンパイルを行うことで警告の内容を確認することができます。

```
C:\helloworld>scalac -deprecation HelloWorld.scala
HelloWorld.scala:3: warning: method format in object Predef is deprecated: Use formatString.format(args: _*) or arg.formatted(formatString) instead
    println(format("Hello %s!", args(0)))
            ^
one warning found

C:\helloworld>
```
}}

続いてscalaコマンドでコンパイルしたプログラムを実行します。

```
C:\helloworld>scala HelloWorld
Hello World!

C:\helloworld>
```

mainメソッドの引数にはコマンドラインから指定した引数が文字列型の配列で渡されます。

{{code HelloWorldArgs.scala,HELLO_WORLD_ARGS_SCALA
object HelloWorldArgs {
  def main(args: Array[String]): Unit = {
    val name: String = args(0)
    println("Hello %s!".format(name))
  }
}
}}

実行例は以下のようになります。

```
C:\helloworld>scalac HelloWorldArgs.scala

C:\helloworld>scala HelloWorldArgs Scala
Hello Scala!

C:\helloworld>
```

{{column Appトレイト
scala.Appトレイトを使用すると実行可能なScalaプログラムを簡単に作成することができます。

以下は{{link HELLO_WORLD_ARGS_SCALA}}と同じ処理をAppトレイトを用いて実装した場合の例です。Appトレイトを使用することでmainメソッドの定義を省略できます。また、mainメソッドの引数として受け取っていたコマンドライン引数はAppトレイトに定義されているargsフィールドとして参照することができます。

{{code HelloWorldApp.scala
object HelloWorldApp extends App {
  val name: String = args(0)
  println("Hello %s!".format(name))
}
}}
}}


###{{anchor Scalaのディレクトリ・ファイル構成を知りたい, STRUCTURE}}

{{keyword パッケージ}}
{{version 2.9, 2.10}}

{{box 関連
- {{link ../chapter02/chapter02.md, PACKAGE}}
- {{link ../chapter04/chapter04.md, CLASS_DEFINITION}}
}}
{{box 利用例
- Scalaのディレクトリ・ファイル構成を知りたい場合
}}

Scalaでは1つのソースファイルに複数のpublicクラスを定義したり、ディレクトリ構造と関係なくパッケージを定義することができます。

Javaではソースファイルやディレクトリ構造に以下のような制約がありますが、Scalaにはこのような制約はありません。

- パッケージと同じ構造のディレクトリに配置すること
- 1つのソースファイルに定義できるpublicクラスは1つのみ
- ソースファイル名とpublicクラスのクラス名が一致していなくてはならない

{{code 1つのソースファイルに複数のクラスを定義
package jp.sf.amateras.scala

class HelloWorld {
  ...
}

class HelloWorld2 {
  ...
}
}}

パッケージ宣言に中括弧（{ ... }）を使用することで1つのソースファイル内に複数のパッケージを定義することもできます。

{{code 1つのソースファイルに複数のパッケージを定義
package jp.sf.amateras.scala.helloworld1 {
  ...
}

package jp.sf.amateras.scala.helloworld1 {
  ...
}
}}

このようにScalaではファイルやディレクトリの構造と実際のクラスやパッケージとの間に物理的な関連がなく、自由度の高い定義が可能になっています。しかし、関連の薄い複数のクラスを1つのソースファイルで定義したり、ディレクトリ構造を無視したパッケージ構成を定義すると、目的のクラスがどこで定義されているのかを探し出すことが難しくなってしまいます。1つのソースファイルには関連するものをまとめ、わかりやすいファイル名を付ける、また特に理由のない限りパッケージとディレクトリの構造は一致させておくとよいでしょう。

###{{anchor Scalaプログラムをコンパイルせずに実行したい, SCRIPT}}

{{keyword scalaコマンド}}
{{version 2.9, 2.10}}

{{box 関連
- {{link HELLO_WORLD}}
}}
{{box 利用例
}}

scalaコマンドにScalaプログラムのソースコードを指定することで、コンパイルを行わずソースコードを直接実行することができます。

```
C:\helloworld>scala HelloWorld.scala Scala
Hello Scala!

C:\helloworld>
```

また、scalaコマンドでは以下のようにScalaコードを直接記述したスクリプトファイルを実行することもできます。

{{code script.scala
// mainメソッドを定義せずにScalaコードを記述できる
println("Hello World!")

// コマンドライン引数も取得可能
println("Hello %s!".format(args(0)))
}}

```
C:\scala-2.9.0.1>scala script.scala Scala
Hello World!
Hello Scala!

C:\scala-2.9.0.1>
```

{{column Scalaでワンライナーを実行する
scalaコマンドに-eオプションを付けることでコマンドラインからワンライナーを実行することができます。

```
C:\helloworld>scala -e "println(\"Hello World!\")"
Hello World!

C:\helloworld>
```
}}

###{{anchor クラスパスを指定したい, CLASSPATH}}

{{keyword scalaコマンド,scalacコマンド,クラスパス}}
{{version 2.9, 2.10}}

{{box 関連
}}
{{box 利用例
- Scalaプログラムでライブラリを使用したい場合
}}

scalacコマンドやscalaコマンドに-cpオプションを指定します。

ScalaではScalaで書かれたライブラリはもちろんのこと、既存の膨大なJavaライブラリを使用することができます。これらのライブラリはjarファイルというアーカイブ形式で提供されており、使用するにはこのjarファイルをクラスパスに追加する必要があります。

以下の例はApache Commons IO（http://commons.apache.org/io/）を使用したScalaプログラムで、http://www.google.co.jp/ の内容を取得して標準出力に出力するというものです。

{{code Commons IOを使用したScalaプログラム（CommonsIOSample.scala）
import java.io._
import java.net._
import org.apache.commons.io._

object CommonsIOSample extends App {
  val in: InputStream = new URL("http://www.google.co.jp/").openStream()
  try {
    println(IOUtils.toString(in))
  } finally {
    IOUtils.closeQuietly(in)
  }
}
}}

このプログラムをコンパイル、実行するにはCommons IOのjarファイルが必要になります。以下のようにscalacコマンドに-cpオプションでjarファイルのパスを指定してコンパイルします。複数のjarファイルを指定する場合は;で区切ります（LinuxやMacの場合は;でなく:で区切ります）。

```
C:\helloworld> scalac -cp commons-io-2.0.1.jar CommonsIOSample.scala
```

同様に実行時も-cpオプションで使用するjarファイルを指定します。

```
C:\helloworld> scala -cp .;commons-io-2.0.1.jar CommonsIOSample
```

{{note クラスパス指定時の注意点
scalaコマンドに-cpオプションを指定しない場合デフォルトでカレントディレクトリがクラスパスに含まれますが、-cpオプションを指定する場合は明示的に指定する必要があります。そのため上記の実行例では-cpオプションの先頭で.（カレントディレクトリ）を指定しているということに注意してください。
}}

###{{anchor jarファイルを実行したい, RUN_JAR}}

{{keyword scalaコマンド,jarファイル}}
{{version 2.9, 2.10}}

{{box 関連
}}
{{box 利用例
- jarファイルにアーカイブしたScalaプログラムを実行したい場合
}}

まずは実行可能なjarファイルを作成します。どのクラスを実行するかを指定するためのマニフェストファイルを以下のような内容で作成します。

{{code MANIFEST.MF
Main-Class: HelloWorldArgs
}}

jarファイルの作成にはJDKに含まれているjarコマンドを使用します。

```
C:\scala-2.9.0.1>jar cvfm HelloWorldArgs.jar MANIFEST.MF *.class
マニフェストが追加されました。
HelloWorldArgs$.class を追加中です。(入 = 970) (出 = 558)(42% 収縮されました)
HelloWorldArgs.class を追加中です。(入 = 662) (出 = 539)(18% 収縮されました)

C:\helloworld>
```

実行可能なjarファイルは以下のようにしてscalaコマンドで実行することができます。

```
C:\helloworld>scala HelloWorldArgs.jar Naoki
Hello Naoki!

C:\helloworld>
```

###{{anchor 対話型シェル（REPL）を使いたい, REPL}}

{{keyword scalaコマンド}}
{{version 2.9, 2.10}}

{{box 関連
- {{link HELLO_WORLD}}
}}
{{box 利用例
- Scalaプログラムの動作を素早く確認したい場合
}}

scalaコマンドを実行するクラスやjarファイルなどを指定せずに実行すると対話型のシェルを起動することができます。

```
C:\helloworld>scala
Welcome to Scala version 2.9.1-1 (Java HotSpot(TM) Client VM, Java 1.6.0_31).
Type in expressions to have them evaluated.
Type :help for more information.

scala>
```

対話型シェルでは任意のScalaコードを実行することができるため、ライブラリの動作を確認したり、ちょっとしたコードを試すのに適しています。なお、対話型シェルは:quitで終了します。

```
scala> val name = "Naoki"
name: java.lang.String = Naoki

scala> val message = "Hello " + name + "!"
message: java.lang.String = Hello Naoki!

scala> println(message)
Hello Naoki!

scala> :quit
 
C:\helloworld>
```

対話型シェルで標準ライブラリ以外のライブラリを使用するにはScalaプログラムの実行時と同様、-cpオプションでクラスパスを指定します（{{link CLASSPATH}}参照）。ただし、多くのライブラリを使用している場合クラスパスの設定は非常に面倒です。[[Chapter11]]で取り上げているsbtを使用するとプロジェクトで使用しているライブラリをクラスパスに追加した状態で簡単に対話型シェルを起動することができます。詳細については{{link ../chapter11/chapter11.md, SBT_BUILD}}を参照してください。
