[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

Design
=======

This design library intends to propose android widgets with enhanced capabilities. The widgets available are:
 * `ValidationInputEditText` a TextInputEditText with text validation 

## ValidationInputEditText

```xml
<android.support.design.widget.TextInputLayout
    android:layout_height="wrap_content"
    android:layout_width="match_parent">

    <com.jbvincey.design.validationinputedittext.ValidationInputEditText
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

