package com.example.pokedex.ui.components.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlainTextField(value: String, onValueChange: (String) -> Unit, placeholderText: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholderText) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun ValidatedField(
    field: @Composable () -> Unit,
    errorMessage: String?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .then(
                    if (errorMessage != null) {
                        Modifier.border(
                            width = 1.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(4.dp)
                        )
                    } else {
                        Modifier
                    }
                )
        ) {
            field()
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp)
            )
        }
    }
}


@Composable
fun PasswordField(
    password: String,
    setPassword: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Password",
) {
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { newPassword -> setPassword(newPassword) },
        placeholder = { Text(text = placeholderText) },
        modifier = modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun EmailField(
    email: String,
    setEmail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = email,
        onValueChange = { newEmail -> setEmail(newEmail) },
        placeholder = { Text(text = "Email") },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy( keyboardType = KeyboardType.Email),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )
}

@Composable
fun SubmitButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
fun GradientBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEEEEEE),
                        Color(0xFFF5F5F5),
                        Color(0xFFEEEEEE),
                    )
                )
            )
    )
}

@Composable
fun PasswordRequirements(
    show: Boolean,
    password: String,
    modifier: Modifier = Modifier,
) {
    val hasMinimumLength = password.length >= 8
    val hasUppercase = password.any { it.isUpperCase() }
    val hasDigitOrSymbol = password.any { it.isDigit() || !it.isLetterOrDigit() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        if (show) {
            Column {
                Text(
                    text = "Password Requirements:",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E88E5)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordRequirementItem(
                    requirement = "At least 8 characters",
                    isValid = hasMinimumLength
                )
                PasswordRequirementItem(
                    requirement = "Include one uppercase letter",
                    isValid = hasUppercase
                )
                PasswordRequirementItem(
                    requirement = "Include one number or symbol",
                    isValid = hasDigitOrSymbol
                )
            }
        }
    }
}

@Composable
fun PasswordRequirementItem(requirement: String, isValid: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(
            imageVector = if (isValid) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
            contentDescription = null,
            tint = if (isValid) Color(0xFF4CAF50) else Color(0xFFF44336),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = requirement,
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (isValid) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        )
    }
}