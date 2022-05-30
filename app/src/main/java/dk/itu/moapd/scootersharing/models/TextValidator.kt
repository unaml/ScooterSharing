package dk.itu.moapd.scootersharing.models

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import dk.itu.moapd.scootersharing.R
import java.util.regex.Pattern

/**
 * A class to validate the content from `EditText` components.
 */
class TextValid(private val view: View) : TextWatcher {

    /**
     * A boolean parameter to define the name validation status.
     */
    var isValidName = false
        private set

    /**
     * A boolean parameter to define the phone validation status.
     */
    var isValidPhone = false
        private set


    /**
     * A set of static attributes used in this activity class.
     */
    companion object {
        // This pattern works with Danish names.
        private val NAME_PATTERN = Pattern.compile(
            "^[A-Z]+[a-z]{2,}(?: [a-zA-Z]+)?(?: [a-zA-Z]+)?\$"
        )



        /**
         * This method validates the current data from the name `EditText` component.
         *
         * @param name The user's name.
         *
         * @return A boolean value with the validation result.
         */
        fun isValidName(name: CharSequence): Boolean {
            return NAME_PATTERN.matcher(name).matches()
        }

        /**
         * This method validates the current data from the phone `EditText` component.
         *
         * @param phone The user's phone number
         *
         * @return A boolean value with the validation result.
         */
        fun isValidPhone(phone: Editable?): Boolean {
            return android.util.Patterns.PHONE.matcher(phone.toString()).matches()
        }
    }

    /**
     * This method is called to notify you that, within <code>s</code>, the <code>count</code>
     * characters beginning at <code>start</code> are about to be replaced by new text with length
     * <code>after</code>. It is an error to attempt to make changes to <code>s</code> from this
     * callback.
     *
     * @param s The characters from the UI View component.
     * @param start The characters begins at the `start` index.
     * @param count The number of characters to be replace.
     * @param after The index where the new characters will be replaced.
     */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    /**
     * This method is called to notify you that, within <code>s</code>, the <code>count</code>
     * characters beginning at <code>start</code> have just replaced old text that had length
     * <code>before</code>. It is an error to attempt to make changes to <code>s</code> from this
     * callback.
     *
     * @param s The characters from the UI View component.
     * @param start The characters begins at the `start` index.
     * @param before The index where the new characters will be replaced.
     * @param count The number of characters to be replace.
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    /**
     * This method is called to notify you that, somewhere within <code>s</code>, the text has been
     * changed. It is legitimate to make further changes to <code>s</code> from this callback, but
     * be careful not to get yourself into an infinite loop, because any changes you make will cause
     * this method to be called again recursively.
     *
     * (You are not told where the change took place because other `afterTextChanged()` methods may
     * already have made other changes and invalidated the offsets. But if you need to know here,
     * you can use `Spannable#setSpan()` in `onTextChanged()` to mark your place and then look up
     * from here where the span ended up.
     *
     * @param s The UI View component.
     */
    override fun afterTextChanged(s: Editable?) {
        when (view.id) {
            R.id.firstName ->
                isValidName = isValidName(s.toString())
            R.id.lastName ->
                isValidName = isValidName(s.toString())
            R.id.phone ->
                isValidPhone = isValidPhone(s)
        }
    }

}