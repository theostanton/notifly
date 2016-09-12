Notify
======
Work in progress. 

A wrapper for Androids notifications, including Nougats custom views.



```java
Notify.activity(this, "Title", "Some content text", R.drawable.ic_some_drawable)
    .onClick(
            NotificationIntent.activity(SomOtherActivity.class)
                    .withArgument(SecondActivity.TEXT_ARG, "A text argument")
    )
    .show();
```