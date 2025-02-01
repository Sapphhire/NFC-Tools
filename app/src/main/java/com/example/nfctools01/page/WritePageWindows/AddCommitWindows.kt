package com.example.nfctools01.page.WritePageWindows

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CardDefaults

@Composable
fun AddCommitWindows(
    onSelectText: () -> Unit, // 文本按钮点击事件
    onDismiss: () -> Unit // 关闭窗口事件
) {
    // 使用 Box 来让窗体居中显示
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize(align = Alignment.Center) // 确保窗体在屏幕中间
    ) {
        // 使用 Card 来加上背景、边框和圆角
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // 给 Card 外部添加间距
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(12.dp),
            // elevation = CardDefaults.elevation(8.dp), // 使用 CardDefaults.elevation
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF)) // 设置背景颜色
        ) {
            // 内部 Column 排版
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                // 窗体标题
                Text(text = "选择记录类型", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                // 文本按钮
                Button(
                    onClick = onSelectText,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text("文本")
                }

                // 你可以添加其他记录类型的按钮
                Button(
                    onClick = { /* 处理文件类型 */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text("文件")
                }

                Button(
                    onClick = { /* 处理 URL 类型 */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text("URL")
                }

                Button(
                    onClick = { /* 处理 Wi-Fi 类型 */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text("Wi-Fi")
                }

                // 取消按钮
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("取消")
                }
            }
        }
    }
}



@Preview
@Composable
fun PreviewAddCommitWindows() {
    AddCommitWindows(onSelectText = { println("Text selected") }, onDismiss = { println("Dismissed") })
}