package com.example.android_base_compose.main.ui.login_screen

import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_base_compose.R
import com.example.android_base_compose.base.ui.common.LoadingScreen
import com.example.android_base_compose.base.ui.common.clearFocus
import com.example.android_base_compose.base.ui.common.modifier16Padding
import com.example.android_base_compose.base.ui.common.modifierFullSize
import com.example.android_base_compose.main.theme.Dimen
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val isShowLoading by loginViewModel.isShowLoadingState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(true) {
        loginViewModel.loginErrorMessageState.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Box(modifier = Modifier.clearFocus(focusManager)) {
        LoadingScreen(isShowLoading = isShowLoading)
        Column(
            modifier = modifierFullSize, verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = modifier16Padding,
                fontSize = Dimen.Text20,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.login_screen_title),
            )
            LoginTextField(
                modifier = modifier16Padding,
                text = email,
                setText = { loginViewModel.setEmail(it) },
                focusManager = focusManager,
                label = stringResource(id = R.string.login_screen_email)
            )
            LoginTextField(
                modifier = modifier16Padding,
                text = password,
                setText = { loginViewModel.setPassword(it) },
                focusManager = focusManager,
                label = stringResource(id = R.string.login_screen_password),
                isPasswordFiled = true
            )
            Button(modifier = modifier16Padding.focusable(), onClick = {
                focusManager.clearFocus()
                coroutineScope.launch {
                    loginViewModel.login(context)
                }
            }) {
                Text(text = stringResource(id = R.string.login_screen_submit_button))
            }
        }
    }
}

@Composable
fun LoginTextField(
    modifier: Modifier,
    text: String,
    setText: (String) -> Unit,
    focusManager: FocusManager,
    label: String? = null,
    isPasswordFiled: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        singleLine = true,
        label = { label?.let { Text(text = label) } },
        onValueChange = { setText(it) },
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = if (isPasswordFiled) KeyboardType.Password else KeyboardType.Email
        ),
        visualTransformation = if (isPasswordFiled) PasswordVisualTransformation() else VisualTransformation.None
    )
}
