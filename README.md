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


## APK derleme ve indirme

Her `main` dalı gönderiminde GitHub Actions, Android debug APK'sını derler ve `minik-kesifler-debug-apk` adlı bir artifact olarak 14 gün saklar.

1. [Actions derlemelerini aç](https://github.com/M93hasan/-mer/actions/workflows/android.yml).
2. En üstteki başarılı **Android APK** çalıştırmasını seç.
3. **Artifacts** bölümündeki `minik-kesifler-debug-apk` dosyasını indir.
4. ZIP dosyasını açıp `app-debug.apk` dosyasını Android cihazına aktar ve kurulum için cihazın istediği izni ver.

Bu dosya test amaçlı debug APK'dır; mağazaya dağıtılacak imzalı release APK/AAB değildir.

## Bilinen sınırlar — 19 Ekim 2026

- APK derlemesi GitHub Actions üzerinde başarılıdır; gerçek telefon/tablet testi henüz doğrulanmamıştır.
- Mevcut uygulama temel oyun prototipidir. Ebeveyn paneli, günlük ekran süresi sınırı ve kalıcı ilerleme kaydı henüz uygulanmış özellikler olarak kabul edilmemelidir.
- Müzik bölümü şu an gerçek enstrüman kayıtları değil, kısa metin-konuşma ve sistem tonu efektleri kullanır.
- Uzaktan içerik yönetim sunucusu ve web yönetim paneli bu depoda henüz mevcut değildir.
- Çocukla kullanmadan önce cihaz üzerinde dokunma alanları, Türkçe TTS, ses seviyesi, ekran yönü ve çevrimdışı açılış test edilmelidir.
