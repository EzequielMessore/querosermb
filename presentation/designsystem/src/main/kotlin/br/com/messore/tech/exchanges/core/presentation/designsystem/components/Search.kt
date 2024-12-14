package br.com.messore.tech.exchanges.core.presentation.designsystem.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.messore.tech.exchanges.core.presentation.designsystem.ExchangesTheme

@VisibleForTesting
const val SearchTag = "Search"

@Composable
fun Search(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String = "",
    onSearchChanged: (String) -> Unit,
    onClearTextClicked: (String) -> Unit = {},
    keyboardAction: () -> Unit = {},
) {
    val keyboardCtrl = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .testTag(SearchTag)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 10.dp)
            ),
        value = value,
        onValueChange = onSearchChanged,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Cor do placeholder adaptável ao tema
                ),
                maxLines = 2,
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    indication = null,
                    onClick = { onClearTextClicked("") },
                    interactionSource = interactionSource,
                ),
                contentDescription = if (value.isEmpty()) "Search Icon" else "Close Icon",
                imageVector = if (value.isEmpty()) Icons.Default.Search else Icons.Default.Close,
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardAction()
            keyboardCtrl?.hide()
        }),
    )
}


@Preview
@Composable
private fun SearchPreview() {
    ExchangesTheme {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            Search(
                value = "texto exemplo",
                onSearchChanged = {},
                onClearTextClicked = {}
            )
        }
    }
}
