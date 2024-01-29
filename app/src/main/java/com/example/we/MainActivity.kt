package com.example.we

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.we.ui.theme.WETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent,
                    contentColor = Color.White,
                    content = {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(40, 75, 190),
                                            Color(60, 95, 210)
                                        )
                                    )
                                )
                                .fillMaxSize()
                        ) {
                            BookShop()
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShop() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = {
                Row {
                    Image(
                        painter = painterResource(R.drawable.book_worm_icon),
                        contentDescription = "",
                        modifier = Modifier.size(120.dp)
                    )
                }
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(24.dp)
                        .clickable { /* Handle search action */ }
                )
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(24.dp)
                        .clickable { /* Handle cart action */ }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display book items in a list
        LazyColumn {
            items(10) {
                BookItem(R.drawable.gogging, "Cant Hurt Me", "DAVID GOGGINS", "€29,99")
                Spacer(modifier = Modifier.height(16.dp))
                BookItem(R.drawable.james, "How They Broke Britain", "JAMES O'BRIEN", "€9.50 €19.00")
                Spacer(modifier = Modifier.height(16.dp))
                BookItem(R.drawable.murdle, "Murdle", "G.T. KARBER", "€24,00")
                Spacer(modifier = Modifier.height(16.dp))
                BookItem(R.drawable.steel, "Loving", "DANIELLE STEEL", "€22.00")
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BookItem(bookImage: Int, title: String, author: String, price: String) {
    val (actualPrice, discountedPrice) = parsePrice(price)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { /* Handle book item click */ }
    ) {
        Row(Modifier.background(Color(60, 95, 210))) {
            Image(
                painter = painterResource(id = bookImage),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.medium)
                    .shadow(25.dp, MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = author,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    if (discountedPrice != null) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.copy(
                                    textDecoration = TextDecoration.LineThrough).toSpanStyle()
                                ) {
                                    append("$discountedPrice ")
                                }
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()
                                ) {
                                    append(actualPrice)
                                }
                            },
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = actualPrice,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

private fun parsePrice(price: String): Pair<String, String?> {
    val parts = price.split(" ")
    return if (parts.size == 2) {
        parts[0] to parts[1]
    } else {
        price to null
    }
}

@Preview(showBackground = true)
@Composable
fun BookShopPreview() {
    WETheme {
        BookShop()
    }
}