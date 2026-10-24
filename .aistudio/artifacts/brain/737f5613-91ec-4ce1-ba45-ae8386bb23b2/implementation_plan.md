# Eku Phone Tycoon - Akıllı Telefon Şirketi & Tasarım Simülatörü

Kendi akıllı telefon şirketinizi kurup yöneteceğiniz, MediaTek'ten Snapdragon'a işlemciler, AMOLED ekranlar, RAM ve depolama seçenekleri, kamera ve batarya konfigürasyonlarıyla telefon tasarlayıp pazara sunduğunuz derin bir mobil tycoon simülasyonu.

## Kullanıcı Kararları & Onaylanan Tercihler

> [!IMPORTANT]
> Kullanıcı ile yapılan netleştirme görüşmesi sonucunda aşağıdaki tercihler onaylanmıştır:

- **Simülasyon Derinliği**: Kapsamlı Tycoon mekaniği (AR-GE teknoloji ağacı, pazar rekabeti, dinamik eleştirmen incelemeleri, haftalık satış simülasyonu ve finansal raporlar).
- **Telefon Özelleştirme Kapsamı**: Uçtan uca tüm bileşenler: İşlemci (Snapdragon, MediaTek Dimensity, Exynos, Tensor, Bionic), RAM & Depolama, Ekran (OLED/AMOLED/LCD, yenileme hızı, parlaklık), Kamera mimarisi, Batarya ve Hızlı şarj, Kasa malzemeleri (Titanyum, Cam, Seramik, Deri), İşletim Sistemi ve Yapay Zeka özellikleri, Satış fiyatlandırması.
- **Görsel Tasarım & Tema**: Modern teknoloji odaklı canlı karanlık tema (Neon camgöbeği, elektrik mavisi ve altın sarısı vurgular, gerçek zamanlı telefon kasa önizlemesi ve akıcı kart animasyonları).
- **Kalıcılık & Altyapı**: Cihaz üzerinde hızlı ve kesintisiz çalışan, oyun durumunu (şirket parası, itibar, üretilen modeller, AR-GE seviyeleri) güvenle saklayan yerel durum yönetimi.

---

## 1. Genel Bakış ve Temel Konsept

**Eku Phone Tycoon**, oyuncunun küçük bir teknoloji garajından başlayarak dünyanın en büyük akıllı telefon devine dönüştüğü bir simülasyon oyunudur.

### Temel Özellikler
1. **İnteraktif Telefon Tasarım Atölyesi (Studio)**:
   - **İşlemci (SoC)**: MediaTek Dimensity 9400, Dimensity 8300, Helio G99; Qualcomm Snapdragon 8 Elite, Snapdragon 8 Gen 3, 7+ Gen 3, 6 Gen 1; Apple A18 Pro; Google Tensor G4; Samsung Exynos 2400. Her işlemcinin maliyeti, CPU puanı, GPU puanı ve güç verimliliği bulunur.
   - **RAM**: 4GB, 6GB, 8GB, 12GB, 16GB, 24GB LPDDR5X (Çoklu görev & hız skoru).
   - **Depolama**: 64GB, 128GB, 256GB, 512GB, 1TB UFS 4.0.
   - **Ekran**: 6.1" - 6.8", IPS LCD 90Hz, AMOLED 120Hz, LTPO OLED 144Hz 4500 nits, Kavisli/Düz panel seçenekleri.
   - **Kamera**: Tekli 50MP, Çift Geniş+Ultra Geniş, Üçlü Periskop 5x Zoom, 200MP Gece Sensörü, OIS ve Video motoru.
   - **Batarya & Şarj**: 4500mAh - 6500mAh Si/C batarya, 33W - 120W süper hızlı şarj, kablosuz şarj.
   - **Kasa & Tasarım**: Plastik, Alüminyum, Cam, Titanyum, Vegan Deri; Çerçeve renkleri (Uzay Grisi, Titanyum Mavisi, Zümrüt Yeşili, Mat Siyah, İnci Beyazı).
   - **Yazılım & Yapay Zeka**: EkuOS optimizasyonu, Yapay Zeka Asistanı, 5 Yıl Güncelleme Desteği.
