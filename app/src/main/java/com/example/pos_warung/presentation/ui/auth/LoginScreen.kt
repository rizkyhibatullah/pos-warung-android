import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pos_warung.R
import com.example.pos_warung.presentation.common.UiState
import com.example.pos_warung.presentation.ui.components.common.POSButton
import com.example.pos_warung.presentation.ui.components.common.PosTextField
import com.example.pos_warung.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            onLoginSuccess()

        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(48.dp))

        PosTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username",
            placeholder = "Masukkan Username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PosTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Masukkan Password",
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        POSButton(
            text = "Login",
            onClick = {
                viewModel.login(username, password)
            },
            enabled = loginState !is UiState.Loading
        )
        when (loginState) {
            is UiState.Loading -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Sedang Login...", color = MaterialTheme.colorScheme.primary)
            }
            is UiState.Error -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (loginState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            else -> {}
        }
    }


}