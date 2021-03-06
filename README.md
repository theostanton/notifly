Notifly
=======

Incomplete / Work in progress. 

![](images/logo.png)

A wrapper for Androids notifications, including Nougats custom views.

Download
--------

Gradle:
```groovy
compile 'com.theostanton.notifly:notifly:0.1'
```

Snippets
--------

### Simplest example

```java
Notify.create(
        context,
        "Title",
        "Some content text",
        R.drawable.ic_some_drawable
).show();
```

### Accepts string resources

```java
Notify.create(
        context,
        R.string.title,
        R.string.content,
        R.drawable.ic_some_drawable
).show();
```

### Launch Activity onClick

```java
.onClick(
        NotificationIntent.activity(SomeActivity.class)
            .withArgument(SomeActivity.TEXT_ARG, "A text argument")
)
```

### Launch Service onClick

```java
.onClick(
        NotificationIntent.service(SomeService.class)
            .withArgument(SomeService.TEXT_ARG, "A text argument")
)
```

### On expansion 

```java
.style(
        ExpandedNotification.create("Line 1", "Line 2")
            .title("Title when expanded")
)
```

### Quick reply

```java
.reply( 
        NotificationReply.create(
            SomeActivity.REPLY_ARG, 
            "Quick reply",
            R.drawable.ic_some_drawable,
            SomeActivity.class
        ) 
)
```

### Update / New notification

Defaults to updating default notification. 

Use .append() to create a new notification. 

```java
.append()
```

### Dismiss on click

Call this method on the Activity / Service onCreate method to clear the clicked notification. 

```java
Notifly.onLaunch(this);
```

Custom views
======

For a simple layout

```xml 
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:tools="http://schemas.android.com/tools"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:orientation="horizontal"
              tools:context=".MainActivity">

    <Button
        android:id="@+id/button_yes"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"

        android:text="@string/yes"/>

    <Button
        android:id="@+id/button_no"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"

        android:text="@string/no"/>
</LinearLayout>
```

```java
Notify.createCustom(
        context,
        CustomNotificationView.from(context, R.layout.notification_view)
            .addAction(R.id.button_button_yes, "com.theostanton.app.YES")
            .addAction(R.id.button_no, "com.theostanton.app.NO"),
        R.drawable.ic_some_drawable
)
```

And in your `AndroidManifest.xml`

```xml
<service 
        android:name=".ListenerService"
        android:exported="true"
        android:label="@string/app_name">
        <intent-filter>
            <action android:name="com.theostanton.app.YES"/>
            <action android:name="com.theostanton.app.NO"/>
        </intent-filter>
</service>
```