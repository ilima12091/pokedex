package com.example.pokedex.ui.utils

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

object Validator {
    fun validateEmail(email: String): ValidationResult {
        val emptyValidation = validateNonEmpty(email, "Email")
        if (emptyValidation is ValidationResult.Error) {
            return emptyValidation
        }

        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error("Invalid email format")
        }
    }

    fun validatePassword(password: String): ValidationResult {
        val hasMinimumLength = password.length >= 8
        val hasUppercase = password.any { it.isUpperCase() }
        val hasDigitOrSymbol = password.any { it.isDigit() || !it.isLetterOrDigit() }

        val errorMessage = """
            Invalid Password, password must at least:
              - Have 8 characters
              - Include one uppercase letter
              - Include one number or symbol
        """.trimIndent()

        return if (hasMinimumLength && hasUppercase && hasDigitOrSymbol) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(errorMessage)
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        return if (password == confirmPassword) {
            ValidationResult.Success
        } else {
            ValidationResult.Error("Passwords do not match")
        }
    }

    fun validateNonEmpty(field: String, fieldName: String): ValidationResult {
        return if (field.isBlank()) {
            ValidationResult.Error("$fieldName cannot be empty")
        } else {
            ValidationResult.Success
        }
    }
}