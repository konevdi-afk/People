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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

    // Функция для проверки корректности цвета
    fun isValidColor(colorName: String): Boolean {
        return when (colorName.trim().lowercase()) {
            "red", "green", "blue", "yellow",
            "cyan", "magenta", "black", "white", "gray" -> true
            else -> false
        }
    }

    // Функция для преобразования текста в цвет
    fun parseColor(colorName: String): Color {
        return when (colorName.trim().lowercase()) {
            "red" -> Color.Red
            "green" -> Color.Green
            "blue" -> Color.Blue
            "yellow" -> Color.Yellow
            "cyan" -> Color.Cyan
            "magenta" -> Color.Magenta
            "black" -> Color.Black
            "white" -> Color.White
            "gray" -> Color.Gray
            else -> Color.Blue // По умолчанию
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Текстовое поле для ввода цвета
        TextField(
            value = colorText,
            onValueChange = {
                colorText = it
                // Сбрасываем ошибку при изменении текста
                showError = false
                errorMessage = ""
            },
            label = { Text("Введите цвет (Red, Green, Blue, etc.)") },
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

            Text(
                text = "Доступные цвета: Red, Green, Blue, Yellow, Cyan, Magenta, Black, White, Gray",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Кнопка, меняющая цвет - ТЕПЕРЬ ЦВЕТ ПРИМЕНЯЕТСЯ!
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
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Изменить цвет", color = Color.White)
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