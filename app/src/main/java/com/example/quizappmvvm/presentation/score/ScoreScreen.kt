package com.example.quizappmvvm.presentation.score


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quizappmvvm.R
import com.example.quizappmvvm.presentation.nav_graph.Routes
import com.example.quizappmvvm.presentation.util.Dimens
import com.smarttoolfactory.screenshot.ImageResult
import com.smarttoolfactory.screenshot.ScreenshotBox
import com.smarttoolfactory.screenshot.rememberScreenshotState
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScoreScreen(
    noOfQuestions: Int,
    noOfCorrectAns: Int,
    navController: NavController
) {
    val screenshotState = rememberScreenshotState()
    var captureTriggered by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val imageResult by screenshotState.imageState
    var sharingMedium by remember { mutableStateOf("") }

    BackHandler {
        navController.navigate(Routes.HomeScreen.route) {
            popUpTo(Routes.HomeScreen.route) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(captureTriggered) {
        if (captureTriggered) {
            screenshotState.capture()
            captureTriggered = false
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.MediumPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(
                onClick = {
                    navController.navigate(Routes.HomeScreen.route) {
                        popUpTo(Routes.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_close_24),
                    contentDescription = "close",
                    tint = colorResource(id = R.color.blue_grey)
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
        ScreenshotBox(screenshotState = screenshotState) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clip(RoundedCornerShape(Dimens.MediumCornerRadius))
                    .background(colorResource(id = R.color.blue_grey)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = Dimens.MediumPadding,
                        vertical = Dimens.MediumPadding
                    ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val composition by rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(resId = R.raw.score_anim)
                    )
                    val annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("You attempted ")
                        }
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("$noOfQuestions questions ")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("and from that ")
                        }
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.green))) {
                            append("$noOfCorrectAns answers ")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("are correct")
                        }
                    }
                    val scorePercentage = calculatePercentage(noOfCorrectAns, noOfQuestions)
                    LottieAnimation(
                        modifier = Modifier.size(Dimens.LargeLottieAnimSize),
                        composition = composition,
                        iterations = 100
                    )
                    Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
                    Text(
                        text = "Congrats!",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.MediumTextSize,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))
                    Text(
                        text = "$scorePercentage% Score",
                        color = colorResource(id = R.color.green),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.LargeTextSize,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))
                    Text(
                        text = "Quiz completed successfully",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.SmallTextSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))
                    Text(
                        text = annotatedString,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.SmallTextSize,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Share with us : ",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = Dimens.SmallTextSize
                        )
                        Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (!captureTriggered) {
                                        captureTriggered = true
                                        sharingMedium = "instagram"
                                    }
                                },
                            painter = painterResource(id = R.drawable.instagram),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (!captureTriggered) {
                                        captureTriggered = true
                                        sharingMedium = "facebook"
                                    }
                                },
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (!captureTriggered) {
                                        captureTriggered = true
                                        sharingMedium = "whatsapp"
                                    }
                                },
                            painter = painterResource(id = R.drawable.whatsapp),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(imageResult) {
        when (val result = imageResult) {
            is ImageResult.Success -> {
                shareData(context, result, sharingMedium)
            }

            is ImageResult.Error -> {
                Toast.makeText(
                    context,
                    "Something went wrong: ${result.exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }
}

fun shareData(context: Context, imageResult: ImageResult.Success, sharingMedium: String) {
    val bitmap = imageResult.data.asImageBitmap()
    val imageFile = createImageFile(context)
    if (imageFile != null) {
        val outputStream = FileOutputStream(imageFile)
        bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
        val imageUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
        when (sharingMedium) {
            "whatsapp" -> {
                shareOnWhatsApp(context, imageUri, "Check out my quiz score!")
            }

            "facebook" -> {
                shareOnFacebook(context, imageUri, "Check out my quiz score!")
            }
            "instagram" -> {
                shareOnInstagram(context, imageUri, "Check out my quiz score!")
            }
        }
    }
}

private fun shareOnWhatsApp(context: Context, imageUri: Uri, text: String) {
    val whatsappIntent = Intent(Intent.ACTION_SEND)
    whatsappIntent.type = "image/*"
    whatsappIntent.setPackage("com.whatsapp")
    whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    whatsappIntent.putExtra(Intent.EXTRA_TEXT, text)
    try {
        context.startActivity(whatsappIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
    }
}

private fun shareOnFacebook(context: Context, imageUri: Uri, text: String) {
    val facebookIntent = Intent(Intent.ACTION_SEND)
    facebookIntent.type = "image/*"
    facebookIntent.setPackage("com.facebook.katana")
    facebookIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    facebookIntent.putExtra(Intent.EXTRA_TEXT, text)
    try {
        context.startActivity(facebookIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Facebook not installed.", Toast.LENGTH_SHORT).show()
    }
}

private fun shareOnInstagram(context: Context, imageUri: Uri, text: String) {
    val instagramIntent = Intent(Intent.ACTION_SEND)
    instagramIntent.type = "image/*"
    instagramIntent.setPackage("com.instagram.android")
    instagramIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    instagramIntent.putExtra(Intent.EXTRA_TEXT, text)
    try {
        context.startActivity(instagramIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Instagram not installed.", Toast.LENGTH_SHORT).show()
    }
}

private fun createImageFile(context: Context): File? {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "scorecard_${timeStamp}_"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return try {
        File.createTempFile(imageFileName, ".jpg", storageDir)
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

fun calculatePercentage(k: Int, n: Int): Double {
    require(k >= 0 && n > 0) { "Invalid input: k must be non-negative and n must be positive" }
    val percentage = (k.toDouble() / n.toDouble()) * 100.0
    return DecimalFormat("#.##").format(percentage).toDouble()
}


