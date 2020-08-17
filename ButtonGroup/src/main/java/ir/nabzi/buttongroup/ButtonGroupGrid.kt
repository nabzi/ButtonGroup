package ir.nabzi.buttongroup
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.Button

import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import androidx.gridlayout.widget.GridLayout


class ButtonGroupGrid(context:Context ,attrs: AttributeSet)  : LinearLayout(context , attrs) {
    var selectedOption : String = ""
    lateinit var  entries: Array<CharSequence>
    var selectedBG : Int = 0
    var unSelectedBG : Int = 0
    var selectedTextColor : Int  = 0
    var unSelectedTextColor : Int = 0
    var colCount = 1
    var rowCount = 1
    @StyleRes
    var buttonTextAppearance : Int = 0
    init{
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ButtonGroup,
            0, 0)
            .apply {
                try {
                    entries  = getTextArray(R.styleable.ButtonGroup_android_entries)
                    selectedBG  = getResourceId(R.styleable.ButtonGroup_selected_button_bg , 0)
                    unSelectedBG  = getResourceId(R.styleable.ButtonGroup_unselected_button_bg , 0)
                    selectedTextColor  = getResourceId(R.styleable.ButtonGroup_selected_button_text_color , 0)
                    unSelectedTextColor  = getResourceId(R.styleable.ButtonGroup_unselected_button_text_color , 0)
                    buttonTextAppearance =  getResourceId(R.styleable.ButtonGroup_android_textAppearance , 0)
                    colCount = getInt(R.styleable.ButtonGroup_col_count, 1)
                    rowCount = getInt(R.styleable.ButtonGroup_row_count, 1)
                    setOptions()
                } finally {
                    recycle()
                }
            }
    }
    private fun setOptions (){
        if(entries == null)
            return
        this.orientation = VERTICAL
        val btnList = arrayListOf<Button>()
        for ( row in 0 until rowCount) {
            var linearLayout = LinearLayout(context)
            val layContentParams = LinearLayout.LayoutParams(
                FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layContentParams.weight = 1.0F
            linearLayout.layoutParams  = layContentParams
            for ( option in entries.toList().slice(IntRange(row * colCount  , row  *colCount + colCount - 1))) {
                val btn = Button(context)
                val layContentParams = LinearLayout.LayoutParams(
                    FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layContentParams.gravity = Gravity.FILL_HORIZONTAL
                layContentParams.setMargins(8, 8, 8, 8)
                layContentParams.weight = 1.0F
                btnList.add(btn)

                btn.run {
                    this.layoutParams = layContentParams
                    text = option
                    setPadding(0, 10, 0, 10)
                    background = context.resources.getDrawable(unSelectedBG)
                    setTextColor(resources.getColor(unSelectedTextColor))
                    TextViewCompat.setTextAppearance(this, buttonTextAppearance)
                    linearLayout.addView(this)
                    setOnClickListener {
                        selectedOption = text.toString()
                        for (btn in btnList) {
                            if (btn != it) {
                                btn.background = context.resources.getDrawable(unSelectedBG)
                                btn.setTextColor(resources.getColor(unSelectedTextColor))
                            } else {
                                it.background = context.resources.getDrawable(selectedBG)
                                btn.setTextColor(resources.getColor(selectedTextColor))
                            }
                        }
                    }
                }

            }
            linearLayout.orientation = HORIZONTAL
            this.addView(linearLayout)
        }
    }
}