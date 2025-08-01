package com.cryptic.roundrecipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.SuggestionChip
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.max
import androidx.room.util.TableInfo


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(){

    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current


    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = {
                                Text("Search")
                            },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, "Search Icon")
                        },
                        shape = CircleShape,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if(query.isNotBlank()){
                                    /*TODO*/
                                }
                                focusManager.clearFocus()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                },
                navigationIcon = {
                    TooltipBox(
                        positionProvider =
                            TooltipDefaults.rememberTooltipPositionProvider(
                                TooltipAnchorPosition.Above
                            ),
                        tooltip = { PlainTooltip { Text("Menu") } },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                },
                actions = {
                    TooltipBox(
                        positionProvider =
                            TooltipDefaults.rememberTooltipPositionProvider(
                                TooltipAnchorPosition.Above
                            ),
                        tooltip = { PlainTooltip { Text("Add to favorites") } },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "User Profile Settings",
                            )
                        }
                    }
                },
            )
        },
        floatingActionButton  = {
            HorizontalFloatingToolbar(
                expanded = true,
                modifier = Modifier.padding(20.dp),
                expandedShadowElevation = 20.dp,
                colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
                    toolbarContainerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                contentPadding = PaddingValues(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                ){
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home Icon"
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Advanced Search Icon"
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Filled.Book,
                            contentDescription = "Cookbook"
                        )
                    }
                }
            }
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){

        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecipeCard() {
    val tags = listOf("Easy", "Quick", "Dinner", "Chicken", "Healthy", "30-Minute Meal", "Pasta")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.45f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    modifier = Modifier
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(id = R.drawable.sampleimage),
                    contentDescription = "A sample Image",
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.non_veg),
                        contentDescription = "Non Veg Icon"
                    )

                    Spacer(Modifier.weight(1f))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Time Icon"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("12 hours")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.55f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Big Black Man",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "He is a cop so no cop gonna bully him",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                LazyRow(
                    modifier = Modifier.align(alignment = Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items = tags) { tag ->
                        SuggestionChip(
                            onClick = { /* TODO: Handle tag click */ },
                            label = { Text(text = tag) }
                        )
                    }
                }
            }
        }
    }
}
