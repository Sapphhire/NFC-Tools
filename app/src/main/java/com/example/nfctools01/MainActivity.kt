// MainActivity.kt
package com.example.nfctools01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.example.nfctools01.page.HomePage // 导入 HomePage
import com.example.nfctools01.ui.theme.NFCTools01Theme
import androidx.compose.ui.Modifier // 导入 Modifier
import androidx.compose.foundation.layout.fillMaxSize // 导入 fillMaxSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用 setContent 来设置内容
        setContent {
            NFCApp()
        }
    }
}

@Composable
fun NFCApp() {
    // 使用 remember 来管理 textToWrite 的状态
    var textToWrite by remember { mutableStateOf("") }

    // 定义写入 NFC 的操作
    val onStartWrite = {
        println("Writing to NFC: $textToWrite")
    }

    // 处理文本更改
    val onTextChanged: (String) -> Unit = { newText ->
        textToWrite = newText
    }

    // 使用 HomePage 来构建 UI
    NFCTools01Theme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            HomePage(
                textToWrite = textToWrite,
                onTextChanged = onTextChanged,
                onStartWrite = onStartWrite
            )
        }
    }
}