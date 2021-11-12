package com.project.bitereg.utils

import android.content.Context
import android.util.Log
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.project.bitereg.R

/**
 * Common Utility Functions
 */
object CommonUtils {

    private const val TAG = "CommonUtils"

    /**
     * This method verifies if username is valid or not.
     * @param name: Username for verification
     * */
    private fun verifyUserName(name: CharSequence): Boolean {
        name.split(" ").forEach {
            Log.d(TAG, "verifyUserName: $it")
            if (!it.lowercase().matches(Regex("[a-z]+"))) return false
        }
        return true
    }

    /**
     *  This method matches the email address with BIT Web Mail
     *  @param email Email address for verification
     * */
    private fun isEmailValid(email: String) =
        email.matches(Regex("(btech|bba|bca)([0-9]{5}\\.[0-9]{2})@bitmesra\\.ac\\.in"))

    /**
     *  This method verifies if the entered password is valid or not.
     *  A valid password must:
     *  - Have a length of 8 or more.
     *  - Must have a ,lower case alphabet, an upper case alphabet, a digit and a special character.
     */
    private fun isPasswordValid(password: String) =
        password.matches(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"))

    /**
     * This method is used to set error state to a TextInputLayout.
     *
     * @param inputLayout: Layout to setup error stat.
     * @param errorStateColor: Depicts the error state tint.
     * @param defaultStateColor: Depicts the drawable state tint.
     */
    fun setupErrorState(
        inputLayout: TextInputLayout,
        errorStateColor: Int = R.attr.colorError,
        defaultStateColor: Int = R.color.primary_accent
    ) {
        inputLayout.apply {
            editText?.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && inputLayout.error != null) {
                    startIconDrawable?.setTint(context.getColorFromAttr(errorStateColor))
                }
            }

            editText?.doOnTextChanged { _, _, _, _ ->
                if (error != null) {
                    error = null
                    startIconDrawable?.setTint(context.getColor(defaultStateColor))
                }
            }
        }
    }

    /**
     * This method checks if input layout is blank and sets error message.
     *
     * @param inputLayout: TextInput to check if it is empty or not.
     * @param shouldRequestFocus: Flag to toggle request focus behavior.
     */
    fun verifyInput(
        type: VerificationType,
        inputLayout: TextInputLayout,
        shouldRequestFocus: Boolean = true
    ): Boolean {
        setupErrorState(inputLayout)
        val inputText = inputLayout.editText?.text.toString()
        Log.d(TAG, "verifyInput: $inputText")

        val isInputValid = when (type) {
            VerificationType.BLANK_CHECK -> inputText.isNotEmpty()
            VerificationType.EMAIL -> isEmailValid(inputText)
            VerificationType.PASSWORD -> isPasswordValid(inputText)
            VerificationType.USER_NAME -> verifyUserName(inputText)
        }

        if (isInputValid) return true

        val errorMessage = when (type) {
            VerificationType.BLANK_CHECK -> "This should not be blank!!"
            VerificationType.EMAIL -> "Invalid Email, please enter BIT Web Mail!"
            VerificationType.PASSWORD -> "Invalid Password!!"
            VerificationType.USER_NAME -> "Invalid User Name!!"
        }

        inputLayout.editText?.apply {
            inputLayout.error = errorMessage
            inputLayout.startIconDrawable?.setTint(context.getColorFromAttr(R.attr.colorError))
            if (shouldRequestFocus) requestFocus()
        }
        return false
    }

    enum class VerificationType {
        BLANK_CHECK,
        EMAIL,
        PASSWORD,
        USER_NAME
    }

    @ColorInt
    fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }

}