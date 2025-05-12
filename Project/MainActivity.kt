package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDeliveryApp()
        }
    }
}

// --- Common Background Container ---
@Composable
fun BackgroundContainer(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_image), // <- your background image in drawable
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        content()
    }
}

// --- Main App ---
@Composable
fun FoodDeliveryApp() {
    var isLoggedIn by remember { mutableStateOf(false) }
    val cartItems = remember { mutableStateListOf<FoodItem>() }

    BackgroundContainer {
        if (isLoggedIn) {
            MainAppContent(cartItems)
        } else {
            LoginScreen(onLoginSuccess = { isLoggedIn = true })
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.8f))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onLoginSuccess() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun MainAppContent(cartItems: MutableList<FoodItem>) {
    var selectedScreen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen,
                onScreenSelected = { selectedScreen = it }
            )
        },
        containerColor = Color.Transparent // Scaffold transparent
    ) { innerPadding ->
        BackgroundContainer {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when (selectedScreen) {
                    Screen.Home -> HomeScreen(cartItems)
                    Screen.Cart -> CartScreen(cartItems)
                }
            }
        }
    }
}

enum class Screen {
    Home, Cart
}

@Composable
fun BottomNavigationBar(
    selectedScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedScreen == Screen.Home,
            onClick = { onScreenSelected(Screen.Home) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = selectedScreen == Screen.Cart,
            onClick = { onScreenSelected(Screen.Cart) },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") }
        )
    }
}

data class FoodItem(val name: String, val price: String, val emoji: String)

val sampleFoodItems = listOf(
    FoodItem("Pizza", "$12", "🍕"),
    FoodItem("Burger", "$8", "🍔"),
    FoodItem("Fries", "$5", "🍟"),
    FoodItem("Sushi", "$15", "🍣"),
    FoodItem("Pasta", "$10", "🍝"),
    FoodItem("Ice Cream", "$6", "🍦"),
    FoodItem("Salad", "$7", "🥗"),
    FoodItem("Steak", "$20", "🥩"),
    FoodItem("Tacos", "$9", "🌮"),
    FoodItem("Burrito", "$11", "🌯"),
    FoodItem("Hot Dog", "$5", "🌭"),
    FoodItem("Ramen", "$12", "🍜"),
    FoodItem("Donuts", "$4", "🍩"),
    FoodItem("Curry", "$14", "🍛"),
    FoodItem("Smoothie", "$7", "🥤"),
    FoodItem("Cupcakes", "$5", "🧁"),
    FoodItem("Pancakes", "$8", "🥞")
)

@Composable
fun HomeScreen(cartItems: MutableList<FoodItem>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Restaurants",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.7f))
                .padding(8.dp)
        )

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(sampleFoodItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${item.emoji} ${item.name}", fontSize = 20.sp, color = Color.Black)
                            Text(item.price, color = MaterialTheme.colorScheme.primary)
                        }
                        Button(onClick = { cartItems.add(item) }) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartScreen(cartItems: MutableList<FoodItem>) {
    var orderPlaced by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Cart",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.7f))
                .padding(8.dp)
        )

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Cart is empty!", color = Color.Black)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${item.emoji} ${item.name}", color = Color.Black)
                            Text(item.price, color = Color.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { orderPlaced = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Place Order")
            }

            if (orderPlaced) {
                OrderConfirmationScreen(cartItems)
            }
        }
    }
}

@Composable
fun OrderConfirmationScreen(cartItems: List<FoodItem>) {
    val deliveryTime = remember { mutableIntStateOf(Random.nextInt(1, 60)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        BackgroundContainer {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.8f))
                    .padding(16.dp)
            ) {
                Text(
                    "Order Placed Successfully!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Your ordered items:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.7f))
                        .heightIn(max = 200.dp)
                ) {
                    items(cartItems) { item ->
                        Text(
                            "${item.emoji} ${item.name} - ${item.price}",
                            color = Color.Black,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Your order will be delivered in ${deliveryTime.intValue} minutes.",
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }
    }
}

