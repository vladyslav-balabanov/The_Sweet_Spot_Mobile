package com.example.thesweetspotmobile.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.thesweetspotmobile.R
import com.example.thesweetspotmobile.Utils.GoogleAuthResultContract
import com.example.thesweetspotmobile.ui.components.navigation.Screens
import com.example.thesweetspotmobile.ui.theme.Biege80
import com.example.thesweetspotmobile.ui.theme.Brown40
import com.example.thesweetspotmobile.ui.theme.Brown80
import com.example.thesweetspotmobile.ui.theme.BrownOrange40
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun Auth(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var showPassword by remember { mutableStateOf(false) }
    var isLogin by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val authSuccessed by authViewModel.isAuthSuccessed.observeAsState(false)



    LaunchedEffect(authSuccessed) {
        if (authSuccessed) {
            navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Auth.route) { inclusive = true }
            }
            authViewModel.resetAuthState()
        }
    }

    val googleAuth =
        rememberLauncherForActivityResult(contract = GoogleAuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account != null) {
                    coroutineScope.launch {
                        authViewModel.googleAuth(account)
                    }
                }
            } catch (e: ApiException) {
                println(e)
            }
        }
    SweetSpotTheme(dynamicColor = false, darkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Biege80)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(isLogin)
            Spacer(modifier = Modifier.height(16.dp))

            if (!isLogin) {
                InputField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Ім'я"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            InputField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Адреса електронної пошти"
            )
            Spacer(modifier = Modifier.height(8.dp))

            PasswordInputField(
                value = password,
                onValueChange = { password = it },
                showPassword = showPassword,
                onShowPasswordToggle = { showPassword = !showPassword }
            )
            Spacer(modifier = Modifier.height(16.dp))

            AuthButton(
                text = if (isLogin) "Увійти" else "Зареєструватися",
                onClick = {
                    coroutineScope.launch {
                        if (isLogin) {
                            authViewModel.defaultSignIn(email.text, password.text)
                        } else {
                            authViewModel.defaultSignUp(name.text, email.text, password.text)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { isLogin = !isLogin }) {
                Text(
                    if (isLogin) "Немає акаунту? Зареєструватися" else "Вже є акаунт? Увійти",
                    color = Brown40,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (isLogin) {
                Text("або", color = Brown40, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                AuthButton(
                    text = "Увійти з Google",
                    onClick = { googleAuth.launch(1) },
                    backgroundColor = Brown80
                )
                Spacer(modifier = Modifier.height(8.dp))
                AuthButton(
                    text = "Увійти з facebook",
                    onClick = { /* FacebookLogin тут */ },
                    backgroundColor = BrownOrange40
                )
            }
        }
    }
}

@Composable
fun Logo(isLogin: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        val image: Painter = painterResource(id = R.drawable.cakelogo) // Замените на ваш ресурс
        Image(
            modifier = Modifier.size(163.dp, 174.dp),
            painter = image,
            contentDescription = "Логотип"
        )
        Text(
            text = "The Sweet Spot",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = if (isLogin) "Вхід" else "Реєстрація",
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun InputField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, placeholder: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp), // Внутренние отступы для текста
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            decorationBox = { innerTextField ->
                if (value.text.isEmpty()) {
                    Text(placeholder, style = MaterialTheme.typography.bodyLarge, color = Color.LightGray)
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun PasswordInputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    showPassword: Boolean,
    onShowPasswordToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f).padding(0.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    if (value.text.isEmpty()) {
                        Text("Пароль", style = MaterialTheme.typography.bodyLarge, color = Color.LightGray)
                    }
                    innerTextField()
                }
            )
            TextButton(onClick = onShowPasswordToggle, modifier = Modifier.height(100.dp).padding(0.dp)) {
                Text(if (showPassword) "Сховати" else "Показати", color = Brown40, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
fun AuthButton(text: String, onClick: () -> Unit, backgroundColor: Color = Brown40) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(text, color = Color.White, style = MaterialTheme.typography.titleMedium)
    }
}