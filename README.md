# Spring Boot 基本事項

## 前提としている環境

- IDE: VSCode (拡張機能: Java Extension Pack, Lombok Annotations Support for VS Code)
- プロジェクトマネージャー: Maven

## ディレクトリやソースコード作成場所に関するルールや慣習

Javaソースコードやhtmlは以下の場所に作成する。

- ソースコード配置場所: `src/main/java/{groupName}/{artifactId}`
- html配置場所: `src/main/resources/templates`
- cssやJavascriptなどの静的ファイル: `src/main/resources/static`

## データーベース接続設定

データーベースの設定は`resources`ディレクトリにある`application.properties`
を編集して設定を行う。以下に例を示す。

```
# MySQLの場合
spring.datasource.url=jdbc:mysql://localhost:3306/dbname?serverTimezone=JST
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=

# H2 Databaseの場合
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=su
spring.datasource.password=
spring.datasource.sql-script-encoding=UTF-8
spring.h2.console.enabled=true
spring.datasource.initialize=true
spring.datasource.schema=classpath:schema.sql
spring.datasource.data=classpath:data.sql
```

`spring.datasource.url`は`connection string`と呼ばれる接続情報を含む文字列を指定する。
書式はデーターベースごとに異なるため使用するデーターベースの`connection string`を参照すること。

H2 Databaseの場合、インメモリデータベースが利用でき、起動するごとにデーターがリセットされる。
このため、毎度、テーブル作成やデーター挿入を行うことになるが、上記ファイルのように`schema`と`data`
を設定すれば、`resources`ディレクトリ直下にある`schema.sql`と`data.sql`に基づいて自動的に行ってくれる。
ここで、`schema.sql`には`CREATE TABLE`文を記述し、`data.sql`には`INSERT INTO`文を記述する。

## ルーティング・コントローラー

コントローラのメソッドにアノテーションを付けてルーティング設定を行う。

```java
@Controller
public class staticPageController {
  
  // Getリクエストの場合
  @GetMapping("/home")
  public String getHome() {
    return "home";
  }

  // Postリクエストの場合
  @PostMapping("/home")
  public String postHome() {
    return "home";
  }

}
```

コントローラーの各メソッドの戻り値はビューファイルのパスを指定する。
パスの起点は`src/main/resources/templates`となる。
例えば、`src/main/resources/templates/home.html`であれば、
`home`を戻り値として返すようにする。

## モデルの定義

### 定義方法

以下に示す例のように、クラスに`@Data`アノテーションを付与して、
データーベースのカラムに対応付けてフィールドを定義する。
変数名は慣習としてキャメルケースにすること（SQL文はスネークケース）。

```java
import lombok.Data;

@Data
public class todoModel {
  
  private int id;
  private String content;

}
```

## ビューとコントローラ間のデータのやりとり

### 属性による受け渡し

`/sample`で`Get`リクエストを受け、コントローラで定義した変数を`sample.html`で
表示する例は以下のようになる。

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class SampleController {

  @GetMapping("/sample")
  public String getSample(Model model) {

    String text = "hello";

    // model.addAttribute("属性名", 何らのオブジェクト)
    model.addAttribute("text", hello);

    return "sample";

  }

}
```

ビューでは、次のようにして値を取り出す。

```html
<!-- textはコントローラで指定した属性名 -->
<p th:value="${text}"></p>
```

### データバインド

モデルをインスタンス化する際、入力フォームの値をモデルのフィールドに適用したい場合がある。
例として、ユーザーの新規作成があげられる。まず、次のようなユーザーモデルを想定する。

```java
import lombok.Data;

@Data
public class UserModel {
  
  private int id;
  private String email;
  private String username;
  private String password;

}
```

次にコントローラーは以下のように定義する。

```java
@Controller
public class UserController {

  @PostMapping("/user/create")
  public String postCreateUser(@ModelAttribute UserModel user, Model model) {

    

  }

}
```

### バリデーションの設定

バリデーションはモデルクラスに記述し、
検証を行いたいフィールドに検証用のアノテーションを付与する。
例えば、`todoModel`にバリデーションをかける場合、以下のようになる。

```java
@Data
public class todoModel {
  
  private int id;
  
  @NotBlank(message = "{notblack_todo_content}")
  @Length(max = 500, message = "{length_todo_content}")
  private String content;

}
```

`@NotBlank`は空欄の場合、エラーが発生する。
`@Length`は文字数の最小および最大値を検証する。
ここで、`message`オプションはその検証に失敗したとき生成されるエラーメッセージを
指定する。`{ }`で囲うと独自キーとして認識され、別ファイルに記述したエラーメッセージ一覧
と対応付けることができる。エラーメッセージは後述を参照のこと。

上記だけでは、例外発生のエラー画面がブラウザ上に表示され、適切なメッセージがユーザーに提供されないため、
以下のようにコントローラーにメソッドを作成する。

```java
@Controller
public class TodoController {

  // 入力ページのformタグからPostリクエストがくる想定
  @PostMapping("/todo/create")
  public String postCreateTodo(
    @ModelAttribute @Validated TodoModel todo,
    BindingResult bindingResult,
    Model model) {

    // ↑ バリデーションするモデルに@Validatedをつけた引数とバインディング結果を受け取るBindingResult引数を
    // 指定する

    // 検証に失敗した場合、入力ページに戻す
    if (bindingResult.hasErrors) {
      return getNewTodo(todo, model);
    }

    // 成功した場合、index.htmlを表示する
    return "index";

  }

  // 入力ページを表示するためのメソッド
  @GetMapping("/todo/new")
  public String getNewTodo(@ModelAttribute TodoModel todo, Model model) {

    return "todo/new";

  }

}
```

上記の`postCreateTodo`メソッドは検証に失敗した際、入力ページを再び表示する。
このとき、入力フォーム周辺にエラー内容を表示することがよく行われる。
その表示は以下のようにする。

```html
<!DOCTYPE html>
<html lang="ja" xmlns:th="http://www.thymeleaf.org">

<!-- 中略 -->

<form th:action="@{/post/create}" method="post" th:object="${postModel}">
  <label>Content</label>
  <input type="text" th:field="*{content}">
  <div th:if="${#fields.hasErrors('content')}" th:errors="*{content}">
</form>

<!-- 省略 -->
```

上記の`content`の部分は対象となるフィールド名を記述する。

## Spring JSBCによるデーターベース操作

CRUDのサンプル


## エラーメッセージの管理

バリデーションに関するエラーメッセージは`resources`ディレクトリに`ValidationMessages.properties`を作成して管理する。
例えば、バリデーションの設定で指定した独自キーに対するメッセージを記述する場合、以下のようになる。

```
content=コンテンツ

notblack_todo_content={0}を入力してください
length_todo_content={0}は文字数{2}以下で入力してください
```

`{*}`の`*`は検証アノテーションで設定したオプションの順番を表す。
例えば、`@Length(min = 10, max = 50)`とした場合、`{1}`および`{2}`は、それぞれ、`10`および`50`を示す。
`{0}`はアノテーションを付与したフィールド名が格納されており、
このまま用いると「contentを入力してください」のように表示される。
contentを日本語や別名にしたい場合、`content=コンテンツ`のように指定する。


## ロギング


## Spring Securityを利用したログイン機能

