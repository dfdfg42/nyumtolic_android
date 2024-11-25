package com.nyumtolic.nyumtolicapp

import android.app.Activity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.nyumtolic.nyumtolicapp.ui.theme.NyumtolicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NyumtolicAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WebViewScreen(url = "https://nyumtolic.com")
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    val context = LocalContext.current
    val activity = context as? Activity
    val webView = remember { WebView(context) }

    BackHandler {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            activity?.finish()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            webView.apply {
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }
                loadUrl(url)
            }
        },
        update = {
            // 필요에 따라 업데이트 로직 추가 가능
        }
    )
}
