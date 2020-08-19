package ir.nabzi.buttongroup

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat


class ButtonGroupGrid(context:Context ,attrs: AttributeSet)  : LinearLayout(context , attrs) {
    var selectedOption : String = ""
    lateinit var  entries: Array<CharSequence>
    var selectedBG : Int = 0
    var unSelectedBG : Int = 0
    var selectedTextColor : Int  = 0
    var unSelectedTextColor : Int = 0
    var colCount = 1
    var rowCount = 1
    var drawableResources = arrayListOf<Int>()
    var drawableResourcesSelected = arrayListOf<Int>()
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
                    var arrayResourceId: Int = getResourceId(R.styleable.ButtonGroup_icons, 0)
                    if (arrayResourceId != 0) {
                        val resourceArray =
                            resources.obtainTypedArray(arrayResourceId)
                        for (i in 0 until resourceArray.length()) {
                            val resourceId = resourceArray.getResourceId(i, 0)
                            drawableResources.add(resourceId)
                        }
                        resourceArray.recycle()
                    }
                    var arrayResourceId2: Int = getResourceId(R.styleable.ButtonGroup_selected_icons, 0)
                    if (arrayResourceId2 != 0) {
                        val resourceArray =
                            resources.obtainTypedArray(arrayResourceId2)
                        for (i in 0 until resourceArray.length()) {
                            val resourceId = resourceArray.getResourceId(i, 0)
                            drawableResourcesSelected.add(resourceId)
                        }
                        resourceArray.recycle()
                    }
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
            val sublist = entries.toList().slice(IntRange(row * colCount  , row  *colCount + colCount - 1))
            for ( (index,option) in sublist.withIndex()) {
                val btn = Button(context)
                val layContentParams = LinearLayout.LayoutParams(
                    FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                layContentParams.gravity = Gravity.FILL_HORIZONTAL
                layContentParams.setMargins(2, 2, 2, 2)
                layContentParams.weight = 1.0F
                btnList.add(btn)

                btn.run {
                    var icon : Int? = null
                    var iconSelected : Int? = null
                    val btnIndex = row * colCount + index
                    if(drawableResources.isNotEmpty() && btnIndex < drawableResources.size ) {
                        icon = drawableResources[btnIndex]
                    }
                    iconSelected = if(drawableResourcesSelected.isNotEmpty() &&
                        btnIndex < drawableResourcesSelected.size ) {
                        drawableResourcesSelected[btnIndex]
                    }else {
                        icon
                    }
                    setBackgroundResource(unSelectedBG)
                    icon?.let{
                        btn.setCompoundDrawablesWithIntrinsicBounds (0,it,0,0)
                    }
                    this.layoutParams = layContentParams
                    text = option
                    setPadding(2, 5, 2, 5)
                    background = context.resources.getDrawable(unSelectedBG)
                    setTextColor(ContextCompat.getColor(context,unSelectedTextColor))
                    TextViewCompat.setTextAppearance(this, buttonTextAppearance)
                    linearLayout.addView(this)
                    setOnClickListener {
                        selectedOption = text.toString()
                        for ((index,btn) in btnList.withIndex()) {
                            if (btn != it) {
                                btn.background = context.resources.getDrawable(unSelectedBG)
                                btn.setCompoundDrawablesWithIntrinsicBounds (0,drawableResources[index],0,0)
                                btn.setTextColor(resources.getColor(unSelectedTextColor))
                            } else {
                                it.background = context.resources.getDrawable(selectedBG)
                                iconSelected?.let {
                                    btn.setCompoundDrawablesWithIntrinsicBounds (0,it,0,0)
                                }
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