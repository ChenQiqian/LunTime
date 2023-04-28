package com.thss.lunchtime

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thss.lunchtime.signup.SignUpPage
import com.thss.lunchtime.signup.SignUpViewModel

import com.thss.lunchtime.ui.theme.LunchTimeTheme

@RequiresApi(Build.VERSION_CODES.R)
class MainActivity : ComponentActivity() {
    val signupViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.setDecorFitsSystemWindows(false)
//        } else {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        }
        super.onCreate(savedInstanceState)
        setContent {
            LunchTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    LoginPage({ _, _ -> }, { _, _ -> })
                    SignUpPage({},{}, signupViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LunchTimeTheme {
        Greeting("Android")
    }
}