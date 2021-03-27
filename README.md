# ChatView

チャット画面用のCustomViewです。
サンプルでDocomo雑談対話APIを利用しています。
実際の利用に際しては、各自でDocomo雑談対話APIのアプリケーションの利用申請が必要になります。

![](image/screen.png)


## 使用例


#### レイアウト

```activity_main.xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
    >

    <com.hnimrod.chatview.ChatView
            android:id="@+id/layout_chatview"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            />
</RelativeLayout>
```

#### コード

```MainActivity.java
public class MainActivity extends AppCompatActivity {

    private ChatView chatView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        chatView = (ChatView) findViewById(R.id.layout_chatview);
        
        chatView.addRightMessage("こんにちは");
        chatView.addLeftMessage("こんばんは");
    }        
}
```


#### Docomo雑談対話API対応

Docomo雑談対話APIを使うためには、[docomo Developer support](https://dev.smt.docomo.ne.jp/?p=login)への登録が必要になります。

docomo Developer support からアプリケーションの登録申請を行い、
発行された API key を、`DialogueClient.java` の `API_KEY` に設定して、
ビルドし直してください。

```DialogueClient.java
public static final String API_KEY = ""; // Docomo 雑談対話 API keyを設定してください
```