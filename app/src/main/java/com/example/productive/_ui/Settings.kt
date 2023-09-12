package com.example.productive._ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.productive.Utility.Utility
import com.example.productive._ui.viewModels.TasksViewModel
import com.example.productive.data.local.data_store.EmailPreference
import com.example.productive.data.local.data_store.getSignedInAccounts
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    context: Context,
    tasksViewModel: TasksViewModel
) {
    Box(
        modifier = Modifier.zIndex(1f)
    ) {

        var emailPreference = EmailPreference.getInstance(context)

        var showAccountsDialog by remember {
            mutableStateOf(false)
        }
        var syncedAccount by remember (key1 = emailPreference.getEmailFromPref()) {
            mutableStateOf(emailPreference.getEmailFromPref())
        }

        if (showAccountsDialog) {
            if (Utility.isConnectivityAvailable(context)) {
                getSignedInAccounts(
                    context = context, onShowDialog = {
                        showAccountsDialog = !showAccountsDialog
                    }, showDialog = showAccountsDialog,
                    onEmailSelected = {email ->
                        emailPreference.saveEmailToPref(email)
                        tasksViewModel.getAllTasksFromRemote()
                        tasksViewModel.updateUserEmail(email)
                    }
                )
            }
            else{
                LaunchedEffect(key1 = showAccountsDialog){
                    Toast.makeText(context, "No Internet Connection!!", Toast.LENGTH_LONG).show()
                }
                showAccountsDialog = false
            }
        }

        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column() {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = "Sync to cloud",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.clickable(enabled = syncedAccount.isEmpty()) {
                            showAccountsDialog = true
                        },)
                    if (syncedAccount.isNotEmpty()) {
                        Text(
                            text = syncedAccount,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}