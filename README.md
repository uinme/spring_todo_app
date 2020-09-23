# Spring Boot 基礎 チュートリアル

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

## ビューとコントローラ間のデータのやりとり

### 属性による受け渡し


### データバインド


## Spring JSBCによるデーターベース操作

CRUDのサンプル


## エラーメッセージの管理

エラーメッセージは`resources`ディレクトリに`messages.properties`を作成して管理する。
例えば、バリデーションの設定におけるメッセージを記述する場合、以下のようになる。

```
content=コンテンツ

notblack_todo_content={0}を入力してください
length_todo_content={0}は文字数{2}以下で入力してください
```

`{0}`は検証アノテーションで定義されているフィールドを示す。
`{0}`はアノテーションを付与したフィールド名が格納されており、
このまま用いると「contentを入力してください」のように表示される。
contentを日本語や別名にしたい場合、`content=コンテンツ`のように指定する。

上記の独自キーを用いた方法でメッセージ管理を行う場合、デフォルトの状態では、
`ValidationMessages.properties`に記述する必要がある。
このファイルをそのまま作成すればよいが、日本語が文字化けするため、
文字コードの設定を変更する必要がある。その設定はソースファイル配置配下に
`WebConfig.java`を作成して以下のようにする。

```java
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class WebConfig {
  
  @Bean
  public MessageSource messageSource() {
    
    ReloadableResourceBundleMessageSource bean =
      new ReloadableResourceBundleMessageSource();
    
    // メッセージプロパティのファイル名を指定する
    bean.setBasename("classpath:messages");

    // 日本語を扱えるようにするために、文字コードをUTF-8にする
    bean.setDefaultEncoding("UTF-8");

    return bean;

  }

  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean() {

    LocalValidatorFactoryBean localValidatorFactoryBean =
      new LocalValidatorFactoryBean();

    localValidatorFactoryBean.setValidationMessageSource(messageSource());

    return localValidatorFactoryBean;

  }

}
```

## ロギング


## Spring Securityを利用したログイン機能

