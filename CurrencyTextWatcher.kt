import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat

class CurrencyTextWatcher(val textInputEditText: TextInputEditText) : TextWatcher {

    var finalValue = 0.00
    val df = DecimalFormat("0.00")

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        textInputEditText.text?.length?.let {
            textInputEditText.requestFocus(it)
            textInputEditText.setSelection(it)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
        textInputEditText.removeTextChangedListener(this)
        val numberFormatted = df.format(finalValue)
        textInputEditText.setText(numberFormatted)
        textInputEditText.text?.length?.let {
            textInputEditText.setSelection(it)
        }
        textInputEditText.addTextChangedListener(this)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textInputEditText.text?.length?.let {
            textInputEditText.requestFocus(it)
            textInputEditText.setSelection(it)
        }

        s?.toString()?.let {
            val nextAmount = it.toDouble()
            val previousValue = finalValue
            finalValue = when {
                finalValue>nextAmount -> nextAmount/10
                finalValue<nextAmount -> nextAmount*10
                before>0 -> nextAmount/10
                finalValue==nextAmount -> nextAmount*10
                else -> 0.00
            }
        }

    }
}
