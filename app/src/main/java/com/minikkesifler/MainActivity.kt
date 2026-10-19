package com.minikkesifler

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.random.Random

private val Sky = Color(0xFFEAF7FF)
private val Ink = Color(0xFF26344A)
private val Palette = listOf(Color(0xFFFFD166), Color(0xFF8ED081), Color(0xFF83C5F7), Color(0xFFFFA6C1), Color(0xFFC7B5F5), Color(0xFFFFB878))

class MainActivity : ComponentActivity() {
    private var tts: TextToSpeech? = null
    private var tone: ToneGenerator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tone = ToneGenerator(AudioManager.STREAM_MUSIC, 75)
        tts = TextToSpeech(this) { status -> if (status == TextToSpeech.SUCCESS) tts?.language = Locale("tr", "TR") }
        setContent { MinikKesifler { say(it) } }
    }
    private fun say(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "minik-kesif")
        tone?.startTone(ToneGenerator.TONE_PROP_ACK, 100)
    }
    override fun onDestroy() {
        tts?.stop(); tts?.shutdown(); tone?.release()
        super.onDestroy()
    }
}

private enum class Game(val title: String, val emoji: String, val subtitle: String) {
    HOME("Minik Keşifler", "🌈", "Birlikte öğrenelim!"),
    MATCH("Eşini Bul", "🧩", "Aynı olanları bul"),
    COLORS("Renkler", "🎨", "Söylenen rengi seç"),
    SHAPES("Şekiller", "🔺", "Şekilleri tanı"),
    ANIMALS("Hayvanlar", "🐥", "Hayvanları tanı"),
    NUMBERS("Sayılar", "🔢", "Bir, iki, üç!"),
    MUSIC("Müzik Zamanı", "🎵", "Dokun ve ritim oluştur")
}

