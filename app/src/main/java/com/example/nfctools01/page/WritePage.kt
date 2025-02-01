@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.nfctools01.page

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfctools01.page.WritePageWindows.AddCommitWindows
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import com.example.nfctools01.page.WritePageWindows.TextInputWindow

@Composable
fun WritePage() {
    var showAddCommitWindow by remember { mutableStateOf(false) } //这是AddCW窗口
    var showTextInputWindow by remember { mutableStateOf(false) } //这是Text窗口

    val records = remember { mutableStateListOf<Pair<String, String>>() }
    // 这是记录列表窗口，内记录包括内容String和记录类型String

    // 添加记录按钮点击事件
    val onAddRecordClick = {
        // 弹出 AddCommitWindows 窗体
        showAddCommitWindow = true
    }

    // 写入NFC按钮点击事件
    val onWriteClick = {
        // 这里的逻辑可以用来进行 NFC 写入操作，后续再添加
        println("Write to NFC clicked")
    }

    // 页面布局
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // 添加记录按钮
            Button(onClick = onAddRecordClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "添加记录")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 写入NFC按钮
            Button(onClick = onWriteClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "写入NFC")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 空白的记录列表
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                if (records.isEmpty()) {
                    Text(text = "暂无记录", style = MaterialTheme.typography.bodyLarge)
                } else {
                    // 渲染记录列表
                    records.forEach { (type, content) ->
                        Text(text = "$type: $content")
                    }
                }
            }
        }

        // 显示 AddCommitWindows 窗体
        if (showAddCommitWindow) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)), // 半透明背景
                contentAlignment = Alignment.Center // 居中显示
            ) {
                AddCommitWindows(
                    onSelectText = {
                        showAddCommitWindow = false// 关闭当前窗口
                        showTextInputWindow = true // 切换到“写入文本”窗口
                    },
                    onDismiss = {
                        showAddCommitWindow = false// 按到取消则关闭当前窗口
                    }
                )
            }
        }
        // 显示“写入文本”窗口
        if (showTextInputWindow) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                TextInputWindow(
                    onConfirm = { text ->
                        records.add("文本" to text) // 添加记录
                        showTextInputWindow = false
                    },
                    onCancel = {
                        showTextInputWindow = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewWritePage() {
    WritePage()
}