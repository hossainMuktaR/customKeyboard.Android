package com.hossain.customkeyboardappandroid.keyboard.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import com.hossain.customkeyboardappandroid.keyboard.KeyboardService

class KeyboardViewCompose(context: Context): AbstractComposeView(context) {
    @Composable
    override fun Content() {
        val ic = (LocalContext.current as KeyboardService).currentInputConnection

        KeyboardLayout { btn ->
            if (btn.length == 1){
                ic.commitText(btn, btn.length)
            }
            if (btn == "backspace") {
                ic.deleteSurroundingText(1, 0)
            }
            if (btn == "space") {
                ic.commitText(" ", 1)
            }
        }
    }
}