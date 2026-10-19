# Minik Keşifler — Ana Geliştirme Promptu

## Rol ve hedef
Kıdemli Android geliştiricisi, çocuk dostu oyun tasarımcısı, yazılım mimarı, güvenlik uzmanı ve test mühendisi olarak çalış. **Minik Keşifler** adlı 2–3 yaş çocuklara yönelik, Türkçe, reklamsız, güvenli, çevrimdışı çalışabilen ve gelecekte uzaktan içeriklerle geliştirilebilen bir Android eğitim oyunu oluştur.

Yalnızca plan, maket veya demo üretme. Gerçek çalışan kodu, oyunları, sesleri, kayıt sistemini, ebeveyn bölümünü, uzaktan içerik yönetimini, testleri ve kurulum belgelerini geliştir. Yapılmamış özellikleri tamamlandı gibi gösterme.

## 1. Platform ve mimari
- Android telefon ve tabletleri destekle; öncelik yatay tablet, telefon ve dikey ekranlarda da uyumlu arayüz.
- Kotlin + Jetpack Compose, modüler ve test edilebilir mimari kullan.
- Android 8.0+ hedefini kütüphane uyumluluğuyla doğrula.
- Bağımlılık sürümlerini sabitle; kaynak kodunu Android Studio'da açılabilir ve derlenebilir tut.
- Oyun mantığını, içerik tanımlarını, sesleri, görselleri, yerel kayıtları ve uzaktan içerik hizmetini birbirinden ayır.
- Uygulama internet olmadan açılmalı ve mevcut oyunları oynatabilmeli.

## 2. Görsel tasarım ve ana ekran
Özgün, sevimli, sakin ve kaliteli bir çocuk arayüzü oluştur. Bir ayıcık rehber kullan. Büyük dokunmatik alanlar, net simgeler, Türkçe sesli yönergeler, yumuşak animasyonlar ve okunaklı metinler kullan. Karmaşık menülerden, hızlı flaşlardan, aşırı uyaranlardan ve küçük butonlardan kaçın.

Ana ekranda şu çalışan bölümler olsun:
1. Eşleştirme
2. Şekiller
3. Renkler
4. Hayvanlar ve sesleri
5. Sayılar (öncelikle 1–3)
6. Müzik ve dans

Henüz uygulanmamış bölümleri çalışanmış gibi gösterme. Ekran boyutlarına ve piksel yoğunluklarına uyum sağla.

## 3. Gerçek oyun içerikleri
**Eşleştirme:** Aynı hayvanı, şekli veya nesneyi eşleştirme; başlangıçta iki seçenek; dokunma ile alternatif etkileşim; doğru cevapta kısa animasyon ve olumlu ses; yanlış cevapta nazik ipucu ve yeniden deneme.

**Şekiller:** Daire, kare, üçgen, yıldız; şeklin adını Türkçe seslendir; aynı şekli bulma ve uygun yere yerleştirme.

**Renkler:** Kırmızı, mavi, sarı, yeşil ve diğer temel renkler; “Kırmızı topu bul” gibi görevler; rengi nesne ve sesli isimle de destekle.

**Hayvanlar:** Kedi, köpek, inek, koyun, ördek, aslan, fil ve benzeri hayvanlar; dokununca adı ve sesi; “Köpeği bul” gibi basit görevler.

**Sayılar:** Önce 1, 2 ve 3; sayıları somut nesnelerle ilişkilendir; basit sayma ve miktar eşleştirme görevleri.

**Müzik ve dans:** Ayıcık dansı, basit ritimler ve telif hakkına uygun melodiler; müzik ve efekt sesleri ayrı ayrı kapatılabilsin.

İçerik altyapısı daha sonra meyveler, günlük nesneler, büyük-küçük, az-çok, duygular, yön/konum ve günlük yaşam alışkanlıkları gibi modüllerin eklenmesine hazır olsun. Bu yaş grubunda görevleri kısa, tekrarlanabilir ve baskısız tut. Süre baskısı, kaybetme, ceza, sıralama, can sistemi veya rekabet kullanma.

## 4. Türkçe ses ve erişilebilirlik
- Doğal ve anlaşılır Türkçe yönergeler kullan.
- Temel sesleri uygulamaya dahil et; internet ve harici metin-konuşma hizmeti olmadan çalışsın.
- Seslerin üst üste binmesini önle; uygulama arka plana gidince sesleri doğru yönet.
- Sessiz mod, ses seviyesi, müzik ve efekt ayarları sun.
- Görsel geri bildirim yalnızca sese bağımlı olmasın.
- Telif hakkı belirsiz ses ve görseller kullanma.

