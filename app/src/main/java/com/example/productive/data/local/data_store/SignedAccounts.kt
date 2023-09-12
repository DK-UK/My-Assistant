package com.example.productive.data.local.data_store

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun getSignedInAccounts(context: Context,
                        onShowDialog: () -> Unit,
                        showDialog : Boolean,
                        onEmailSelected : (String) -> Unit) {
    val accountManager = context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
    val accounts: Array<Account> = accountManager.getAccountsByType("com.google")
    accounts.forEach {
        Log.e("Dhaval", "getSignedInAccounts: ${it}")
    }

    if (showDialog) {
        showAccountDialog(
            accounts = accounts.toList(),
            onClickedAccount = { email ->
                onEmailSelected.invoke(email)
            },
            onShowDialog
        )
    }
}

@Composable
fun showAccountDialog(
    accounts: List<Account>,
    onClickedAccount: (String) -> Unit,
    onShowDialog : () -> Unit
) {

    Dialog(onDismissRequest = onShowDialog,
        properties = DialogProperties()
    ) {
        Column() {
            Row() {
                Text(text = "Accounts", style = MaterialTheme.typography.bodyLarge)
            }

            val acc = listOf<String>("dk@gmai.com", "dhaval@gmail.com", "dhavalkeni@gmail.com")

            LazyColumn() {
                items(acc) {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                onClickedAccount.invoke(it)
                                onShowDialog.invoke()
                            })
                }
            }
        }
    }
}