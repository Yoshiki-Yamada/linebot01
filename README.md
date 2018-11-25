linebot
===============


quickreplay機能
----------------

今回のquickreplayの機能は一番簡易的な機能になります。  
「クイックリプライ」と入力するとquickreplayを動作させるようにしました。  

【変更点】  

クイックリプライ」と入力するとquickreplayを動作させるようにしました。  

Callbackクラスの中に以下の文を追加する。なお、importは必要に応じて追加する。  
※importする際にpom.xml内のSpring Bootのバージョンを全て2.1.0に変更する必要がある。

```
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
    
以下の文も変更する。  
------------------    
    
    ```
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
            case "クイックリプライ":
                return get();
            default:
                return reply(text);
        }
    }
    ```
