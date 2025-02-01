//@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.nfctools01.page

import androidx.compose.foundation.clickable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

// 每条日志的模型
data class LogEntry(
    val version: String,
    val timestamp: String,
    val content: String
)

@Composable
fun LogPage() {

    val logs = listOf(
        LogEntry("v-0.4", "2025-02-01 15:50",
            "1. 优化写入逻辑，点击写入按钮时增加交互弹窗\n" +
                    "2. 在添加记录的'选择记录类型'页面增加了UI优化\n" +
                    "3. 输入文本，信息可增添之列表\n"),

        LogEntry("v-0.3", "2025-01-25 15:21",
            "1. 把'任务'导航页改成'日志'导航页，新建并封装LogPage.kt。\n" +
                    "2. Debug并优化'日志'页面的逻辑，功能基本实现，UI完成初步优化。"),

        LogEntry("v-0.2", "2025-01-25 13:54",
            "1. 新建导航页，分块'读'、'写'、'其它'、'任务'四个板块分块。\n" +
                    "2. 新建WritePage.kt,封装了'写'界面的功能。"),

        LogEntry("v-0.1", "2025-01-25 13:34",
            "1. 实现最基础的功能，完成NFC写入文本的功能。"),
    )

    // 使用remember初始化expandedState列表
    val expandedState = remember { mutableStateListOf(*Array(logs.size) { false }) }

    // LazyColumn用于垂直排列日志条目
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // 使用items遍历日志列表
        items(logs) { log ->
            // 获取当前日志的索引
            val index = logs.indexOf(log)

            // 渲染每一条日志
            LogItem(
                log = log,
                isExpanded = expandedState[index],  // 传递当前日志是否展开的状态
                onClick = { expandedState[index] = !expandedState[index] }  // 切换展开/收缩状态
            )
        }
    }
}

@Composable
fun LogItem(log: LogEntry, isExpanded: Boolean, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 日志标题，添加背景框体并填充深色
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer, // 使用深色背景
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() }, // 点击标题展开或收缩
            shape = MaterialTheme.shapes.medium // 设置圆角效果
        ) {
            // 使用 Row 来确保版本号和更新时间对齐
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // 添加适当的内边距
                horizontalArrangement = Arrangement.SpaceBetween // 确保左右对齐
            ) {
                // 版本号显示
                Text(
                    text = "Version: ${log.version}",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                )

                // 更新时间显示
                Text(
                    text = "Updated at: ${log.timestamp}",
                    style = TextStyle(fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSecondaryContainer)
                )
            }
        }

        // 展开/收缩日志内容
        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = log.content)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogPage() {
    LogPage() // 直接调用 LogPage，不传递 logs
}