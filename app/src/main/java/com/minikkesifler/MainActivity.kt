package com.minikkesifler

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.random.Random

private val Ink = Color(0xFF25345A)
private val Muted = Color(0xFF69789A)
private val Cream = Color(0xFFFFFBF4)
private val Coral = Color(0xFFFF8066)
private val Lilac = Color(0xFFB8A4F7)
private val Mint = Color(0xFF8EDFC1)
private val Sky = Color(0xFF8ACDF5)
private val Sun = Color(0xFFFFD36E)
private val Pink = Color(0xFFFFB6CD)
private val tileColors = listOf(Color(0xFFFFE4D9), Color(0xFFE3F6EA), Color(0xFFE1F1FF), Color(0xFFFFF1C9), Color(0xFFF0E8FF), Color(0xFFFFE5F0))

class MainActivity : ComponentActivity() {
    private var tts: TextToSpeech? = null
    private var tone: ToneGenerator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tone = ToneGenerator(AudioManager.STREAM_MUSIC, 65)
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale("tr", "TR"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.language = Locale.getDefault()
                }
            }
        }
        val prefs = getSharedPreferences("minik_kesifler", MODE_PRIVATE)
        setContent {
            MinikKesifler(
                initialPoints = prefs.getInt("points", 0),
                initialSound = prefs.getBoolean("sound", true),
                onPointsChanged = { prefs.edit().putInt("points", it).apply() },
                onSoundChanged = { prefs.edit().putBoolean("sound", it).apply() },
                say = { speak(it) }
            )
        }
    }

    private fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "minik-kesif")
        tone?.startTone(ToneGenerator.TONE_PROP_ACK, 75)
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        tone?.release()
        super.onDestroy()
    }
}

private enum class Game(val title: String, val emoji: String, val subtitle: String, val color: Color) {
    ANIMALS("Hayvan dostlar", "🦁", "Sesleri ve isimleri keşfet", Color(0xFFFFE4D9)),
    COLORS("Renk bahçesi", "🎨", "Renkleri bul ve öğren", Color(0xFFE1F1FF)),
    SHAPES("Şekil treni", "🔺", "Şekilleri tanı", Color(0xFFF0E8FF)),
    NUMBERS("Minik sayılar", "🔢", "Birlikte sayalım", Color(0xFFFFF1C9)),
    MATCH("Eşini bul", "🧩", "Aynı resimleri eşleştir", Color(0xFFE3F6EA)),
    VEHICLES("Taşıtlar", "🚂", "Neler hareket ediyor?", Color(0xFFFFE5F0)),
    FEELINGS("Duygular", "😊", "Yüz ifadelerini tanı", Color(0xFFE1F1FF)),
    MUSIC("Ritim zamanı", "🥁", "Dokun, dinle, eğlen", Color(0xFFFFF1C9))
}

