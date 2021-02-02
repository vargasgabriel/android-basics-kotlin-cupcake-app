package com.example.cupcake.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.BindingMethod
import com.example.cupcake.R

class CheckBoxWithCounterWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val checkBoxView: CheckBox?
    private var checkBoxTheme: Int = 0
    private var checkBoxText: CharSequence? = ""
        set(value) {
            field = value
            checkBoxView?.text = value
        }

    private val btnLeftView: Button
    private var btnLeftTheme: Int = R.style.Widget_CheckBoxWithCounter_ButtonLeft


    private val btnRightView: Button
    private var btnRightTheme: Int = R.style.Widget_CheckBoxWithCounter_ButtonRight

    private val counterView: TextView?
    private val counterTheme: Int = 0
    private var counter: Int = 0
        set(value) {
            field = value
            counterView?.text = field.toString()
        }

    var maxCounter : Int = 1;
    private var maxCounterListener: MaxCounterListener? = null

    init {
        orientation = HORIZONTAL
        isClickable = true

        initializeAttributes(context, attrs)

        checkBoxView = CheckBox(ContextThemeWrapper(context, checkBoxTheme))
        checkBoxView.text = checkBoxText
        addView(checkBoxView)

        btnLeftView = Button(ContextThemeWrapper(context, btnLeftTheme))
        btnLeftView.setOnClickListener {
            if (counter > 0) {
                if (counter == 1) checkBoxView.isChecked = false
                minusCounter()
            }
        }
        addView(btnLeftView)

        counterView = TextView(ContextThemeWrapper(context, counterTheme))
        counterView.text = counter.toString()
        addView(counterView)

        btnRightView = Button(ContextThemeWrapper(context, btnRightTheme))
        btnRightView.setOnClickListener {
            if (counter < maxCounter) {
                if (counter == 0) checkBoxView.isChecked = true
                addCounter()
            } else {
                maxCounterListener?.onMax()
            }
        }
        addView(btnRightView)
    }

    private fun initializeAttributes(
        context: Context,
        attrs: AttributeSet?,
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CheckBoxWithCounterWidget)
        checkBoxTheme = typedArray.getInt(R.styleable.CheckBoxWithCounterWidget_checkBoxTheme,
            android.R.style.Widget_DeviceDefault_CompoundButton_CheckBox)
        checkBoxText = typedArray.getString(R.styleable.CheckBoxWithCounterWidget_checkBoxText)
        counter = typedArray.getInt(R.styleable.CheckBoxWithCounterWidget_counterText, 0)

        typedArray.recycle()
    }

    fun setCheckBoxText(@StringRes value: Int) {
        checkBoxText = context.getString(value)
    }

    private fun addCounter() {
        counter += 1
    }

    private fun minusCounter() {
        counter -= 1
    }

    class MaxCounterListener(val maxListener: () -> Unit) {
        fun onMax() = maxListener()
    }

    fun setOnMaxCounterListener(maxListener: MaxCounterListener) {
        maxCounterListener = maxListener
    }
}