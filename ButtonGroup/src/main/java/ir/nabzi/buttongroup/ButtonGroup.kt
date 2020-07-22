package ir.nabzi.buttongroup

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout


class ButtonGroup(context:Context ,attrs: AttributeSet)  : LinearLayout(context , attrs) {
    var selectedOption : String = ""
    lateinit var  entries: Array<CharSequence>
    var selectedBG : Int = 0
    var unSelectedBG : Int = 0
    var selectedTextColor : Int  = 0
    var unSelectedTextColor : Int = 0
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

                    setOptions()
                } finally {
                    recycle()
                }
        }
    }
    private fun setOptions (){
        if(entries == null)
            return
        val btnList = arrayListOf<Button>()
        for (option in entries) {
                val btn = Button(context)
                val layContentParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT)
                layContentParams.setMargins(8,8,8,8)
                layContentParams.weight = 1.0F
                btnList.add(btn)
                btn.run {
                    this.layoutParams = layContentParams
                    text = option
                    setPadding(0, 10, 0, 10)
                    background = context.resources.getDrawable(unSelectedBG)
                    setTextColor(resources.getColor(unSelectedTextColor))
                    this@ButtonGroup.addView(this)
                    setOnClickListener{
                        selectedOption = text.toString()
                        for (btn in btnList) {
                            if (btn != it) {
                                btn.background = context.resources.getDrawable(unSelectedBG)
                                btn.setTextColor(resources.getColor(unSelectedTextColor))
                            }
                            else{
                                it.background = context.resources.getDrawable(selectedBG)
                                btn.setTextColor(resources.getColor(selectedTextColor))
                            }
                        }
                    }
                }
            }
        }
}