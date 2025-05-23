package org.example.cashitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import org.example.cashitest.presentation.transactions_list.TransactionsListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)
        setContent {
            MaterialTheme {
                Navigator(TransactionsListScreen)
            }
        }
    }
}