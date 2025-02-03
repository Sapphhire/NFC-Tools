package com.example.nfctools01.utils

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcA
import android.widget.Toast

// NFC 写入的回调接口
interface NfcWriteCallback {
    fun onSuccess()
    fun onFailure(error: String)
}

// NFC 写入函数
fun writeToNFC(
    content: String,
    context: Context,
    callback: NfcWriteCallback
) {
    val nfcAdapter = NfcAdapter.getDefaultAdapter(context)

    // 检查设备是否支持 NFC
    if (nfcAdapter == null) {
        callback.onFailure("设备不支持NFC")
        return
    }

    // 检查 NFC 是否已启用
    if (!nfcAdapter.isEnabled) {
        callback.onFailure("请启用NFC功能")
        return
    }

    // 确保上下文是 Activity
    if (context is Activity) {
        nfcAdapter.enableReaderMode(
            context,
            { tag ->
                val nfcA = NfcA.get(tag)
                try {
                    nfcA.connect()

                    // 将内容转换为字节数组
                    val data = content.toByteArray()

                    // 写入 NFC 标签
                    nfcA.transceive(data)
                    callback.onSuccess() // 写入成功
                } catch (e: Exception) {
                    callback.onFailure("写入失败: ${e.message}") // 写入失败
                } finally {
                    nfcA.close()
                }
            },
            NfcAdapter.FLAG_READER_NFC_A, // 使用 NFC-A 技术
            null
        )
    } else {
        callback.onFailure("NFC写入功能需要Activity上下文")
    }
}