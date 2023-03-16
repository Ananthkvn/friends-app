package com.example.friends.validationFields

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class FormFieldAge<Age> {
    protected val stateInternal = MutableStateFlow<Age?>(null)
    val state = stateInternal.asStateFlow()
    protected val isValidInternal = MutableStateFlow(true)
    val isValid = isValidInternal.asStateFlow()
    abstract suspend fun validate(focusIfError: Boolean = true):Boolean
    open fun clearError(){}
    open fun clearFocus(){}
    open fun disable(){}
    open fun enable(){}
}