@Composable
private fun MinikKesifler(
    initialPoints: Int,
    initialSound: Boolean,
    onPointsChanged: (Int) -> Unit,
    onSoundChanged: (Boolean) -> Unit,
    say: (String) -> Unit
) {
    var game by remember { mutableStateOf<Game?>(null) }
    var points by remember { mutableIntStateOf(initialPoints) }
    var soundOn by remember { mutableStateOf(initialSound) }
    var parentGate by remember { mutableStateOf(false) }
    var parentAnswer by remember { mutableStateOf("") }
    var parentOpen by remember { mutableStateOf(false) }
    var feedback by remember { mutableStateOf("Bugün harika keşifler yapacağız!") }
    var round by remember { mutableIntStateOf(1) }
    var target by remember { mutableIntStateOf(0) }
    var target2 by remember { mutableIntStateOf(1) }
    var completed by remember { mutableIntStateOf(0) }

    val colors = listOf("Kırmızı", "Sarı", "Mavi", "Yeşil", "Mor", "Turuncu")
    val colorValues = listOf(Color(0xFFFF5E67), Color(0xFFFFD166), Color(0xFF58A9F5), Color(0xFF64CDA0), Color(0xFFAC8CF5), Color(0xFFFFA64D))
    val shapes = listOf("🔴", "🟦", "🔺", "⭐", "❤️")
    val shapeNames = listOf("Daire", "Kare", "Üçgen", "Yıldız", "Kalp")
    val animals = listOf("🐱", "🐶", "🐰", "🐸", "🦁", "🐼")
    val animalNames = listOf("Kedi", "Köpek", "Tavşan", "Kurbağa", "Aslan", "Panda")
    val vehicles = listOf("🚗", "🚂", "🚁", "🚲", "🚜", "🚀")
    val vehicleNames = listOf("Araba", "Tren", "Helikopter", "Bisiklet", "Traktör", "Roket")
    val feelings = listOf("😊", "😂", "😴", "😢", "😮", "🥰")
    val feelingNames = listOf("Mutlu", "Kahkahalı", "Uykulu", "Üzgün", "Şaşkın", "Sevgi dolu")

    fun sayIfOn(text: String) { if (soundOn) say(text) }
    fun newRound() {
        round++
        target = Random.nextInt(6)
        target2 = Random.nextInt(6)
        feedback = listOf("Harika gidiyorsun!", "Yeni bir keşfe hazır mısın?", "Birlikte deneyelim!", "Süpersin minik kaşif!").random()
    }
    fun correct() {
        points++
        completed++
        onPointsChanged(points)
        feedback = listOf("Yaşasın! Doğru bildin! ⭐", "Şahane! Harikasın! 🎉", "Aferin sana! 🌟", "Başardın! Beşlik çakalım! 🙌").random()
        sayIfOn(feedback.replace(Regex("[^\\p{L}\\p{N} !]"), ""))
        newRound()
    }
    fun tryAgain() {
        feedback = "Bir daha deneyelim, yapabilirsin 💛"
        sayIfOn("Bir daha deneyelim. Yapabilirsin.")
    }

    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF6D78D9), onPrimary = Color.White, background = Cream, surface = Color.White)) {
        Box(
            Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFFFFF9EE), Color(0xFFEAF6FF), Color(0xFFF7EEFF))))
        ) {
            Column(
                Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(horizontal = 18.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(54.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFFFFD36E), Color(0xFFFF9D90)))),
                        contentAlignment = Alignment.Center
                    ) { Text("🌈", fontSize = 31.sp) }
                    Spacer(Modifier.width(11.dp))
                    Column(Modifier.weight(1f)) {
                        Text(if (game == null) "Minik Keşifler" else game!!.title, fontSize = 23.sp, fontWeight = FontWeight.Black, color = Ink)
                        Text(if (game == null) "Oyna • öğren • keşfet" else "Minik adımlar, büyük keşifler", fontSize = 13.sp, color = Muted)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("⭐ $points", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                        Text("yıldızın", fontSize = 11.sp, color = Muted)
                    }
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier.size(42.dp).clip(CircleShape).background(Color.White).clickable {
                            parentGate = true
                            parentAnswer = ""
                        },
                        contentAlignment = Alignment.Center
                    ) { Text("🔒", fontSize = 20.sp) }
                }

                Spacer(Modifier.height(16.dp))

                if (game == null) {
                    Box(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp))
                            .background(Brush.linearGradient(listOf(Color(0xFF9D9AFB), Color(0xFF78C9EE))))
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Merhaba minik kaşif! 👋", fontSize = 23.sp, fontWeight = FontWeight.Black, color = Color.White)
                                Spacer(Modifier.height(6.dp))
                                Text("Bugün neler keşfedeceğiz?", fontSize = 16.sp, color = Color.White)
                                Spacer(Modifier.height(12.dp))
                                Row(
                                    Modifier.clip(RoundedCornerShape(50)).background(Color.White.copy(alpha = .23f)).clickable { sayIfOn("Merhaba minik kaşif! Bir oyun seç ve eğlenmeye başla.") }.padding(horizontal = 14.dp, vertical = 9.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("🔊", fontSize = 16.sp)
                                    Spacer(Modifier.width(7.dp))
                                    Text("Beni dinle", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            }
                            Text("🦊", fontSize = 72.sp)
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Oyun dünyaları", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                        Spacer(Modifier.weight(1f))
                        Text("8 eğlenceli oyun", fontSize = 12.sp, color = Muted)
                    }
                    Spacer(Modifier.height(10.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(11.dp),
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        contentPadding = PaddingValues(bottom = 10.dp)
                    ) {
                        items(Game.values().toList(), key = { it.name }) { item ->
                            GameCard(item) {
                                game = item
                                target = Random.nextInt(6)
                                target2 = Random.nextInt(6)
                                feedback = "Hazır mısın? Haydi başlayalım!"
                                sayIfOn(when (item) {
                                    Game.ANIMALS -> "Hayvan dostlar oyununa hoş geldin."
                                    Game.COLORS -> "Renk bahçesine hoş geldin."
                                    Game.SHAPES -> "Şekil trenine hoş geldin."
                                    Game.NUMBERS -> "Haydi birlikte sayalım."
                                    Game.MATCH -> "Eşini bulmaya hazır mısın?"
                                    Game.VEHICLES -> "Taşıtları keşfedelim."
                                    Game.FEELINGS -> "Duyguları öğrenelim."
                                    Game.MUSIC -> "Ritim zamanı. Dokun ve eğlen."
                                })
                            }
                        }
                    }
                    Row(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Color.White.copy(alpha = .9f)).padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🏆", fontSize = 30.sp)
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Küçük başarılar", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Ink)
                            Text("$completed doğru cevap • Her gün yeni bir şey öğren!", fontSize = 12.sp, color = Muted)
                        }
                        Text("✨", fontSize = 26.sp)
                    }
                } else {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = { game = null; feedback = "Bir oyun seç, keşfe devam et!" }) { Text("← Oyunlara dön", fontSize = 15.sp, fontWeight = FontWeight.Bold) }
                        Spacer(Modifier.weight(1f))
                        Text("Tur $round", color = Muted, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(Modifier.height(6.dp))
                    Box(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(25.dp)).background(Color.White.copy(alpha = .93f)).padding(18.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text(feedback, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Ink, textAlign = TextAlign.Center)
                            Spacer(Modifier.height(15.dp))
                            when (game!!) {
                                Game.COLORS -> {
                                    Text("Bu rengi bul!", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Spacer(Modifier.height(12.dp))
                                    Box(Modifier.size(86.dp).clip(CircleShape).background(colorValues[target]), contentAlignment = Alignment.Center) {
                                        Text("?", fontSize = 38.sp, fontWeight = FontWeight.Black, color = Color.White)
                                    }
                                    Text(colors[target], fontSize = 19.sp, color = Ink, fontWeight = FontWeight.Bold)
                                    Spacer(Modifier.height(15.dp))
                                    OptionGrid(colors, textSize = 17, optionColor = { colorValues[colors.indexOf(it)] }) {
                                        if (it == colors[target]) correct() else tryAgain()
                                    }
                                }
                                Game.ANIMALS -> {
                                    Text("Hangi hayvan bu?", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Text(animals[target], fontSize = 76.sp)
                                    Text(animalNames[target], fontSize = 18.sp, color = Muted)
                                    Spacer(Modifier.height(12.dp))
                                    OptionGrid(animals, textSize = 40, optionColor = { Color(0xFFFFE4D9) }) {
                                        if (it == animals[target]) correct() else tryAgain()
                                    }
                                }
                                Game.SHAPES -> {
                                    Text("Aynı şekli bul!", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Text(shapes[target % shapes.size], fontSize = 76.sp)
                                    Text(shapeNames[target % shapeNames.size], fontSize = 18.sp, color = Muted)
                                    Spacer(Modifier.height(12.dp))
                                    OptionGrid(shapes, textSize = 40, optionColor = { Color(0xFFF0E8FF) }) {
                                        if (it == shapes[target % shapes.size]) correct() else tryAgain()
                                    }
                                }
                                Game.NUMBERS -> {
                                    val count = target % 5 + 1
                                    Text("Kaç tane yıldız var?", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Spacer(Modifier.height(14.dp))
                                    Text((1..count).joinToString(" ") { "⭐" }, fontSize = 36.sp, textAlign = TextAlign.Center)
                                    Spacer(Modifier.height(16.dp))
                                    OptionGrid(listOf("1", "2", "3", "4", "5"), textSize = 28, optionColor = { Sun.copy(alpha = .45f) }) {
                                        if (it == count.toString()) correct() else tryAgain()
                                    }
                                }
                                Game.MATCH -> {
                                    val matchEmoji = listOf("🐻", "🚗", "🍎", "🐟", "🌼", "🎈")
                                    val wanted = matchEmoji[target]
                                    Text("Resmin eşini bul!", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Text(wanted, fontSize = 76.sp)
                                    Text("Aynısından bir tane daha var mı?", fontSize = 15.sp, color = Muted, textAlign = TextAlign.Center)
                                    Spacer(Modifier.height(12.dp))
                                    OptionGrid((matchEmoji.filter { it != wanted }.shuffled().take(3) + wanted).shuffled(), textSize = 42, optionColor = { Mint.copy(alpha = .35f) }) {
                                        if (it == wanted) correct() else tryAgain()
                                    }
                                }
                                Game.VEHICLES -> {
                                    Text("Bu taşıtı bul!", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Text(vehicles[target], fontSize = 76.sp)
                                    Text(vehicleNames[target], fontSize = 18.sp, color = Muted)
                                    Spacer(Modifier.height(12.dp))
                                    OptionGrid(vehicles, textSize = 40, optionColor = { Sky.copy(alpha = .35f) }) {
                                        if (it == vehicles[target]) correct() else tryAgain()
                                    }
                                }
                                Game.FEELINGS -> {
                                    Text("Yüz nasıl hissediyor?", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Text(feelings[target], fontSize = 78.sp)
                                    Text(feelingNames[target], fontSize = 18.sp, color = Muted)
                                    Spacer(Modifier.height(12.dp))
                                    OptionGrid(feelings, textSize = 40, optionColor = { Pink.copy(alpha = .4f) }) {
                                        if (it == feelings[target]) correct() else tryAgain()
                                    }
                                }
                                Game.MUSIC -> {
                                    Text("Bir ritim seç!", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                                    Text("🥁  🎹  🔔", fontSize = 45.sp)
                                    Spacer(Modifier.height(14.dp))
                                    OptionGrid(listOf("🥁 Davul", "🎹 Piyano", "🔔 Zil", "👏 Alkış"), textSize = 18, optionColor = { Sun.copy(alpha = .5f) }) {
                                        feedback = when {
                                            it.contains("Davul") -> "Güm güm! Harika ritim! 🥁"
                                            it.contains("Piyano") -> "Ding ding! Ne güzel ses! 🎹"
                                            it.contains("Zil") -> "Çın çın! Bir daha çal! 🔔"
                                            else -> "Şak şak! Alkış sana! 👏"
                                        }
                                        sayIfOn(feedback.replace(Regex("[^\\p{L}\\p{N} !]"), ""))
                                        points++
                                        completed++
                                        onPointsChanged(points)
                                    }
                                }
                            }
                            Spacer(Modifier.height(14.dp))
                            Button(
                                onClick = { sayIfOn(feedback.replace(Regex("[^\\p{L}\\p{N} !]"), "")) },
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF747DE0))
                            ) { Text(if (soundOn) "🔊  Tekrar dinle" else "💛  Harika gidiyorsun!", fontSize = 17.sp, fontWeight = FontWeight.Bold) }
                        }
                    }
                }
            }
        }
    }

    if (parentGate) {
        AlertDialog(
            onDismissRequest = { parentGate = false },
            containerColor = Cream,
            title = { Text("Ebeveyn alanı 🔒", color = Ink, fontWeight = FontWeight.ExtraBold) },
            text = {
                Column {
                    Text("Bu bölüm büyükler içindir. Devam etmek için 2 + 3 kaç eder?", color = Ink)
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(value = parentAnswer, onValueChange = { parentAnswer = it }, label = { Text("Cevap") }, singleLine = true)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (parentAnswer.trim() == "5") { parentGate = false; parentOpen = true }
                    else parentAnswer = ""
                }) { Text("Devam et") }
            },
            dismissButton = { TextButton(onClick = { parentGate = false }) { Text("Kapat") } }
        )
    }

    if (parentOpen) {
        AlertDialog(
            onDismissRequest = { parentOpen = false },
            containerColor = Cream,
            title = { Text("Ebeveyn ayarları ⚙️", color = Ink, fontWeight = FontWeight.ExtraBold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Minik Kaşif'in oyun deneyimini buradan yönetebilirsin.", color = Muted)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Sesli yönlendirme", color = Ink, fontWeight = FontWeight.Bold)
                            Text("Türkçe konuşma ve ses efektleri", color = Muted, fontSize = 12.sp)
                        }
                        Switch(checked = soundOn, onCheckedChange = { soundOn = it; onSoundChanged(it) })
                    }
                    HorizontalDivider()
                    Text("⭐ Toplanan yıldız: $points", color = Ink, fontWeight = FontWeight.Bold)
                    Text("Doğru cevap: $completed", color = Ink)
                    Text("Not: Uygulama çevrimdışı çalışır. Ses kalitesi cihazın Türkçe konuşma desteğine bağlıdır.", color = Muted, fontSize = 12.sp)
                }
            },
            confirmButton = { TextButton(onClick = { parentOpen = false }) { Text("Tamam") } },
            dismissButton = {
                TextButton(onClick = { points = 0; completed = 0; onPointsChanged(0); parentOpen = false }) { Text("Yıldızları sıfırla") }
            }
        )
    }
}

