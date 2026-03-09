package id.ac.itera.choirunnisasy.myprofile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.profile_nisa
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ─── COLOR PALETTE ────────────────────────────────────────────────────────────
private val matchaDeep      = Color(0xFF3D5229)
private val matcha          = Color(0xFF5C7A3E)
private val matchaLight     = Color(0xFFA8C57E)
private val matchaPale      = Color(0xFFD4E8B8)
private val strawberry      = Color(0xFFC0392B)
private val strawberryPale  = Color(0xFFFDECEA)
private val cream           = Color(0xFFFAF6F0)
private val creamDark       = Color(0xFFF0E8DC)
private val warmWhite       = Color(0xFFFFFDF9)
private val charcoal        = Color(0xFF1A1A1A)

// ─── ROOT COMPOSABLE ──────────────────────────────────────────────────────────
@Composable
fun App() {
    MaterialTheme {
        var headerVisible  by remember { mutableStateOf(false) }
        var contentVisible by remember { mutableStateOf(false) }
        var cardsVisible   by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            headerVisible  = true
            delay(500)
            contentVisible = true
            delay(300)
            cardsVisible   = true
        }

        val infiniteTransition = rememberInfiniteTransition(label = "bg")
        val bgAlpha by infiniteTransition.animateFloat(
            initialValue  = 0.3f,
            targetValue   = 0.6f,
            animationSpec = infiniteRepeatable(
                animation  = tween(4000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bgPulse"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(cream, creamDark, Color(0xFFE8D8C8))
                    )
                )
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(matchaPale.copy(alpha = bgAlpha * 0.8f), Color.Transparent),
                        radius = 600f
                    ),
                    radius = 600f,
                    center = Offset(size.width * 0.95f, -80f)
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(strawberryPale.copy(alpha = bgAlpha * 0.7f), Color.Transparent),
                        radius = 400f
                    ),
                    radius = 400f,
                    center = Offset(-60f, size.height * 0.85f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // 1. Header
                ProfileHeader(visible = headerVisible)

                // 2. Name Card
                NameCard(
                    visible  = contentVisible,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .offset(y = (-52).dp)
                )

                // 3. Contact Info
                Spacer(modifier = Modifier.height((-38).dp))

                SectionTitle(                                          // ProfileComponent.kt
                    title    = "Contact Information",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                )

                val infoList = listOf(
                    Triple(Icons.Rounded.Email,      "Email Address",    "choirunnisa.123140136@student.itera.ac.id"),
                    Triple(Icons.Rounded.Phone,      "Phone Number",     "+62 812 3456 7890"),
                    Triple(Icons.Rounded.LocationOn, "Current Location", "Bandar Lampung, Indonesia")
                )
                val infoColors = listOf(
                    Pair(matcha,             matchaPale),
                    Pair(strawberry,         strawberryPale),
                    Pair(Color(0xFFD4840A),  Color(0xFFFFF3E0))
                )

                infoList.forEachIndexed { index, (icon, label, value) ->
                    AnimatedVisibility(
                        visible = cardsVisible,
                        enter   = fadeIn(tween(500, delayMillis = index * 150)) +
                                slideInHorizontally(tween(500, delayMillis = index * 150)) { -60 }
                    ) {
                        InfoCard(                                       // ProfileComponent.kt
                            icon        = icon,
                            label       = label,
                            value       = value,
                            accentColor = infoColors[index].first,
                            bgColor     = infoColors[index].second,
                            modifier    = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }

                // 4. Skills
                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle(                                          // ProfileComponent.kt
                    title      = "Skills & Interests",
                    modifier   = Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
                    isReversed = true
                )

                AnimatedVisibility(
                    visible = cardsVisible,
                    enter   = fadeIn(tween(600, delayMillis = 500)) +
                            slideInVertically(tween(600, delayMillis = 500)) { 40 }
                ) {
                    SkillsSection(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }

                // 5. Button
                Spacer(modifier = Modifier.height(20.dp))

                AnimatedVisibility(
                    visible = cardsVisible,
                    enter   = fadeIn(tween(600, delayMillis = 700)) +
                            slideInVertically(tween(600, delayMillis = 700)) { 50 }
                ) {
                    ContactButton(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                    )
                }

                // 6. Footer
                FooterNote(                                            // ProfileComponent.kt
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 28.dp)
                )
            }
        }
    }
}

