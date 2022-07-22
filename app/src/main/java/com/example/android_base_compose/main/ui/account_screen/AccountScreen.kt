package com.example.android_base_compose.main.ui.account_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_base_compose.R
import com.example.android_base_compose.base.ui.common.modifier16Padding
import com.example.android_base_compose.base.ui.common.modifier8Padding
import com.example.android_base_compose.main.theme.Dimen

@Composable
fun AccountScreen() {
    val accountViewModel: AccountViewModel = hiltViewModel()
    val name by remember { mutableStateOf("Dat") }
    val email by remember { mutableStateOf("datnt@yopaz.vn") }
    Column(
            modifier = modifier16Padding
                    .padding(top = Dimen.Space36),
            horizontalAlignment = CenterHorizontally,
    ) {
        Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = modifier16Padding
        )
        Column(Modifier.padding(Dimen.Space16)) {
            Text(text = "name: $name", fontSize = Dimen.Text20, modifier = modifier8Padding.padding(bottom = Dimen.Space8))
            Text(text = "Email: $email", fontSize = Dimen.Text20, modifier = modifier8Padding.padding(bottom = Dimen.Space16))
            Button(
                    onClick = { accountViewModel.logOut() },
                    modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.account_logout_button))
            }
        }
    }
}
