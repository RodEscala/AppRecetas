package com.example.helloworldcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.navigation.NavHostController

import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppInit()
        }
    }
}

@Composable
fun AppInit() {
    // Configurar el NavController y el NavHost para gestionar las rutas de la app
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { Login(navController) }
        composable("register") { Register(navController) }
        composable("recetas"){ Recetas(navController)}
    }
}


@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        Modifier
            .fillMaxSize()

    ) {
        Divider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        // Colocamos la imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo), // Nombre de tu imagen en drawable
            contentDescription = "Background image",
            modifier = Modifier
                .padding(1.dp)
                .fillMaxSize(), // Hace que la imagen ocupe todo el espacio disponible
            contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
        )
        Column(
            Modifier
                .padding(24.dp)

                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Icon(
                painter = painterResource(id = R.drawable.cocina),
                null,
                modifier = Modifier.size(80.dp),
                tint = Color.Gray
            )
            // Campo para el nombre de usuario
            TextInput(InputType.Name, value = name, onValueChange = { name = it })

            // Campo para la contraseña
            TextInput(InputType.Password, value = password, onValueChange = { password = it })
            Button(onClick = {
                navController.navigate("recetas")
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Ingresar", Modifier.padding(vertical = 8.dp))
            }
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "No tienes Cuenta ?", color = Color.Blue)
                TextButton(onClick = {
                    navController.navigate("register")
                }) {
                    Text(text = "Registrate")
                }
            }
        }
    }
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOption: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name : InputType(
        label = "Usename",
        icon = Icons.Default.Person,
        keyboardOption = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password : InputType(
        label = "Password",
        icon = Icons.Default.Lock,
        keyboardOption = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun TextInput(inputType: InputType, value: String, onValueChange: (String) -> Unit) {

    val maxLength = 10
    TextField(
        value = value,
        onValueChange = onValueChange, // Llamar a la función pasada para actualizar el valor
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null) },
        label = { Text(text = inputType.label) },
        shape = MaterialTheme.shapes.small
    )

}


@Composable
fun Register(navController: NavHostController) {
    // Obtén el contexto de la aplicación
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var users: MutableList<String> = mutableListOf()

    fun userRegister() {
        if (name.isNotEmpty() && password.isNotEmpty()) {

            val newUser = User(name, password)
            users.add(newUser.toString())
            // Mostrar un mensaje Toast de registro exitoso
            Toast.makeText(context, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context,
                "Por favor, ingresa nombre de usuario y contraseña.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Box(
        Modifier
            .fillMaxSize()

    ) {
        // Colocamos la imagen de fondo

        Column(
            Modifier
                .padding(24.dp)

                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,

            )
        {
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.cocina),
                null,
                modifier = Modifier.size(80.dp),
                tint = Color.Gray
            )

            // Campo para el nombre de usuario
            TextInput(InputType.Name, value = name, onValueChange = { name = it })

            // Campo para la contraseña
            TextInput(InputType.Password, value = password, onValueChange = { password = it })

            Button(onClick = {

                userRegister()

            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Registrarse", Modifier.padding(vertical = 8.dp))
            }
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 48.dp)
            )

        }
    }
}

// Clase Usuario que contiene nombre de usuario y contraseña
data class User(val user: String, val password: String)


@Composable
fun Recetas(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo), // Nombre de tu imagen en drawable
            contentDescription = "Background image",
            modifier = Modifier
                .padding(1.dp)
                .fillMaxSize(), // Hace que la imagen ocupe todo el espacio disponible
            contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
        )
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .background(Color.Black) // Fondo negro
                        .clip(RoundedCornerShape(16.dp)) // Bordes redondeados para el fondo
                        .fillMaxWidth() // Ancho máximo
                        .padding(vertical = 15.dp) // Padding vertical
                        .padding(horizontal = 20.dp), // Padding horizontal si lo necesitas
                    horizontalAlignment = Alignment.CenterHorizontally // Centra el contenido horizontalmente
                ) {
                    Text(
                        text = "Recetas",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally) // Centra el texto horizontalmente
                            .padding(20.dp),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.LightGray // Color del texto
                        )
                    )
                }

            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.pure), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "Pure de papas",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.alfredo), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "Pasta con Salsa Alfredo",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.lasagna), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "Lasagna",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.torta), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "Torta de verduras",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.cazuela), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "Cazuela de carne",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.arrozconpollo), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "arroz con pollo",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.estofado), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "Estofado de carne",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
            item {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.polloasado), // Nombre de tu imagen en drawable
                        contentDescription = "Background image",
                        modifier = Modifier
                            .fillMaxWidth() // Hace que la imagen ocupe todo el ancho disponible
                            .height(150.dp)
                            .padding(1.dp)
                            .clip(RoundedCornerShape(16.dp)), // Opcional: Padding alrededor de la imagen
                        contentScale = ContentScale.Crop // Ajusta la imagen para que se recorte si es necesario
                    )
                    Text(

                        text = "Pollo asado con Miel",
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        )
                    )

                }


            }
        }
    }
}