@Composable
private fun MinikKesifler(say: (String) -> Unit) {
    var game by remember { mutableStateOf(Game.HOME) }
    var points by remember { mutableIntStateOf(0) }
    var targetColor by remember { mutableIntStateOf(Random.nextInt(6)) }
    var matchTarget by remember { mutableIntStateOf(Random.nextInt(3)) }
    var shapeTarget by remember { mutableIntStateOf(Random.nextInt(4)) }
    var animalTarget by remember { mutableIntStateOf(Random.nextInt(4)) }
    var numberTarget by remember { mutableIntStateOf(Random.nextInt(3) + 1) }
    var feedback by remember { mutableStateOf("Haydi başlayalım!") }
    val colors = listOf("Sarı", "Yeşil", "Mavi", "Pembe", "Mor", "Turuncu")
    val shapes = listOf("🔴", "🟦", "🔺", "⭐")
    val shapeNames = listOf("Daire", "Kare", "Üçgen", "Yıldız")
    val animals = listOf("🐱", "🐶", "🐰", "🐸")
    val animalNames = listOf("Kedi", "Köpek", "Tavşan", "Kurbağa")
    fun correct(next: () -> Unit) {
        points++
        feedback = "Aferin! Harika yaptın! ⭐"
        say("Aferin! Harika yaptın!")
        next()
    }
    fun wrong() {
        feedback = "Bir daha deneyelim! 😊"
        say("Bir daha deneyelim")
    }

    MaterialTheme {
        Surface(Modifier.fillMaxSize(), color = Sky) {
            Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    if (game != Game.HOME) Button(onClick = { game = Game.HOME; feedback = "Bir oyun seç!" }) { Text("← Geri", fontSize = 16.sp) }
                    else Text("🌈", fontSize = 34.sp)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(game.title, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                        Text(game.subtitle, fontSize = 15.sp, color = Ink.copy(alpha = .75f))
                    }
                    Text("⭐ $points", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink)
                }
                Spacer(Modifier.height(16.dp))
                if (game == Game.HOME) {
                    Text("Merhaba minik kaşif! 👋", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Ink, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(8.dp))
                    Text("Bir oyuna dokun. Her deneme yeni bir keşif!", fontSize = 17.sp, color = Ink, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(18.dp))
                    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 145.dp), modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(Game.values().filter { it != Game.HOME }) { item ->
                            GameTile(item) {
                                game = item
                                feedback = "Harika! Hadi deneyelim."
                                say(item.title)
                                when (item) {
                                    Game.COLORS -> { targetColor = Random.nextInt(6); say("${colors[targetColor]} rengini bul") }
                                    Game.MATCH -> matchTarget = Random.nextInt(3)
                                    Game.SHAPES -> { shapeTarget = Random.nextInt(4); say(shapeNames[shapeTarget]) }
                                    Game.ANIMALS -> { animalTarget = Random.nextInt(4); say(animalNames[animalTarget]) }
                                    Game.NUMBERS -> { numberTarget = Random.nextInt(3) + 1; say(numberTarget.toTurkish()) }
                                    else -> Unit
                                }
                            }
                        }
                    }
                    Button(onClick = { say("Merhaba minik kaşif! Bir oyun seç.") }, modifier = Modifier.fillMaxWidth().height(54.dp)) { Text("🔊  Beni dinle", fontSize = 19.sp) }
                } else {
                    Text(feedback, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(18.dp))
                    when (game) {
                        Game.MATCH -> {
                            val objects = listOf("🐻", "🚗", "🍎")
                            Text("Bu resmin eşini bul:", fontSize = 19.sp, color = Ink)
                            Text(objects[matchTarget], fontSize = 72.sp)
                            Spacer(Modifier.height(12.dp))
                            ChoiceGrid((objects + objects.shuffled()).shuffled()) { chosen ->
                                if (chosen == objects[matchTarget]) correct { matchTarget = Random.nextInt(3) } else wrong()
                            }
                        }
                        Game.COLORS -> {
                            Text("${colors[targetColor]} rengini bul!", fontSize = 23.sp, color = Ink)
                            Spacer(Modifier.height(14.dp))
                            ChoiceGrid(colors) { chosen -> if (chosen == colors[targetColor]) correct { targetColor = Random.nextInt(6); say("${colors[targetColor]} rengini bul") } else wrong() }
                        }
                        Game.SHAPES -> {
                            Text("${shapeNames[shapeTarget]} nerede?", fontSize = 23.sp, color = Ink)
                            Spacer(Modifier.height(14.dp))
                            ChoiceGrid(shapes) { chosen -> if (chosen == shapes[shapeTarget]) correct { shapeTarget = Random.nextInt(4); say(shapeNames[shapeTarget]) } else wrong() }
                        }
                        Game.ANIMALS -> {
                            Text("${animalNames[animalTarget]} hangisi?", fontSize = 23.sp, color = Ink)
                            Spacer(Modifier.height(14.dp))
                            ChoiceGrid(animals) { chosen -> if (chosen == animals[animalTarget]) correct { animalTarget = Random.nextInt(4); say(animalNames[animalTarget]) } else wrong() }
                        }
                        Game.NUMBERS -> {
                            Text("Kaç yıldız var?", fontSize = 23.sp, color = Ink)
                            Text((1..numberTarget).joinToString(" ") { "⭐" }, fontSize = 44.sp)
                            Spacer(Modifier.height(10.dp))
                            ChoiceGrid(listOf("1", "2", "3")) { chosen -> if (chosen == numberTarget.toString()) correct { numberTarget = Random.nextInt(3) + 1; say(numberTarget.toTurkish()) } else wrong() }
                        }
                        Game.MUSIC -> {
                            Text("Bir enstrümana dokun!", fontSize = 23.sp, color = Ink)
                            Spacer(Modifier.height(16.dp))
                            ChoiceGrid(listOf("🥁", "🎹", "🔔", "👏")) { chosen ->
                                say(when (chosen) { "🥁" -> "Güm güm!"; "🎹" -> "Ding!"; "🔔" -> "Çın çın!"; else -> "Şak şak!" })
                                feedback = "Harika ritim! $chosen"
                                points++
                            }
                        }
                        Game.HOME -> Unit
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { say(feedback) }, modifier = Modifier.fillMaxWidth().height(54.dp)) { Text("🔊  Dinle", fontSize = 19.sp) }
                }
            }
        }
    }
}

private fun Int.toTurkish(): String = when (this) { 1 -> "Bir"; 2 -> "İki"; else -> "Üç" }

@Composable
private fun GameTile(game: Game, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().height(142.dp).clickable(onClick = onClick), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Palette[game.ordinal % Palette.size]), elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)) {
        Column(Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(game.emoji, fontSize = 42.sp)
            Spacer(Modifier.height(6.dp))
            Text(game.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ChoiceGrid(options: List<String>, onChoice: (String) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxWidth().height(260.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(options) { option ->
            Card(modifier = Modifier.fillMaxWidth().height(112.dp).clickable { onChoice(option) }, shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Palette[(option.hashCode() and Int.MAX_VALUE) % Palette.size])) {
                Box(Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
                    Text(option, fontSize = if (option.length > 2) 26.sp else 48.sp, fontWeight = FontWeight.Bold, color = Ink, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
