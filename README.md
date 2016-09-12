Notify
======
Work in progress. 

A wrapper for Androids notifications, including Nougats custom views.


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
        getApplicationContext(),
        R.string.title,
        R.string.content,
        R.drawable.ic_some_drawable
    ).show();
```

### PendingIntent builder

```java
.onClick(
        NotificationIntent.activity(SomeActivity.class)
            .withArgument(SomeActivity.TEXT_ARG, "A text argument")
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
            SecondActivity.REPLY_ARG, 
            "Quick reply",
            R.drawable.ic_some_drawable,
            SomeActivity.class
        ) 
)
```

### Update / New notification

Defaults to updating  default notification. Use .append() to create a new notification. 

```java
.append()
```

### Custom views

```java
Notify.createCustom(
        this,
        CustomNotificationView.from(
            this, 
            R.layout.notification_view
        )
        .addAction(R.id.button_one, "com.theostanton.app.ONE")
        .addAction(R.id.button_two, "com.theostanton.app.TWO")
        .addAction(R.id.button_three, "com.theostanton.app.THREE"),
        R.drawable.ic_3d_rotation_black_18dp
)
```