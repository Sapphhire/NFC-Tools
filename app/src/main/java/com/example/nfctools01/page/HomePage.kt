// HomePage.kt
package com.example.nfctools01.page

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp

@Composable
fun HomePage(
    textToWrite: String,
    onTextChanged: (String) -> Unit,
    onStartWrite: () -> Unit,
) {
    // 使用 remember 和 mutableStateOf 来管理选中的标签
    val selectedTab = remember { mutableStateOf(1) } // 1表示"写"页面被选中

    // Column用于显示主界面内容
    Column(modifier = Modifier.fillMaxSize()) {

        // 标题部分
        Text(
            text = "NFC Maker v-0.3.2", // 设置标题文本
            style = MaterialTheme.typography.headlineLarge, // 设置标题样式
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 添加适当的间距
            color = MaterialTheme.colorScheme.primary // 设置标题颜色
        )

        // 顶部导航栏
        TabRow(
            selectedTabIndex = selectedTab.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Tab项：读
            Tab(
                selected = selectedTab.value == 0,
                onClick = { selectedTab.value = 0 },
                text = { Text("读") }
            )
            // Tab项：写
            Tab(
                selected = selectedTab.value == 1,
                onClick = { selectedTab.value = 1 },
                text = { Text("写") }
            )
            // Tab项：其他
            Tab(
                selected = selectedTab.value == 2,
                onClick = { selectedTab.value = 2 },
                text = { Text("其他") }
            )
            // Tab项：日志
            Tab(
                selected = selectedTab.value == 3,
                onClick = { selectedTab.value = 3 },
                text = { Text("日志") }
            )
        }

        // 根据选中的导航页显示不同内容
        when (selectedTab.value) {
            0 -> {} // "读"页面，暂时留空
            1 -> { WritePage() }
            2 -> {} // "其他"页面，暂时留空
            3 -> { LogPage() }
        }
    }
}

@Preview
@Composable
fun PreviewHomePage() {
    // 提供适当的空实现函数
    HomePage(
        textToWrite = "Example Text",  // 这里传递示例文本
        onTextChanged = { newText ->
            // 在 Preview 中不需要实际处理，只是示例
            println("Text changed: $newText")
        },
        onStartWrite = {
            // 在 Preview 中不需要实际处理，只是示例
            println("Start writing")
        }
    )
}