2. **Fiyatlandırma & Lansman**:
   - Üretim birim maliyeti otomatik hesaplanır.
   - Oyuncu önerilen fiyat veya kendi belirlediği satış fiyatı ile kar marjını ayarlar.
   - Pazarlama kampanyası seçimi (Sosyal Medya Fısıltısı, Teknoloji YouTuberları, Global Lansman Konferansı).
3. **Teknoloji Medyası & Eleştirmen Puanları**:
   - Lansmandan sonra popüler teknoloji sitelerinden (örn. *TechRadar*, *TheVerge*, *GsmEku*, *DonanımDiyarı*) gerçekçi inceleme başlıkları ve 1-10 arası puanlar.
   - "Fiyat-performans canavarı", "Ekranı büyüleyici ama bataryası günü zor çıkarıyor", "Snapdragon 8 Elite gücüyle oyunlarda rakipsiz" gibi dinamik yorumlar.
4. **Pazar Payı ve Satış Simülasyonu**:
   - Telefon pazara çıktıktan sonra haftalık satış döngüsü başlar.
   - Rakip markalar (Pear/Apple, Samstar/Samsung, MiGlobal/Xiaomi) ile pazar payı savaşı.
   - En çok satan telefonlar liderlik tablosu.
5. **AR-GE & Şirket Geliştirme (R&D Tree)**:
   - Kazanılan AR-GE puanları ile kilitli teknolojileri açma: Katlanabilir Ekranlar, Ekran Altı Kamera, 200W Şarj, Özel NPU Çipi.
   - Ofis Seviyesi: Garajdan Teknoloji Kampüsüne yükseltme.

---

## 2. Kullanıcı Deneyimi & Arayüz Tasarımı

### Sayfalar ve Gezinme (Navigation)
- **Alt Gezinme Çubuğu (Bottom Navigation)**:
  - 📱 **Ofis (Dashboard)**: Şirket sermayesi, mevcut seviye, aktif satıştaki modellerin günlük/haftalık geliri, son haberler ve hızlı aksiyonlar.
  - 🛠️ **Tasarla (Studio)**: Telefon bileşen seçimi (İşlemci, RAM, Depolama, Ekran, Kamera, Batarya, Malzeme), canlı telefon mock-up önizlemesi ve maliyet paneli.
  - 🔬 **AR-GE (Laboratuvar)**: Teknoloji ağacı, kilitli bileşenleri araştırma ve açma.
  - 📊 **Pazar & Rakipler**: Pazar analizi, rakip markaların çıkardığı telefonlar, liderlik tablosu ve pazar payı grafikleri.
  - 🏆 **Koleksiyon & Geçmiş**: Bugüne kadar üretilen tüm modeller, aldıkları ödüller, toplam satış adetleri ve inceleme arşivleri.

### Görsel Kimlik ve Atmosfer
- **Arka Plan**: Çok koyu uzay mavisi/antrasit (`#0A0E17`) ve yarı saydam cam paneller (`#141C2E`).
- **Vurgu Renkleri**:
  - Neon Turkuaz / Cyan (`#00F5D4`): Teknoloji, ekran ve yenilik vurgusu.
  - Elektrik Mavisi (`#3A86FF`): İşlemci, RAM ve performans bileşenleri.
  - Kehribar Sarısı (`#FFB703`): Para, kar ve eleştirmen yıldızları.
  - Ateş Mercanı (`#FB5607`): Üretim maliyeti ve rekabet indikatörü.
- **İnteraktif Telefon Önizlemesi**: Tasarım sayfasında ekran oranı, seçilen kasa rengi, kamera lens adedi (tekli/çift/üçlü/dörtlü kamera adası) ve malzeme dokusu görsel olarak anlık güncellenir.

---

## 3. Temel Ürün Kararları & Tercihler

1. **İşlemci ve Donanım Çeşitliliği**:
   - Kullanıcının talebine uygun olarak hem **MediaTek** (Dimensity & Helio) hem **Qualcomm Snapdragon** (8, 7 ve 6 serileri), hem de diğer popüler mimariler zengin parametrelerle (CPU, GPU, Enerji Verimliliği, Maliyet) yer alır.
