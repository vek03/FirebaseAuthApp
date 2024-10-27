//Vek Histories
package com.example.vekauthentication

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vekauthentication.ui.theme.CustomColorScheme
import com.example.vekauthentication.ui.theme.VekAuthenticationTheme
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        setContent {
            VekAuthenticationTheme {
                MyCustomTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Login(auth)
                    }
                }
            }
        }
    }
}

@Composable
fun MyCustomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        content = content
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

fun showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun login(auth: FirebaseAuth, email: String, senha: String, callback: (Boolean, String) -> Unit) {
    var message: String

    auth.signInWithEmailAndPassword(
        email,
        senha
    ).addOnCompleteListener{ task ->
        if (task.isSuccessful) {
            Log.i(TAG, "signInWithEmail:success")
            message = "Login Realizado com Sucesso!"

            callback(true, message)
        } else {
            Log.e(TAG, "signInWithEmail:failure", task.exception)
            message = when (task.exception) {
                is FirebaseAuthInvalidUserException -> "Usuário Não Encontrado!"
                is FirebaseAuthInvalidCredentialsException -> "Credenciais Inválidas!"
                is FirebaseTooManyRequestsException -> "Usuário Bloqueado por Múltiplas Tentativas!"
                else -> "Erro ao Realizar Login!"
            }

            callback(false, message)
        }
    }
}

fun register(auth: FirebaseAuth, email: String, senha: String, callback: (Boolean, String) -> Unit) {
    var message: String

    auth.createUserWithEmailAndPassword(
        email,
        senha
    ).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.i(TAG, "createUserWithEmail:success")
            message = "Usuário Registrado com Sucesso!"

            callback(true, message)
        }
        else {
            Log.e(TAG, "createUserWithEmail:failure", task.exception)
            message = when (task.exception) {
                is FirebaseAuthUserCollisionException -> "Email Já Cadastrado!"
                else -> "Erro ao Registrar Usuário!"
            }

            callback(false, message)
        }
    }
}

fun logout(auth: FirebaseAuth) : Boolean{
    try{
        auth.signOut()

        return true
    }
    catch(e: Exception){
        return false
    }
}

@Composable
fun CustomTextField(value: String, label: String, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        colors = TextFieldColors(
            cursorColor = CustomColorScheme.secondary,
            textSelectionColors = TextSelectionColors(
                handleColor = CustomColorScheme.secondary,
                backgroundColor = CustomColorScheme.primary.copy(alpha = 0.4f)
            ),
            focusedLabelColor = CustomColorScheme.secondary,
            unfocusedLabelColor = CustomColorScheme.secondary,
            focusedIndicatorColor = CustomColorScheme.secondary,
            unfocusedIndicatorColor = CustomColorScheme.secondary,
            focusedContainerColor = CustomColorScheme.primary,
            unfocusedContainerColor = CustomColorScheme.primary,
            focusedTextColor = CustomColorScheme.secondary,
            unfocusedTextColor = CustomColorScheme.secondary,
            focusedLeadingIconColor = CustomColorScheme.primary,
            unfocusedLeadingIconColor = CustomColorScheme.primary,
            focusedTrailingIconColor = CustomColorScheme.primary,
            unfocusedTrailingIconColor = CustomColorScheme.primary,
            focusedPrefixColor = CustomColorScheme.primary,
            unfocusedPrefixColor = CustomColorScheme.primary,
            focusedSuffixColor = CustomColorScheme.primary,
            unfocusedSuffixColor = CustomColorScheme.primary,
            focusedPlaceholderColor = CustomColorScheme.secondary,
            unfocusedPlaceholderColor = CustomColorScheme.secondary,
            focusedSupportingTextColor = CustomColorScheme.primary,
            unfocusedSupportingTextColor = CustomColorScheme.primary,
            disabledLabelColor = CustomColorScheme.secondary,
            disabledPrefixColor = CustomColorScheme.secondary,
            disabledSuffixColor = CustomColorScheme.secondary,
            disabledTextColor = CustomColorScheme.secondary,
            disabledContainerColor = CustomColorScheme.primary,
            disabledIndicatorColor = CustomColorScheme.primary,
            disabledPlaceholderColor = CustomColorScheme.secondary,
            disabledLeadingIconColor = CustomColorScheme.primary,
            disabledTrailingIconColor = CustomColorScheme.primary,
            disabledSupportingTextColor = CustomColorScheme.primary,
            errorCursorColor = CustomColorScheme.error,
            errorIndicatorColor = CustomColorScheme.error,
            errorLabelColor = CustomColorScheme.error,
            errorPrefixColor = CustomColorScheme.error,
            errorTextColor = CustomColorScheme.error,
            errorLeadingIconColor = CustomColorScheme.error,
            errorTrailingIconColor = CustomColorScheme.error,
            errorSuffixColor = CustomColorScheme.error,
            errorPlaceholderColor = CustomColorScheme.primary,
            errorSupportingTextColor = CustomColorScheme.primary,
            errorContainerColor = CustomColorScheme.error
        )
    )
}


fun isValid(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}


@Composable
fun Login(auth: FirebaseAuth){
    val context = LocalContext.current

    var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Log.i(TAG, "Current User: ${auth.currentUser}")
    Log.i(TAG, "isLoggedIn: $isLoggedIn")

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(text = "Vek Authentication", color = MaterialTheme.colorScheme.secondary)
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            CustomTextField(
                value = senha,
                onValueChange = { senha = it },
                label = "Senha",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.SpaceEvenly
        ){
            if(isLoggedIn) {
                Column {
                    Button(onClick = {
                        isError = false
                        message = ""

                        if (!isLoggedIn) {
                            message = "Você Precisa Logar para Cadastrar Usuários!"
                            isError = true
                            return@Button
                        }

                        if (!isValid(email, senha)) {
                            message = "Email ou Senha Inválidos!"
                            isError = true
                            return@Button
                        }

                        register(auth, email, senha, callback = { status, registerMessage ->
                            message = registerMessage

                            if (!status) {
                                isError = true
                            }

                            showToast(context, message)
                        })
                    }) {
                        Text(text = "Registrar")
                    }
                }
            }

            if(!isLoggedIn) {
                Column {
                    Button(onClick = {
                        isError = false
                        message = ""

                        if (isLoggedIn) {
                            message = "Você já está logado!"
                            isError = true
                            showToast(context, message)
                            return@Button
                        }

                        if (!isValid(email, senha)) {
                            message = "Email ou Senha Inválidos!"
                            isError = true
                            showToast(context, message)
                            return@Button
                        }

                        login(auth, email, senha, callback = { status, loginMessage ->
                            message = loginMessage

                            if (status) {
                                isLoggedIn = true
                            } else {
                                isLoggedIn = false
                                isError = true
                            }

                            showToast(context, message)
                        })
                    }) {
                        Text(text = "Logar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(40.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center,
            Alignment.Bottom
        ){
            Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isError) {
                    Text(text = message, color = MaterialTheme.colorScheme.error)
                }

                if(isLoggedIn){
                    Text(text = "Logado como: ${auth.currentUser?.email}", color = MaterialTheme.colorScheme.secondary)

                    Button(onClick = {
                        isLoggedIn = false
                        logout(auth)
                    }) {
                        Text(text = "Logout")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VekAuthenticationTheme {
        Greeting("Vek Test")
    }
}