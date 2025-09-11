package ci.nsu.moble.people

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ci.nsu.moble.people.ui.theme.PeopleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeopleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorChangingButton(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorChangingButton(modifier: Modifier = Modifier) {
    var buttonColor by remember { mutableStateOf(Color.Blue) }
    var colorText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    // Список доступных цветов
    val availableColors = listOf(
        "Red" to Color.Red,
        "Green" to Color.Green,
        "Blue" to Color.Blue,
        "Yellow" to Color.Yellow,
        "Cyan" to Color.Cyan,
        "Magenta" to Color.Magenta,
        "Black" to Color.Black,
        "White" to Color.White,
        "Gray" to Color.Gray
    )

    // Функция для проверки корректности цвета
    fun isValidColor(colorName: String): Boolean {
        return availableColors.any { it.first.equals(colorName.trim(), ignoreCase = true) }
    }

    // Функция для преобразования текста в цвет
    fun parseColor(colorName: String): Color {
        return availableColors.find { it.first.equals(colorName.trim(), ignoreCase = true) }
            ?.second ?: Color.Blue
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Заголовок
        Text(
            text = "Изменение цвета кнопки",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Текстовое поле для ввода цвета
        TextField(
            value = colorText,
            onValueChange = {
                colorText = it
                // Сбрасываем ошибку при изменении текста
                showError = false
                errorMessage = ""
            },
            label = { Text("Введите название цвета") },
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 16.dp),
            isError = showError
        )

        // Сообщение об ошибке
        if (showError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )
        }

        // Кнопка, меняющая цвет
        Button(
            onClick = {
                if (colorText.isBlank()) {
                    showError = true
                    errorMessage = "Пожалуйста, введите название цвета"
                } else if (!isValidColor(colorText)) {
                    showError = true
                    errorMessage = "Некорректный цвет: '$colorText'"
                } else {
                    // Если цвет корректен, меняем цвет кнопки
                    buttonColor = parseColor(colorText)
                    showError = false
                    errorMessage = ""
                }
            },
            modifier = Modifier
                .width(200.dp)
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Изменить цвет", color = Color.White)
        }

        // Список доступных цветов
        Text(
            text = "Доступные цвета:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 8.dp)
        ) {
            items(availableColors.chunked(2)) { rowColors ->
                Column(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    AvailableColorsRow(colors = rowColors)
                }
            }
        }
    }
}

@Composable
fun AvailableColorsRow(colors: List<Pair<String, Color>>) {
    Column {
        colors.forEach { (colorName, color) ->
            Card(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .width(300.dp),
                colors = CardDefaults.cardColors(
                    containerColor = color,
                    contentColor = if (color == Color.Black || color == Color.Blue) Color.White else Color.Black
                )
            ) {
                Text(
                    text = colorName,
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorChangingButtonPreview() {
    PeopleTheme {
        ColorChangingButton()
    }
}