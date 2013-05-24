#{{anchor section2, 第2章 Javaの基本構文}}
##{{anchor introduction, 制御構文}}
###{{anchor run_java_app, 繰り返し処理を行いたい}}

{{keyword for,while,do-while}}
{{version 6,7,8}}

{{box 関連
- xxxxxxxxxxxxxxxxxxx
- xxxxxxxxxxxxxxxxxxx
}}
{{box 利用例
- 条件や配列に応じた繰り返し処理をしたい場合
}}

同じ処理を繰り返し実行したい場合、for文、while文、do-while文での繰り返し構文が利用できます。 

####for文

forループの構文にはfor文とJavaSE5.0以降で記述可能となった拡張for文の2種類があります。 

{{caption リスト,for文の構文,for_syntax}}
{{code
for (初期化式; 条件式; 更新式)
 文
}}

初期化式には、繰り返し処理の開始時に１度だけ実行される式です。初期化式の実行後、条件式が評価されます。評価結果がtrueであれば文を実行し、文の実行後は、更新式が実行されてから、条件式の評価に戻ります。評価結果がfalseであれば繰り返し処理は終了します。

初期化式では、ループ制御用変数が定義できます。初期化式で定義した変数はfor文内にスコープを限定できるため、ループ制御用の変数は初期化式にて定義することが望ましいです。 

{{column JDKに付属するツールの使い方
javacはほげほげ
```
ソースだって書けます
```
}}

詳細については{{link for_syntax,for文の構文}}を参照してください。
