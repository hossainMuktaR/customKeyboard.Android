package com.hossain.customkeyboardappandroid.keyboard.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hossain.customkeyboardappandroid.ui.theme.CustomKeyboardAppAndroidTheme
import com.hossain.customkeyboardappandroid.ui.theme.keyBackgroundDark
import com.hossain.customkeyboardappandroid.ui.theme.keyBackgroundLight
import com.hossain.customkeyboardappandroid.ui.theme.keyboardBackgroundDark
import com.hossain.customkeyboardappandroid.ui.theme.keyboardBackgroundLight
import kotlin.collections.forEach
import kotlin.text.forEach
import kotlin.text.uppercase

@Composable
fun KeyboardLayout(onKeyAction: (String) -> Unit) {
    var isShifted by remember { mutableStateOf(false) }
    val letters = listOf(
        "qwertyuiop",
        "asdfghjkl",
        "zxcvbnm"
    )

    Column(
        modifier = Modifier
            .background( if(isSystemInDarkTheme()) keyboardBackgroundDark else keyboardBackgroundLight)
            .fillMaxWidth()
    ) {
        letters.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),

            ) {
                row.forEach { char ->
                    if (char == 'z') {
                        KeyButton(text = "⇧", onClick = { isShifted = !isShifted },
                            modifier = Modifier.weight(2f))
                    }
                    if(char == 'a') {
                        Spacer(modifier = Modifier
                            .weight(0.5f))
                    }
                    KeyButton(
                        text = if (isShifted) char.uppercase() else char.toString(),
                        onClick = {
                            onKeyAction(if (isShifted)  char.uppercase().toString() else char.toString())
                                  },
                        modifier = Modifier.weight(1f)
                    )
                    if(char == 'l') {
                        Spacer(modifier = Modifier
                            .weight(0.5f))
                    }
                    if (char == 'm') {
                        KeyButton(text = "⌫", onClick = { onKeyAction("backspace") },
                            modifier = Modifier.weight(2f))
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        ) {
            KeyButton(text = "?123", onClick = { onKeyAction("?123")},
                modifier = Modifier.weight(2f))
            KeyButton(text = ",", onClick = { onKeyAction(",") },
                modifier = Modifier.weight(1f))
            KeyButton(text = "Space", onClick = { onKeyAction("space") },
                modifier = Modifier.weight(4f))
            KeyButton(text = ".", onClick = { onKeyAction(".") },
                modifier = Modifier.weight(1f))
            KeyButton(text = "Enter", onClick = { onKeyAction("enter") },
                modifier = Modifier.weight(2f))
        }
    }
}

@Composable
fun KeyButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {

    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = if (isSystemInDarkTheme()) keyBackgroundDark else keyBackgroundLight,
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp, 1.dp),
        modifier = modifier
        .height(48.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardLayoutPreview() {
    CustomKeyboardAppAndroidTheme {
        KeyboardLayout {  }
    }
}