// ─── ProfileHeader ────────────────────────────────────────────────────────────
@Composable
private fun ProfileHeader(visible: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(RoundedCornerShape(bottomStart = 56.dp, bottomEnd = 56.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(matchaDeep, matcha, matchaLight),
                    start  = Offset(0f, 0f),
                    end    = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = Color.White.copy(alpha = 0.07f), radius = 180f,
                center = Offset(size.width - 30f, -40f))
            drawCircle(color = Color.White.copy(alpha = 0.05f), radius = 100f,
                center = Offset(40f, size.height - 30f))
            listOf(
                Offset(size.width * 0.15f, size.height * 0.25f),
                Offset(size.width * 0.85f, size.height * 0.65f),
                Offset(size.width * 0.70f, size.height * 0.15f),
                Offset(size.width * 0.25f, size.height * 0.75f),
            ).forEach { drawCircle(color = Color.White.copy(alpha = 0.2f), radius = 4f, center = it) }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(48.dp))
            AnimatedVisibility(
                visible = visible,
                enter   = fadeIn(tween(800)) + scaleIn(tween(800), initialScale = 0.5f)
            ) {
                ProfilePhotoWidget()
            }
        }
    }
}

// ─── ProfilePhotoWidget ───────────────────────────────────────────────────────
@Composable
private fun ProfilePhotoWidget() {
    val infiniteTransition = rememberInfiniteTransition(label = "ring")
    val rotation by infiniteTransition.animateFloat(
        initialValue  = 0f,
        targetValue   = 360f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing)),
        label         = "rotate"
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(160.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color  = Color.White.copy(alpha = 0.35f),
                radius = size.minDimension / 2,
                style  = androidx.compose.ui.graphics.drawscope.Stroke(
                    width      = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), phase = rotation)
                )
            )
        }

        Box(
            modifier = Modifier
                .size(138.dp)
                .clip(CircleShape)
                .border(4.dp, Color.White, CircleShape)
                .border(7.dp, strawberry, CircleShape)
                .padding(6.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(colors = listOf(matchaLight, matchaDeep))),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter            = painterResource(Res.drawable.profile_nisa),
                contentDescription = "Profile Photo",
                modifier           = Modifier.fillMaxSize(),
                contentScale       = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 4.dp, y = (-4).dp)
                .clip(CircleShape)
                .background(strawberry)
                .border(2.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🍓", fontSize = 14.sp)
        }
    }
}

// ─── NameCard ─────────────────────────────────────────────────────────────────
@Composable
private fun NameCard(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = visible,
        enter   = fadeIn(tween(700)) + slideInVertically(tween(700)) { 50 }
    ) {
        Card(
            modifier  = modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(28.dp),
            colors    = CardDefaults.cardColors(containerColor = warmWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            border    = BorderStroke(1.5.dp, matchaLight.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        Brush.horizontalGradient(colors = listOf(matchaLight, strawberry, matchaLight))
                    )
            )
            Column(
                modifier            = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Choirunnisa", fontSize = 26.sp, fontWeight = FontWeight.Black,
                    color = charcoal, letterSpacing = 0.3.sp)
                Text("Syawaldina",  fontSize = 22.sp, fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic, color = matchaDeep, letterSpacing = 0.3.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text       = "\"Where the calm of matcha meets\nthe sweetness of strawberry\"",
                    fontSize   = 13.sp,
                    color      = strawberry,
                    fontStyle  = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    textAlign  = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProfileTag(emoji = "🌿", text = "123140136",          isMatcha = true)   // ProfileComponent.kt
                    ProfileTag(emoji = "💻", text = "Teknik Informatika", isMatcha = false)  // ProfileComponent.kt
                }
            }
        }
    }
}

// ─── SkillsSection ────────────────────────────────────────────────────────────
@Composable
private fun SkillsSection(modifier: Modifier = Modifier) {
    val skills = listOf(
        Triple("🍵", "Compose UI", true),
        Triple("🍓", "Kotlin",     false),
        Triple("🌿", "Mobile Dev", true),
        Triple("☕", "Clean Code", false),
        Triple("🎨", "UI Design",  true),
        Triple("📱", "Android",    false),
    )

    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = warmWhite.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                skills.take(3).forEach { (e, l, m) ->
                    SkillChip(emoji = e, label = l, isMatcha = m)     // ProfileComponent.kt
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                skills.drop(3).forEach { (e, l, m) ->
                    SkillChip(emoji = e, label = l, isMatcha = m)     // ProfileComponent.kt
                }
            }
        }
    }
}

// ─── ContactButton ────────────────────────────────────────────────────────────
@Composable
private fun ContactButton(modifier: Modifier = Modifier) {
    Button(
        onClick        = { /* TODO */ },
        modifier       = modifier.height(60.dp),
        shape          = RoundedCornerShape(22.dp),
        colors         = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        elevation      = ButtonDefaults.buttonElevation(defaultElevation = 10.dp, pressedElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(colors = listOf(strawberry, Color(0xFFA93226), strawberry)),
                    shape = RoundedCornerShape(22.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("🍓", fontSize = 16.sp)
                Text("Contact Me", fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = Color.White, letterSpacing = 0.4.sp)
                Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(18.dp))
            }
        }
    }
}