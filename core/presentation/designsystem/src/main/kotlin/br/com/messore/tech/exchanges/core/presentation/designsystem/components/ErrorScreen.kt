package br.com.messore.tech.exchanges.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import br.com.messore.tech.exchanges.core.presentation.designsystem.ExchangesTheme

@Stable
data class Button(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
fun ErrorScreen(
    onDismiss: (() -> Unit)? = null,
    primaryButton: Button? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        contentAlignment = Alignment.Center
    ) {
        ConstraintLayout(
            constraintSet = ConstraintSet {
                val closeButtonRef = createRefFor("closeButton")
                val titleRef = createRefFor("title")
                val primaryButtonRef = createRefFor("primaryButton")

                constrain(closeButtonRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }

                constrain(titleRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(primaryButtonRef.top, 8.dp)
                }

                constrain(primaryButtonRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            onDismiss?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.layoutId("closeButton")
                ) {
                    Icon(
                        contentDescription = "close error dialog",
                        imageVector = Icons.Default.Close,
                    )
                }
            }

            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "Ops! Algo deu errado",
                modifier = Modifier.layoutId("title"),
                style = MaterialTheme.typography.labelLarge,
            )

            if (primaryButton != null) {
                Button(
                    onClick = primaryButton.onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("primaryButton")
                ) {
                    Text(
                        text = primaryButton.text,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    ExchangesTheme {
        ErrorScreen(
            onDismiss = {},
            primaryButton = Button(text = "Tentar novamente", onClick = {}),
        )
    }
}