## 5. Ebeveyn alanı
Çocuk ekranından ayrılmış, yetişkin doğrulaması bulunan bir ebeveyn paneli yap. Basit bir simgeye dokunmayı tek güvenlik önlemi sayma. Şunlar olsun:
- 5/10/15/30 dakika ve özel günlük süre sınırı.
- Ses, müzik ve efekt ayarları.
- Oyun bölümlerini etkinleştirme/devre dışı bırakma.
- Son oynanan etkinlikler ve sınırlı ilerleme özeti.
- İsteğe bağlı yerel profiller.
- İçerik indirme/güncelleme durumu ve depolama yönetimi.
- Gizlilik ve yerel verileri silme seçenekleri.
- Uygulama sürümü, güvenli hata raporu dışa aktarma.
- Uzaktan güncellemeleri açıp kapatma ve güncelleme kontrolü.

Çocuğun verilerini varsayılan olarak cihazda tut. Gelişim veya tıbbi değerlendirme iddiasında bulunma.

## 6. Güvenlik ve gizlilik
- Reklam, uygulama içi satın alma, sohbet, sosyal özellik ve harici çocuk bağlantıları ekleme.
- Gereksiz izin isteme; konum, kamera, mikrofon ve rehber erişimi isteme.
- Reklam takibi veya gereksiz analiz SDK'ları kullanma.
- HTTPS, güvenli kimlik doğrulama, yetkilendirme, oran sınırlaması ve denetim kayıtları uygula.
- Sunucu anahtarlarını APK'ya veya kaynak koduna koyma.
- İmzalama anahtarlarını depoya ekleme.
- Çocuklara yönelik mağaza ve gizlilik gerekliliklerini belgele.
- İndirilen içeriklerin dosya türünü, boyutunu, şemasını ve bütünlüğünü doğrula.
- Sunucudan çalıştırılabilir kod, DEX/JAR veya keyfî betik indirip çalıştırma. Uzaktan güncelleme yalnızca önceden tanımlı içerik şemalarıyla sınırlı olsun.

## 7. Çevrimdışı çalışma ve yerel kayıt
Açılış, menü, yayınlanmış oyunlar, temel ses/görseller, ebeveyn ayarları, süre sınırı ve ilerleme internet olmadan çalışsın. İlerleme ve tercihler yerel veritabanında saklansın. İnternet kesilirse uygulama çökmesin; son geçerli içerik paketini kullanmaya devam etsin. İçerik güncellemesi mevcut çalışan içeriği bozmasın.

## 8. Uzaktan içerik güncelleme — zorunlu
Sistemi üç parçaya ayır:

### Android istemcisi
- Oyun motoru, yerel içerik, yerel veritabanı ve ses/görseller.
- İçerik sürüm denetimi, güvenli indirme, bütünlük doğrulama ve hatalı güncellemeden geri dönüş.
- Ağ yokken son geçerli içerikle çalışmaya devam etme.

### İçerik API'si ve depolama
- HTTPS üzerinden sürüm manifesti ve içerik paketleri sun.
- Yeni hayvan, nesne, ses, görsel, görev dizisi, eğitim içeriği ve güvenli yapılandırma yayımlanabilsin.
- Uygulama sürümü uyumluluğu, içerik sürümü, dil, yayın durumu ve geri çekme bilgileri yönetilsin.
- Uygun maliyetli, sürdürülebilir bir altyapı seç; gerçek ücretleri, limitleri ve kurulum adımlarını belgele.
- API anahtarlarını ve yönetici sırlarını güvenli sunucu ortamında tut.

### Web yönetim paneli
Güvenli girişli, tarayıcıdan kullanılabilen yönetim paneli geliştir. İçerik oluşturma/düzenleme, ses ve görsel yükleme, önizleme, test ortamına yayınlama, canlıya alma, yayından kaldırma, sürüm geçmişi, önceki sürüme dönme, uyumluluk kontrolü ve denetim kayıtları olsun.

