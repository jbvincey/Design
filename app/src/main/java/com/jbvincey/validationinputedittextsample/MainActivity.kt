package com.jbvincey.validationinputedittextsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.jbvincey.validationinputedittext.ValidationInputEditTextListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        validationInputEditText.validationInputEditTextListener = ValidationInputEditTextListener {
            Snackbar.make(activity_main_root, R.string.text_validated, Snackbar.LENGTH_LONG).show()
        }
    }
}
