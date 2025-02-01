package com.example.nfctools01.page.WritePageWindows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextInputWindow(
    onConfirm: (String) -> Unit, // 明确指定参数类型
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .width(300.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "输入文本", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("文本内容") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedButton(onClick = onCancel) {
                    Text(text = "取消")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onConfirm(text) }) {
                    Text(text = "确认")
                }
            }
        }
    }
}