2. **Dinamik İnceleme ve Puanlama Motoru**:
   - Puanlama sadece rastgele değildir; işlemci-RAM dengesi, batarya kapasitesi ile ekran yenileme hızı arasındaki uyum ve fiyat/performans oranı hesaplanarak gerçekçi inceleme metinleri türetilir.
3. **Akıcı Oyun Döngüsü (Gameplay Loop)**:
   - Tasarla ➔ Fiyatlandır & Pazarlama Seç ➔ Lansman Yap ➔ İncelemeleri Oku ➔ Satışları Topla ➔ Kazançla AR-GE Yap ➔ Yeni Nesil Telefon Geliştir.

---

## 4. Teknik Mimari & Veri Stratejisi

### Sistem Mimarisi Şeması

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Eku Phone Tycoon Architecture                   │
└────────────────────────────────────────────────────────────────────────┘
                                    │
           ┌────────────────────────┴────────────────────────┐
           ▼                                                 ▼
┌───────────────────────┐                         ┌──────────────────────┐
│     UI Layer (M3)     │                         │   State & ViewModel  │
│  - MainNavigation     │ ◄───── StateFlow ────── │  - TycoonViewModel   │
│  - DashboardScreen    │                         │    * CompanyState    │
│  - PhoneStudioScreen  │ ───── User Actions ───► │    * ActivePhones    │
│  - LabResearchScreen  │                         │    * MarketState     │
│  - MarketScreen       │                         │    * DesignDraft     │
│  - HistoryScreen      │                         └──────────┬───────────┘
│  - LaunchModal/Dialog │                                    │
└───────────────────────┘                                    │
                                                             ▼
                                                  ┌──────────────────────┐
                                                  │   Simulation Engine  │
                                                  │  - HardwareCatalog   │
                                                  │  - ScoringEngine     │
                                                  │  - MarketSimulator   │
                                                  │  - ReviewGenerator   │
                                                  │  - PersistenceRepo   │
                                                  └──────────────────────┘
```

### Veri Modelleri
- `Company`: Şirket adı, bütçe (örn. 500,000 ₺ başlangıç), toplam itibar (fans/hype), şirket seviyesi, ofis adı.
- `PhoneModel`: Model adı, işlemci, RAM, depolama, ekran, kamera, batarya, malzeme, renk, birim maliyet, satış fiyatı, eleştirmen puanı, toplam satılan adet, toplam ciro.
- `ComponentOption`: Tip (CPU, RAM, Display vb.), marka, model adı, özellikler (CPU gücü, enerji, maliyet, gereken teknoloji seviyesi).
- `ResearchTech`: Teknoloji adı, açıklama, maliyet, kilit açtığı bileşenler, tamamlanma durumu.
- `Competitor`: Rakip şirketler, amiral gemisi modelleri ve pazar payları.

---

## 5. Uygulama Adımları (Execution Roadmap)

1. **Varlıklar ve Yapılandırma**:
   - `metadata.json` ve `strings.xml` dosyalarını senkronize ederek oyun adını "Eku Phone Tycoon" olarak belirleme.
2. **Veri ve Simülasyon Motoru (Domain / Model Layer)**:
   - Donanım kataloğu (MediaTek, Snapdragon, RAM, ROM, Ekran, vb.).
   - Maliyet, skor, eleştirmen incelemeleri ve pazar payı hesaplama algoritmaları.
3. **Arayüz Teması ve Bileşenleri (UI Layer)**:
   - Koyu neon teknoloji renk paleti, özel göstergeler, canlı telefon çizim önizleyicisi (Phone Preview Canvas / Composable).
4. **Ekranların İnşası**:
   - Dashboard (Şirket istatistikleri ve satışlar).
   - Studio (Bileşen seçici sekmeleri, işlemci karşılaştırma, canlı telefon modeli).
   - Lansman ve Eleştirmen Puanı modalı / kutlaması.
   - AR-GE Laboratuvarı ve Teknoloji Ağacı.
   - Pazar ve Rekabet ekranı.
5. **Derleme ve Doğrulama**:
   - `compile_applet` ile hatasız derlendiğinin doğrulanması.
