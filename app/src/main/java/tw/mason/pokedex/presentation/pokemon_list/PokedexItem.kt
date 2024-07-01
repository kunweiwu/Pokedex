package tw.mason.pokedex.presentation.pokemon_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.mason.pokedex.R
import tw.mason.pokedex.data.model.PokedexListEntry
import tw.mason.pokedex.presentation.ui.theme.PokedexTheme
import java.util.*


@Composable
fun PokedexItem(
    modifier: Modifier = Modifier,
    navigateToDetail: (dominantColor: Color, pokemonName: String) -> Unit,
    entry: PokedexListEntry
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navigateToDetail(dominantColor, entry.pokemonName)
            },
    ) {
        Column {
            val scope = rememberCoroutineScope()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build(),
                onSuccess = { success ->
                    val drawable = success.result.drawable
                    scope.launch(Dispatchers.IO) {
                        val bmp =
                            (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        Palette.from(bmp).generate {
                            it?.dominantSwatch?.rgb?.let { colorValue ->
                                dominantColor = Color(colorValue)
                            }
                        }
                    }
                },
                contentDescription = "pokemonImage",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                placeholder = painterResource(id = R.drawable.img)
            )
            Text(
                text = entry.pokemonName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                },
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PokedexItemPreview() {
    PokedexTheme {
        PokedexItem(
            modifier = Modifier,
            navigateToDetail = { _, _ -> },
            entry = PokedexListEntry("Test", "", 0)
        )
    }
}