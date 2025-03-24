package com.hossain.customkeyboardappandroid

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.hossain.customkeyboardappandroid.ui.theme.CustomKeyboardAppAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomKeyboardAppAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val ctx = LocalContext.current
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Text("Welcome Our Keyboard")
                        Button(
                            onClick = {
                                ctx.startActivity(
                                    Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
                                )
                            },
                            content = {
                                Text("Enable IME")
                            }
                        )
                        Button(
                            onClick = {
                                val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                                inputMethodManager.showInputMethodPicker()
                            },
                            content = {
                                Text("Select IME")
                            }
                        )
                        Text("Try your keyboard")
                        val (textValue, setTextValue) =  remember{ mutableStateOf("") }
                        TextField(
                            value = textValue,
                            onValueChange = { setTextValue(it) },
                            placeholder = {
                                Text("Type Here")
                            }
                        )
                    }

                }
            }
        }
    }
}
