package com.hossain.customkeyboardappandroid

import android.inputmethodservice.InputMethodService
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageButton

class FastInputIME : InputMethodService() {
    private var keyboardView: View? = null
    private var currentLayout: Int = R.layout.keyboard_layout
    private var isSymbolsLayoutVisible = false
    private var isCapsLock = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(currentLayout, null)
        setupKeyListeners(keyboardView!!)
        return keyboardView!!
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        updateKeyLabels()
    }

    private fun setupKeyListeners(keyboardView: View) {
        setupLetterKeys(keyboardView)
        setupSpecialKeys(keyboardView)
        setupSymbolKeys(keyboardView)
    }

    private fun setupLetterKeys(keyboardView: View) {
        val letterIds = if (!isSymbolsLayoutVisible) {
            listOf(
                R.id.key_q, R.id.key_w, R.id.key_e, R.id.key_r, R.id.key_t,
                R.id.key_y, R.id.key_u, R.id.key_i, R.id.key_o, R.id.key_p,
                R.id.key_a, R.id.key_s, R.id.key_d, R.id.key_f, R.id.key_g,
                R.id.key_h, R.id.key_j, R.id.key_k, R.id.key_l,
                R.id.key_z, R.id.key_x, R.id.key_c, R.id.key_v, R.id.key_b,
                R.id.key_n, R.id.key_m
            )
        } else {
            listOf(
                R.id.key_1, R.id.key_2, R.id.key_3, R.id.key_4, R.id.key_5,
                R.id.key_6, R.id.key_7, R.id.key_8, R.id.key_9, R.id.key_0,
                R.id.key_at, R.id.key_hash, R.id.key_dollar, R.id.key_percent,
                R.id.key_and, R.id.key_dash, R.id.key_plus, R.id.key_open_paren,
                R.id.key_close_paren, R.id.key_slash, R.id.key_asterisk,
                R.id.key_quote, R.id.key_single_quote, R.id.key_colon,
                R.id.key_semicolon, R.id.key_exclamation, R.id.key_question,
                R.id.key_comma, R.id.key_period
            )
        }

        letterIds.forEach { id ->
            keyboardView.findViewById<Button>(id)?.setOnClickListener { view ->
                val text = (view as Button).text.toString()
                if (isCapsLock) {
                    commitText(text.uppercase())
                } else {
                    commitText(text)
                }
            }
        }
    }

    private fun setupSpecialKeys(keyboardView: View) {
        // Backspace key
        keyboardView.findViewById<ImageButton>(R.id.key_backspace)?.apply {
            setOnClickListener {
                handleBackspace()
            }
            setOnLongClickListener {
                isLongPressing = true
                handler.post(object : Runnable {
                    override fun run() {
                        if (isLongPressing) {
                            handleBackspace()
                            handler.postDelayed(this, 50)
                        }
                    }
                })
                true
            }
            setOnTouchListener { _, event ->
                when (event.action) {
                    android.view.MotionEvent.ACTION_UP, 
                    android.view.MotionEvent.ACTION_CANCEL -> {
                        isLongPressing = false
                    }
                }
                false
            }
        }

        // Shift key
        keyboardView.findViewById<ImageButton>(R.id.key_shift)?.setOnClickListener {
            handleShift()
        }

        // Space key
        keyboardView.findViewById<Button>(R.id.key_space)?.setOnClickListener {
            commitText(" ")
        }

        // Enter key
        keyboardView.findViewById<Button>(R.id.key_enter)?.setOnClickListener {
            handleEnter()
        }

        // Switch layout buttons
        keyboardView.findViewById<Button>(R.id.key_symbols)?.setOnClickListener {
            switchLayout()
        }
        keyboardView.findViewById<Button>(R.id.key_abc)?.setOnClickListener {
            switchLayout()
        }
    }

    private fun setupSymbolKeys(keyboardView: View) {
        keyboardView.findViewById<Button>(R.id.key_period)?.setOnClickListener {
            commitText(".")
        }
        keyboardView.findViewById<Button>(R.id.key_comma)?.setOnClickListener {
            commitText(",")
        }
    }

    private fun commitText(text: String) {
        currentInputConnection?.commitText(text, 1)
    }

    private fun handleBackspace() {
        currentInputConnection?.deleteSurroundingText(1, 0)
    }

    private fun handleShift() {
        isCapsLock = !isCapsLock
        updateKeyLabels()
    }

    private fun handleEnter() {
        currentInputConnection?.commitText("\n", 1)
    }

    private fun updateKeyLabels() {
        val letters = arrayOf(
            "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
            "a", "s", "d", "f", "g", "h", "j", "k", "l",
            "z", "x", "c", "v", "b", "n", "m"
        )
        
        letters.forEach { letter ->
            val resId = resources.getIdentifier("key_$letter", "id", packageName)
            keyboardView?.findViewById<Button>(resId)?.apply {
                text = if (isCapsLock) letter.uppercase() else letter
            }
        }
    }

    private fun switchLayout() {
        isSymbolsLayoutVisible = !isSymbolsLayoutVisible
        currentLayout = if (isSymbolsLayoutVisible) {
            R.layout.keyboard_layout_symbols
        } else {
            R.layout.keyboard_layout
        }
        keyboardView = layoutInflater.inflate(currentLayout, null)
        setupKeyListeners(keyboardView!!)
        setInputView(keyboardView)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        private var isLongPressing = false
    }
} 