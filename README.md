[![Maven Central release](https://img.shields.io/badge/MavenCentral-3.0.2-blue.svg?style=flat)](https://s01.oss.sonatype.org/service/local/repositories/releases/content/com/github/jbvincey/design/3.0.2/)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

Design
=======

This design library intends to propose android widgets with enhanced capabilities. The widgets available are:
 * `ValidationInputEditText` a TextInputEditText with text validation
 * `SwipeCallback` an ItemTouchHelper.Callback implementation to display background / icon on item swipe in RecyclerView

## Install

Design is available on **Maven Central**. To use in your project, simply add the following dependency to your app:
```gradle
dependencies {
    ...
    implementation 'com.github.jbvincey:design:3.0.2'
    ...
}
```

#### WARNING

Design used to be available on **JCenter** under the package name `com.jbvincey:design`. 
Due to **JCenter** shutdown, the Design library has been migrated to **Maven Central** with a different package name `com.github.jbvincey:design`.
If you were pulling the Design library from **JCenter**, make sure to add **Maven Central** repository in your configuration and to update the package name of Design library.


## ValidationInputEditText

![ValidationInputEditText](assets/design_sample1.png?raw=true)
![ValidationInputEditText displaying error](assets/design_sample2.png?raw=true)

```xml
<android.support.design.widget.TextInputLayout
    android:layout_height="wrap_content"
    android:layout_width="match_parent">

    <com.jbvincey.design.widget.ValidationInputEditText
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        android:imeOptions="actionGo"
        android:hint="@string/hint"
        app:validationRegex="@string/validation_regex" 
        app:errorText="@string/validation_errortext"
        app:validateTextOnEditorAction="true" />

</android.support.design.widget.TextInputLayout>

```

`ValidationInputEditText` is intended to be used in a `TextInputLayout`. If it has no `TextInputLayout` parent, it will raise an `Exception`.

3 custom attributes are available:
 * `validationRegex`: a regular expression against whom the text will be checked on validation (can take a string or reference)
 * `errorText`: error text displayed when text does not match the regular expression defined above (can take a string or reference)
 * `validateTextOnEditorAction`: a boolean to define whether text should be checked on editor action (works with any `imeOptions` you set)

These attributes can also be defined programmatically.

Finally you can set a listener to perform action once text has been checked and is valid (here in Kotlin):
```
validationInputEditText.validationInputEditTextListener = ValidationInputEditTextListener {
    //your action
}
```

Text is checked on editor action if you set `validateTextOnEditorAction` to true. You can also trigger text validation with `validateText()` method:
```
validationInputEditText.validateText()
```

## SwipeCallback

![SwipeCallback on item swipe end](assets/design_sample3.png?raw=true)
![SwipeCallback on item swipe start](assets/design_sample4.png?raw=true)

Create your `SwipeCallback` to pass it to an `ItemTouchHelper` to be attached to a `RecyclerView`:
```
val swipeCallbackModelStart = SwipeCallbackModel(
    color,
    drawableStart,
    margin,
    SwipeCallbackListener { _ -> Toast.makeText(context, "on item swiped start", Toast.LENGTH_SHORT).show() }
)

val swipeControllerModelEnd = SwipeCallbackModel(
    color,
    drawableEnd,
    margin,
    SwipeCallbackListener { _ -> Toast.makeText(context, "on item swiped end", Toast.LENGTH_SHORT).show() }
)

val itemTouchHelperCallback = SwipeCallback(swipeCallbackModelStart, swipeControllerModelEnd, context)
val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback).apply {
    attachToRecyclerView(recyclerView)
}
```

`SwipeCallback` constructor takes 2 optional `SwipeCallbackModel` (one to swipe start and the other to swipe end) and a context.
If a `SwipeCallbackModel` is null, the corresponding swipe (start or end) will be disabled.

`SwipeCallbackModel` constructor takes 4 arguments:
 * `backgroundColor` an `Int` (`@ColorInt`) defining the background color on swipe
 * `actionDrawable` a `Drawable` defining the icon to display on swipe
 * `actionMargin` an `Int` corresponding to the side margin for the icon
 * `swipeCallbackListener` a `SwipeCallbackListener` where you can implement the action to do once item is swiped

## Contributors

Jean-Baptiste VINCEY, jbvincey@gmail.com


License
=======

    Copyright 2018 Jean-Baptiste VINCEY.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

