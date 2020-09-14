package ir.nabzi.buttons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // buttonGroup.setEntriesArray( arrayOf("111" ,"222" , "333" , "444" , "555"))
        buttonGroup.setSelectedButton(2)
        //buttonGroupGrid.setEntriesArray(arrayOf("a" , "b" , "c" , "d"))
        buttonGroupGrid.setSelectedButton(2)
    }
}