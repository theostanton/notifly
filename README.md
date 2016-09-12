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
        ExpandedNotification.activity("Line 1", "Line 2")
)
```

