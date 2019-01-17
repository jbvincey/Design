package com.jbvincey.designsample.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jbvincey.design.widget.ValidationInputEditTextListener
import com.jbvincey.designsample.R
import kotlinx.android.synthetic.main.fragment_validationinputedittext.*

/**
 * Created by jbvincey on 03/12/2018.
 */
class ValidationInputEditTextFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_validationinputedittext, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validationInputEditText.validationInputEditTextListener = ValidationInputEditTextListener {
            Snackbar.make(fragmentValidationRoot, R.string.text_validated, Snackbar.LENGTH_LONG).show()
        }
    }

}