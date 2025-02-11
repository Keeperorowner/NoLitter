package lantian.nolitter.views.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@ExperimentalMaterial3Api
fun AppBarTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    onClose: () -> Unit
) {
    val textStyle = LocalTextStyle.current
    val interactionSource = remember { MutableInteractionSource() }

    // Make sure there is no background color in the decoration box
    val colors = TextFieldDefaults.colors()

    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse { MaterialTheme.colorScheme.onSurface }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor, fontSize = 16.sp))

    // Request focus when this composable is first initialized
    val focusRequester = FocusRequester()
    SideEffect { focusRequester.requestFocus() }

    // The mutable state of the input text value
    var inputTextValue by remember { mutableStateOf(value) }

    // Combine two thing into one
    val trueOnValueChange: (String) -> Unit = { inputTextValue = it; onValueChange(it) }

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            value = inputTextValue,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(32.dp)
                .focusRequester(focusRequester),
            onValueChange = trueOnValueChange,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            interactionSource = interactionSource,
            singleLine = true,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = inputTextValue,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    singleLine = true,
                    enabled = true,
                    interactionSource = interactionSource,
                    colors = colors,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier.clickable { onClose() }
                        )
                    }
                )
            }
        )
    }
}
