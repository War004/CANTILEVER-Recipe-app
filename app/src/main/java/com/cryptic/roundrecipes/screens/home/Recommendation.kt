package com.cryptic.roundrecipes.screens.home

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.cryptic.roundrecipes.R
import com.cryptic.roundrecipes.database.Classification
import com.cryptic.roundrecipes.database.Recipe
import com.cryptic.roundrecipes.database.RecipeName
import com.cryptic.roundrecipes.formatTime
import com.cryptic.roundrecipes.ui.theme.RoundRecipesTheme
import com.cryptic.roundrecipes.viewModel.home.RecipeListUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(uiState: RecipeListUiState, modifier: Modifier = Modifier) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    when (uiState) {
        is RecipeListUiState.Loading -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is RecipeListUiState.Error -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${uiState.message}")
            }
        }
        is RecipeListUiState.Success -> {
            var query by remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current

            Scaffold(
                modifier = modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),//not using the modifer givven in the parameter remove it
                floatingActionButtonPosition = FabPosition.Center,
                topBar = {
                    TopAppBar(
                        modifier = Modifier.padding(4.dp),
                        title = {
                            TextField(
                                value = query,
                                onValueChange = { query = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Search") },
                                leadingIcon = { Icon(Icons.Filled.Search, "Search Icon") },
                                shape = CircleShape,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        if (query.isNotBlank()) { /*TODO*/ }
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
                                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
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
                                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
                                tooltip = { PlainTooltip { Text("Add to favorites") } },
                                state = rememberTooltipState(),
                            ) {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "User Profile Settings")
                                }
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = MaterialTheme.colorScheme.surface
                        )
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
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(0.7f)) {
                            IconButton(onClick = { /* TODO */ }) {
                                Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon")
                            }
                            Spacer(Modifier.weight(1f))
                            IconButton(onClick = { /* TODO */ }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "Advanced Search Icon")
                            }
                            Spacer(Modifier.weight(1f))
                            IconButton(onClick = { /* TODO */ }) {
                                Icon(imageVector = Icons.Filled.Book, contentDescription = "Cookbook")
                            }
                        }
                    }
                }
            ) { paddingValues ->
                // This is where the list of recipes is displayed.
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    // Add some padding around the whole list and space between items
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Use the 'items' extension function to build the list
                    // 'key' is a performance optimization that helps Compose identify items
                    items(
                        items = uiState.recipes,
                        key = { recipe -> recipe.recipeId }
                    ) { recipe ->
                        RecipeCard(recipe = recipe)
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier) {
    val tags = (recipe.classification.course + recipe.classification.category)
        .filter { it.isNotBlank() }

    val minutes = recipe.totalTimeSeconds / 60

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // The new top-level container is a Column, which will stack the
        // content row on top of the metadata row.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            // --- 1. TOP ROW: MAIN CONTENT ---
            // This row takes up all available vertical space, pushing the metadata row down.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // This is the key to pushing the bottom row down
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left side of the content (Image)
                Image(
                    modifier = Modifier
                        .height(125.dp)
                        .weight(0.45f)
                        .fillMaxHeight() // Fill the height of the content row
                        .clip(RoundedCornerShape(8.dp)),
                    painter = painterResource(id = R.drawable.sampleimage),
                    contentDescription = recipe.name.en,
                    contentScale = ContentScale.Crop
                )

                // Right side of the content (Title and Description)
                Column(
                    modifier = Modifier
                        .weight(0.55f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = recipe.name.en,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2, // Allow two lines for longer titles
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = recipe.warning ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // A small spacer between the content and the metadata bar
            Spacer(modifier = Modifier.height(8.dp))

            // --- 2. BOTTOM ROW: METADATA ---
            // This row contains all the icons, time, and tags.
            // It will always be at the bottom of the card.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side of the metadata (Icon and Time)
                Row(
                    modifier = Modifier.weight(0.45f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (recipe.isNonVeg) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.non_veg),
                            contentDescription = "Non Veg Icon"
                        )
                    }
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
                        Text(
                            text = "$minutes min",
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp)) // Spacer between left and right sections

                // Right side of the metadata (Tags)
                Box(
                    modifier = Modifier.weight(0.55f),
                    contentAlignment = Alignment.CenterEnd // Align tags to the end
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        reverseLayout = false // Alignement towards end looked  also it caused the clip to be behind the image coulumn
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
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    val sampleRecipe = Recipe(
        recipeId = "sample_id",
        countryCode = "in",
        name = RecipeName(local = "Sample Dish", en = "Sample English Dish Name"),
        classification = Classification(
            course = listOf("Main Course"),
            category = listOf("Quick", "Dinner"),
            mealTiming = emptyList()
        ),
        isNonVeg = true,
        totalTimeSeconds = 1800, // 30 minutes
        warning = null,
        overloadInfo = "This is a longer description of the sample dish to see how it wraps and displays in the card."
    )
    RoundRecipesTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            RecipeCard(recipe = sampleRecipe)
        }
    }
}