# Minik Keşifler 🌈

2–3 yaş grubu için Türkçe Android eğitici oyun prototipi.

## İlk sürüm
- Eşini Bul
- Renkleri öğrenme
- Şekilleri tanıma
- Hayvanları tanıma
- 1–3 sayıları
- Dokunarak ritim ve ses keşfi
- Türkçe Android Metin Okuma (TTS) desteği
- Çevrimdışı çalışır; reklam, uygulama içi satın alma, analitik ve takip SDK'sı içermez

## Android Studio ile çalıştırma
1. Depoyu klonla veya ZIP olarak indir.
2. Android Studio'da proje klasörünü aç.
3. JDK 17 seçili olsun ve Gradle senkronizasyonunu tamamla.
4. Android 8.0 (API 26) veya üzeri cihaz/emülatör bağla.
5. **Run** düğmesine bas.
6. APK için **Build > Build Bundle(s) / APK(s) > Build APK(s)** seç.

## Teknik bilgiler
- Kotlin, Jetpack Compose, Material 3
- Min SDK 26 / Target SDK 35
- Uygulama kimliği: `com.minikkesifler`

## Durum
Bu kaynak kod ilk oynanabilir prototiptir; derlenmiş APK/AAB değildir ve henüz gerçek cihazda test edilmemiştir. TTS ses kalitesi cihazın Türkçe ses paketine bağlıdır. Ebeveyn alanı ve güvenli uzaktan içerik yönetimi sonraki geliştirme adımlarıdır.