### İçerik yayınlama kuralları
- Önce test ortamında doğrula, sonra canlıya al.
- İçerik paketleri atomik etkinleştirilsin; yarım indirme çalışan içeriği değiştirmesin.
- İmzayı veya güvenilir bütünlük özetini doğrula.
- Geçersiz veya uyumsuz paketi reddet.
- Güncelleme başarısızsa son geçerli sürüme dön.
- Uzaktan ayarlar güvenli varsayılanlara sahip olsun.
- Yeni yerel kod veya yeni Android yeteneği gerekiyorsa yeni APK/AAB sürümü gerektiğini belirt. Yeni içerik uzaktan güncellenebilir; keyfî kod uzaktan yüklenemez.
- Google Play üzerinden dağıtımı sessiz, izinsiz APK güncellemesine dayandırma.

Her içerik manifestinde paket kimliği, sürüm, içerik türü, desteklenen uygulama sürümü, dil, dosya listesi, boyutlar, bütünlük özetleri, görev verileri, seslendirme metinleri, yayın durumu ve zaman bilgileri bulunsun. Şemayı sürümlendir; bilinmeyen alanları güvenli ele al.

## 9. Test ve kalite
Birim, entegrasyon ve kritik kullanıcı akışı testleri yaz. Şunları doğrula:
- Altı temel oyun akışı ve görev tamamlama.
- Dokunma, ses sırası ve ses durdurma.
- Arka plana geçiş ve geri dönüş.
- Farklı ekran boyutları ve yönelimler.
- İnternetsiz açılış ve oyun sırasında bağlantının kesilmesi.
- Başarısız indirme, bozuk dosya ve geçersiz içerik şeması.
- Güncelleme sonrası veri bütünlüğü ve eski sürüme dönüş.
- Ebeveyn erişim kontrolü ve süre sınırı.
- Uygulamanın kapanıp açılması.
- Düşük bellek ve performans.
- Gereksiz izinler ve güvenlik açıkları.

Mümkün olan testleri gerçekten çalıştır; gerçek cihazda test edemediğin noktaları açıkça raporla. Hataları düzeltip testleri yeniden çalıştır.

## 10. Teslim edilecekler
- Android Studio kaynak projesi ve Gradle yapılandırması.
- Çalışan oyunlar, içerik ve gerekli telif uyumlu ses/görseller.
- Yerel veritabanı şeması ve geçişler.
- İçerik API'si, yönetim paneli ve içerik şemaları.
- Otomatik testler.
- API örnekleri ve yapılandırma şablonları; gerçek sırlar içermesin.
- README ve adım adım kurulum/geliştirme dokümantasyonu.
- Sunucu kurulum, yedekleme, geri yükleme ve sürüm çıkarma rehberi.
- APK derleme talimatı ve mağaza dağıtımı için AAB/işaretleme yönergeleri.
- Gizlilik ve güvenlik dokümantasyonu.

## 11. Uygulama yöntemi
Önce depoyu ve mevcut dosyaları incele. Boşsa uygun proje yapısını oluştur. Ardından sırasıyla:
1. Android uygulaması ve altı temel oyun.
2. Sesler, çevrimdışı çalışma ve yerel kayıt.
3. Ebeveyn paneli ve güvenlik.
4. İçerik API'si ve yönetim paneli.
5. Güvenli sürümleme, güncelleme ve geri dönüş.
6. Testler, performans, belgeler ve derleme.

Her aşamada gerçek dosyaları oluştur ve entegre et; yalnızca yapılacaklar listesi yazıp bırakma. Derleme/test araçları veya sunucu kimlik bilgileri eksikse bunu açıkça belirt ve gerekli sonraki adımı ver. Hiçbir özelliğin, dağıtımın, sunucunun veya APK'nın hazır olduğunu doğrulamadan iddia etme.

## Nihai kabul kriterleri
Uygulama derlenebilmeli, temel oyunlar gerçekten oynanabilmeli, Türkçe sesler ve mevcut içerikler çevrimdışı çalışmalı, ebeveyn paneli kullanılabilmeli, uzaktan yönetim panelinden güvenli içerik yayımlanabilmeli, uygulama yeni uyumlu içerikleri APK güncellemesi olmadan alabilmeli ve hatalı güncellemeden dönebilmeli. Tüm kritik test sonuçlarını ve eksik kalan işleri açıkça raporla.

**Nihai hedef:** Bugün çalışan ve kurulabilen bir çocuk eğitim oyunu; ileride yeni oyunlar, hayvanlar, sesler, görseller ve görevlerin güvenli yönetim panelinden uzaktan eklenebildiği, sürdürülebilir bir ürün.
