package ir.nabzi.buttongroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat


class ButtonGroup(context:Context ,attrs: AttributeSet)  : LinearLayout(context , attrs) {
    val btnList = arrayListOf<Button>()
    var selectedOption : String = ""
    var entries: Array<CharSequence>? = null
    var selectedBG : Int = 0
    var unSelectedBG : Int = 0
    var selectedTextColor : Int  = 0
    var unSelectedTextColor : Int = 0
    @StyleRes
    var buttonTextAppearance : Int = 0
    init{
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ButtonGroup,
            0, 0)
            .apply {
                try {
                    getTextArray(R.styleable.ButtonGroup_android_entries)?.let{ entries  =  it }
                    selectedBG  = getResourceId(R.styleable.ButtonGroup_selected_button_bg , 0)
                    unSelectedBG  = getResourceId(R.styleable.ButtonGroup_unselected_button_bg , 0)
                    selectedTextColor  = getResourceId(R.styleable.ButtonGroup_selected_button_text_color , 0)
                    unSelectedTextColor  = getResourceId(R.styleable.ButtonGroup_unselected_button_text_color , 0)
                    buttonTextAppearance =  getResourceId(R.styleable.ButtonGroup_android_textAppearance , 0)
                    setOptions()
                } finally {
                    recycle()
                }
        }
    }
    fun setEntriesArray (entriesArray : Array<CharSequence>)
    {
        entries = entriesArray
        setOptions()
    }
    fun setSelectedButton(index : Int){
        if(btnList.size == 0) return
        var selectedButton = btnList[index]
        for (btn in btnList) {
            if (btn == selectedButton) {
                selectButton(btn)
            }
            else{
                deselectButton(btn)
            }
        }
    }
    private fun setOptions (){
        if(entries == null)
            return
        for (option in entries!!) {
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
                    TextViewCompat.setTextAppearance(this,buttonTextAppearance)
                    this@ButtonGroup.addView(this)
                    setOnClickListener{selectedButton ->
                        selectedOption = text.toString()
                        for (btn in btnList) {
                            if (btn == selectedButton) {
                                selectButton(btn)
                            }
                            else{
                                deselectButton(btn)
                            }
                        }
                    }
                }
            }
        }

    private fun deselectButton(btn: Button) {
        btn.background = context.resources.getDrawable(unSelectedBG)
        btn.setTextColor(resources.getColor(unSelectedTextColor))
    }

    private fun selectButton(btn: Button) {
        btn.background = context.resources.getDrawable(selectedBG)
        btn.setTextColor(resources.getColor(selectedTextColor))
    }
}