/*
 * Copyright 2018 Jean-Baptiste VINCEY.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jbvincey.design.widget

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import com.jbvincey.design.R

/**
 * Created by jbvincey on 25/10/2018.
 */
class ValidationInputEditText @JvmOverloads constructor(context: Context,
                              attrs: AttributeSet? = null,
                              defStyleAttr: Int = R.attr.editTextStyle
): TextInputEditText(context, attrs, defStyleAttr) {

    companion object {
        val DEFAULT_VALIDATION_REGEX = ".*".toRegex()
    }

    /**
     * Regular expression against whom the input text is checked on validation.
     * This can be defined in xml with "validationRegex" tag (accepting a string or reference).
     */
    var validationRegex = DEFAULT_VALIDATION_REGEX

    /**
     * Error text to be displayed by when text is not valid on validation.
     * This can be defined in xml with "errorText" tag (accepting a string or reference).
     */
    var errorText: String? = null

    /**
     * When true, triggers text validation on editor action (e.g. action done on keyboard).
     * This can be defined in xml with "validateTextOnEditorAction".
     */
    var validateTextOnEditorAction: Boolean = false

    /**
     * Listener called when text is actually valid on validation.
     */
    var validationInputEditTextListener: ValidationInputEditTextListener? = null

    private lateinit var textInputLayout: TextInputLayout

    //region initialization

    init {
        initAttrs(context, attrs, defStyleAttr)
        init()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ValidationInputEditText, defStyleAttr, 0)
        try {
            validationRegex = typedArray.getString(R.styleable.ValidationInputEditText_validationRegex)?.toRegex() ?: DEFAULT_VALIDATION_REGEX
            errorText = typedArray.getString(R.styleable.ValidationInputEditText_errorText)
            validateTextOnEditorAction = typedArray.getBoolean(R.styleable.ValidationInputEditText_validateTextOnEditorAction, false)
        } finally {
            typedArray.recycle()
        }
    }

    private fun init() {
        addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { textInputLayout.error = null }
        })

        setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                imeOptions -> handleEditorAction()
                else -> false
            }
        }
    }

    private fun handleEditorAction(): Boolean {
        if (validateTextOnEditorAction) {
            return !validateText()
        }
        return false
    }

    //endregion

    //region TextInputLayout initialization

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initTextInputLayout()
    }

    private fun initTextInputLayout() {
        var parent = this.parent
        while (parent is View) {
            if (parent is TextInputLayout) {
                textInputLayout = parent
                textInputLayout.isErrorEnabled = true
            }
            parent = parent.getParent()
        }
        if (textInputLayout == null) {
            throw RuntimeException("ValidationInputEditText must be in a TextInputLayout")
        }
    }

    //endregion

    //region validation

    /**
     * Compares text against validation Regex, displaying error message on failure, and calling
     * listener on success.
     * @return true if text is validated
     */
    fun validateText(): Boolean {
        val text = text.toString()
        return if (text.matches(validationRegex)) {
            validationInputEditTextListener?.onValidText(text)
            true
        } else {
            textInputLayout.error = errorText
            false
        }
    }

    //endregion

}