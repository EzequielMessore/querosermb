package br.com.messore.tech.exchanges.core.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.com.messore.tech.exchanges.core.presentation.designsystem.ExchangesTheme
import br.com.messore.tech.exchanges.presentation.designsystem.R
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import okhttp3.Headers

/**
 * AppImage is a composable that loads an image from a URL and resolves issues with images not loading.
 * It uses Coil for image loading and caching, ensuring that images are properly cached and displayed.
 *
 * @param imageUrl The URL of the image to load.
 * @param contentDescription The content description for the image.
 *
 * @see <a href="https://stackoverflow.com/a/78647122/5140172">
 *           Image is not displayed with Coil's rememberAsyncImagePainter
 *     </a>
 */
@Composable
fun AppImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    contentDescription: String,
) {
    val headers = Headers.Builder()
        .add("Cookie", "demo_access=M5Slo246")
        .build()
    val model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .placeholder(R.drawable.currency_exchange)
        .error(R.drawable.currency_exchange)
        .headers(headers).build()
    val painterModel = rememberAsyncImagePainter(model = model)
    Image(
        modifier = modifier,
        painter = painterModel,
        contentDescription = contentDescription,
    )
}

@Preview
@Composable
private fun AppImagePreview() {
    ExchangesTheme {
        AppImage(
            contentDescription = "Currency Exchange",
            imageUrl = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons" +
                "/type-id/png_64/5fbfbd742fb64c67a3963ebd7265f9f3.png",
        )
    }
}
