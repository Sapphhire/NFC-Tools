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

import androidx.compose.ui.platform.LocalContext
// Room数据库相关导入
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "text_records")  // 定义表名为 text_records
data class TextRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,  // 主键自动生成
    val content: String  // 文本内容
)

@Dao
interface TextRecordDao {
    // 插入一个文本记录
    @Insert
    suspend fun insertTextRecord(record: TextRecord)

    // 查询所有文本记录
    @Query("SELECT * FROM text_records")
    suspend fun getAllTextRecords(): List<TextRecord>
}

@Database(entities = [TextRecord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun textRecordDao(): TextRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Composable
fun WritePage() {
    val context = LocalContext.current // 获取当前 Context
    val db = AppDatabase.getDatabase(context) // 获取数据库实例
    val textRecordDao = db.textRecordDao()

    var showAddCommitWindow by remember { mutableStateOf(false) } //这是AddCW窗口
    var showTextInputWindow by remember { mutableStateOf(false) } //这是Text窗口
    val records = remember { mutableStateListOf<TextRecord>() }

    // 存储输入的文本内容
    var textInput by remember { mutableStateOf("") }

    // 加载记录
    LaunchedEffect(Unit) {
        val loadedRecords = textRecordDao.getAllTextRecords() // 从数据库加载记录
        records.clear()
        records.addAll(loadedRecords)
    }

    // 监听 textInput 的变化，触发插入数据库
    LaunchedEffect(textInput) {
        if (textInput.isNotEmpty()) {
            val newRecord = TextRecord(content = textInput)
            // 在协程中执行数据库插入
            textRecordDao.insertTextRecord(newRecord)
            records.add(newRecord) // 更新记录列表
            textInput = "" // 插入后清空输入框
        }
    }

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
                    records.forEach { record ->
                        Text(text = "内容: ${record.content}")
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
                        textInput = text // 更新文本内容的状态
                        showTextInputWindow = false // 关闭窗口
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