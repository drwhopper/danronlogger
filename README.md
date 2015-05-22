# danronlogger
Ruru-Logger for Danganronpa

自由研究

## What is this?
「汝は人狼なりや？」るる鯖 で行われている「ダンガンロンパ人狼」の村立てを自動記録し、検索できるようにするアプリです。

るる鯖で配信されている村立て情報からダンガンロンパRP村を検知し、村が終了するとログを解析して検索可能な状態にします。


## 使用してるもの
* Scala
* Play-framework2.X
* PostgreSQL

## Usage
* PostgreSQLのインストール
* データベースの設定を conf/application.conf に
* activator (play framework) のダウンロード
* ./activator run とか

## Caution

* あんまりScalaっぽくない
* コードがださい。バッドスメル。
* なんでPostgreSQLのjdbcはsbt経由じゃなくて直置きなんだとかは気にしてはいけない
* コメントがとても少ない
* heroku用の謎スクリプトは削った（つもり

## 稼働例
<http://danron-ww-logger.herokuapp.com/>

## 日記
制作記録みたいなの書くかも