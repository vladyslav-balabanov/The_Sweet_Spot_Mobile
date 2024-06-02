package com.example.thesweetspotmobile.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thesweetspotmobile.R
import com.example.thesweetspotmobile.Utils.Decoder
import com.example.thesweetspotmobile.Utils.TokenManager
import com.example.thesweetspotmobile.ui.components.CustomDrawer
import com.example.thesweetspotmobile.ui.components.CustomHeader
import com.example.thesweetspotmobile.ui.components.CustomMessageBar
import com.example.thesweetspotmobile.ui.components.ProductGrid
import com.example.thesweetspotmobile.ui.components.SearchBar
import com.example.thesweetspotmobile.ui.components.SortDropdownMenu
import com.example.thesweetspotmobile.ui.components.navigation.Screens
import com.example.thesweetspotmobile.ui.theme.Biege80
import com.example.thesweetspotmobile.ui.theme.Brown40
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme
import kotlinx.coroutines.launch
import java.io.File
import java.util.jar.Manifest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController, profileViewModel: UserProfileViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current
    var userImage by remember { mutableStateOf<Bitmap?>(null) }
    var imageFile by remember { mutableStateOf<File?>(null) }

    val user by profileViewModel.user.observeAsState()

    val name = remember { mutableStateOf(user?.name ?: "") }
    val email = remember { mutableStateOf(user?.email ?: "") }
    val phone = remember { mutableStateOf(user?.phone ?: "") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(user) {
        user?.let {
            name.value = it.name ?: ""
            email.value = it.email ?: ""
            phone.value = it.phone ?: ""
            it.image?.let { encodedImage ->
                val bitmap = Decoder.decodeSimpleBase64(encodedImage)
                if (bitmap != null) {
                    userImage = bitmap
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        profileViewModel.fetchUserProfile()
    }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("Profile", "Selected image URI: $it")
            val bitmap = profileViewModel.decodeImage(it)
            if (bitmap != null) {
                userImage = bitmap
            }

            val file = getFileFromUri(context, it)
            if (file != null) {
                imageFile = file
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            imagePickerLauncher.launch("image/*")
        } else {
            Log.e("Profile", "Permissions not granted")
        }
    }


    SweetSpotTheme(darkTheme = true, dynamicColor = false) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    CustomDrawer(
                        profileViewModel = profileViewModel,
                        onPersonalClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onHomeClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Home.route)
                            }
                        },
                        onCartClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Cart.route)
                            }
                        },
                        onHistoryClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(Screens.History.route)
                            }
                        }
                    )
                }
            },
            drawerState = drawerState,
            content = {
                Scaffold(
                    topBar = {
                        CustomHeader(onMenuClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        })
                    },
                    snackbarHost = { CustomMessageBar(snackbarHostState) },
                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Biege80)
                                .padding(20.dp)
                        ) {
                            Spacer(modifier = Modifier.height(70.dp))
                            Text(
                                text = "Особистий кабінет",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFF473327),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                                    .clickable {
                                        val readPermission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                                        val writePermission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

                                        if (readPermission && writePermission) {
                                            imagePickerLauncher.launch("image/*")
                                        } else {
                                            permissionLauncher.launch(arrayOf(
                                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            ))
                                        }
                                    }) {
                                userImage?.let {
                                    Image(
                                        bitmap = it.asImageBitmap(),
                                        contentDescription = "userimg",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = name.value,
                                onValueChange = { name.value = it },
                                label = { Text("Ім'я", color = Color(0xFF2D2D2D)) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF473327),
                                    unfocusedBorderColor = Color(0xFF473327),
                                    cursorColor = Color(0xFF473327),
                                    focusedTextColor = Color(0xFF473327),
                                    unfocusedTextColor = Color(0xFF473327)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = email.value,
                                onValueChange = { email.value = it },
                                label = { Text("Пошта", color = Color(0xFF2D2D2D)) },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF473327),
                                    unfocusedBorderColor = Color(0xFF473327),
                                    cursorColor = Color(0xFF473327),
                                    focusedTextColor = Color(0xFF473327),
                                    unfocusedTextColor = Color(0xFF473327)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = phone.value,
                                onValueChange = { phone.value = it },
                                label = { Text("Номер телефону", color = Color(0xFF2D2D2D)) },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF473327),
                                    unfocusedBorderColor = Color(0xFF473327),
                                    cursorColor = Color(0xFF473327),
                                    focusedTextColor = Color(0xFF473327),
                                    unfocusedTextColor = Color(0xFF473327)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(46.dp))

                            Button(
                                onClick = {
                                    profileViewModel.updateUserProfile(
                                        imageFile = imageFile,
                                        name = name.value,
                                        email = email.value,
                                        phone = phone.value
                                    )
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Профіль успішно оновлено")
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF473327)),
                                modifier = Modifier.size(204.dp, 51.dp)
                            ) {
                                Text(text = "Зберегти зміни", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                            }

                            Spacer(modifier = Modifier.height(60.dp))

                            Button(
                                onClick = { TokenManager(context).clearToken()
                                          navController.navigate(Screens.Auth.route)
                                          },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF986F55)),
                                modifier = Modifier.size(334.dp, 51.dp)
                            ) {
                                Text(text = "Вийти з акаунту", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                )
            }
        )
    }
}

private fun getFileFromUri(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val file = File(context.cacheDir, "temp_image.jpg")
    val outputStream = file.outputStream()
    inputStream.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return file
}
