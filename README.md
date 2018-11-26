# Linebot
これは、LinebotによるIoTの実現を目標に作成しているものである。
## Quickreplay機能の追加

今回のquickreplayの機能は一番簡易的な機能になります。  
「クイックリプライ」と入力するとquickreplayを動作させるようにする。　　

【変更点】  

クイックリプライ」と入力するとquickreplay機能を追加　　

Callbackクラスの中に以下の文を追加する。なお、importは必要に応じて追加する。  
※importする際にpom.xml内のLinebotのバージョンを全て2.1.0に変更する必要がある。変更しないとimportされずエラーが出てしまう。

まず、`Callback.java`の中に以下の文を追加。

```java:Callback.java
//quickreplayの機能
    @GetMapping("quickreplay")
    public Message get() {
        final List<QuickReplyItem> items = Arrays.<QuickReplyItem>asList(
                QuickReplyItem.builder()
                        .action(new MessageAction("メッセージ", "メッセージ"))
                        .build(),
                QuickReplyItem.builder()
                        .action(CameraAction.withLabel("カメラ"))
                        .build(),
                QuickReplyItem.builder()
                        .action(CameraRollAction.withLabel("カメラロール"))
                        .build(),
                QuickReplyItem.builder()
                        .action(LocationAction.withLabel("位置情報"))
                        .build(),
                QuickReplyItem.builder()
                        .action(PostbackAction.builder()
                                .label("PostbackAction")
                                .text("PostbackAction clicked")
                                .data("{PostbackAction: true}")
                                .build())
                        .build()
        );

        final QuickReply quickReply = QuickReply.items(items);

        return TextMessage
                .builder()
                .text("Message with QuickReply")
                .quickReply(quickReply)
                .build();
    }
```
この文の`QuickReplyItem.builder().action(CameraRollAction.withLabel("**********"))`の**********　には
アイコンに表示したい内容を入れる。

さらに、`Callback.java`の中にある`handleMessage`メソッドに以下の文を追加する。
```java:Callback.java
// 文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    public Message handleMessage(MessageEvent<TextMessageContent> event) {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        switch (text) {
            case "やあ":
                return greet();
            case "おみくじ":
                return replyOmikuji();
            case "部屋":
                return replyRoomInfo();
            case "バブル":
                return replyBubble();
            case "カルーセル":
                return replyCarousel();
                
-----------------　　　ここを追加　　　---------------------

            case "クイックリプライ":
                return get();
                
-------------------------------------------------

            default:
                return reply(text);
        }
    }
```

これで、`handleMessage`メソッドで「クイックリプライ」と入力することによって、`get()`が呼び出される。　　

## Linebotのバージョンの変更
Linebotのバージョンが`2.1.0`になっていない場合は変更しないとimportができないため、各箇所を変更する必要がある。　　
まず`pom.xml`の中を書き換える。

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.example</groupId>
	<artifactId>linebot</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>linebot</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.17.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
		<thymeleaf-layout-dialect.version>2.3.0</thymeleaf-layout-dialect.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.linecorp.bot</groupId>
			<artifactId>line-bot-api-client</artifactId>
			<version>2.1.0</version>//ここを変更
		</dependency>

		<dependency>
			<groupId>com.linecorp.bot</groupId>
			<artifactId>line-bot-model</artifactId>
			<version>2.1.0</version>//ここを変更
		</dependency>

		<dependency>
			<groupId>com.linecorp.bot</groupId>
			<artifactId>line-bot-servlet</artifactId>
			<version>2.1.0</version>//ここを変更
		</dependency>

		<dependency>
			<groupId>com.linecorp.bot</groupId>
			<artifactId>line-bot-spring-boot</artifactId>
			<version>2.1.0</version>//ここを変更
		</dependency>

        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>RELEASE</version>
            <scope>compile</scope>
        </dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>

    </dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>


</project>
```
すると、importができるようになる。
