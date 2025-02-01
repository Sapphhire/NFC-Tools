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

@Composable
fun WritePage() {
    var showAddCommitWindow by remember { mutableStateOf(false) }


    // 存储记录的列表（初始为空）
    var records by remember { mutableStateOf<List<String>>(emptyList()) }

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
                    records.forEach {
                        Text(text = it)
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
                        // 选择文本时，可以跳转到写入文本页面
                        println("Text selected")
                        // 关闭当前 AddCommitWindows
                        showAddCommitWindow = false
                    },
                    onDismiss = {
                        // 取消窗口，关闭 AddCommitWindows
                        showAddCommitWindow = false
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