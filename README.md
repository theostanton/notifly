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

### Takes string resources

```java
Notify.create(
                getApplicationContext(),
                R.string.title,
                R.string.content,
                R.drawable.ic_some_drawable
        ).show();
```