@Composable
private fun GameCard(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(154.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = game.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = .9f))
    ) {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 13.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier.size(68.dp).clip(CircleShape).background(Color.White.copy(alpha = .78f)),
                contentAlignment = Alignment.Center
            ) { Text(game.emoji, fontSize = 42.sp) }
            Spacer(Modifier.height(8.dp))
            Text(game.title, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Ink, textAlign = TextAlign.Center)
            Spacer(Modifier.height(3.dp))
            Text(game.subtitle, fontSize = 11.sp, color = Muted, textAlign = TextAlign.Center, maxLines = 1)
        }
    }
}

@Composable
private fun OptionGrid(
    options: List<String>,
    textSize: Int,
    optionColor: (String) -> Color,
    onChoice: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().heightIn(max = 340.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 2.dp)
    ) {
        items(options, key = { it }) { option ->
            Card(
                modifier = Modifier.fillMaxWidth().height(82.dp).clickable { onChoice(option) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = optionColor(option)),
                border = BorderStroke(2.dp, Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Box(Modifier.fillMaxSize().padding(6.dp), contentAlignment = Alignment.Center) {
                    Text(option, fontSize = textSize.sp, fontWeight = FontWeight.Bold, color = Ink, textAlign = TextAlign.Center, lineHeight = (textSize + 4).sp)
                }
            }
        }
    }
}
