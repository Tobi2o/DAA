## 1. Introduction

### 1.1 Contexte général

Les codes-barres sont devenus un élément omniprésent dans de nombreux domaines, notamment le commerce, la logistique et la gestion d’inventaire. Leur adoption massive s’explique par leur simplicité d’utilisation et leur efficacité à stocker et transmettre des informations de manière rapide et fiable. On les retrouve sur les emballages de produits, les billets électroniques, les cartes de fidélité, et bien d’autres supports.

Avec l’essor des smartphones et des applications mobiles, la lecture de codes-barres est devenue une fonctionnalité essentielle pour de nombreuses applications Android. Que ce soit pour scanner un ticket de transport, vérifier un prix en magasin, ou encore gérer un stock de marchandises, la possibilité de lire des codes-barres via l’appareil photo d’un smartphone apporte une valeur ajoutée significative aux utilisateurs et aux entreprises.

Les technologies de reconnaissance de codes-barres ont également évolué au fil du temps. À l'origine, les lecteurs de codes-barres étaient des périphériques matériels dédiés, utilisés principalement dans les magasins et entrepôts. Aujourd’hui, grâce aux avancées en vision par ordinateur et aux bibliothèques logicielles spécialisées, il est possible d’intégrer facilement un scanner de codes-barres performant directement dans une application mobile.

### 1.2 Objectifs du projet

L’objectif de ce projet est de fournir une étude détaillée sur l’intégration de la lecture de codes-barres dans une application Android. Nous allons explorer les différentes technologies et bibliothèques disponibles, en mettant l’accent sur leur facilité d’utilisation, leur compatibilité et leurs performances.

Plus précisément, ce document vise à :

- Présenter les fondamentaux de la lecture de codes-barres et ses principales applications.
- Comparer les principales bibliothèques disponibles sur Android pour implémenter cette fonctionnalité.
- Proposer un guide pratique détaillé pour intégrer un scanner de codes-barres dans une application Android.
- Discuter des limitations et des alternatives à cette technologie.

### 1.3 Méthodologie

Pour mener à bien cette étude, nous suivrons une approche en plusieurs étapes :

1. **Recherche documentaire** : analyse des bibliothèques les plus utilisées pour la lecture de codes-barres sur Android, notamment ML Kit Barcode Scanning et ZXing.
2. **Comparaison des solutions** : évaluation des critères clés tels que la facilité d’intégration, les performances et la compatibilité avec différents appareils Android.
3. **Implémentation pratique** : développement d’un prototype d’application intégrant un scanner de codes-barres, avec un focus sur l’utilisation de ML Kit et ZXing.
4. **Évaluation et retour d’expérience** : tests pratiques, identification des limitations et proposition d’améliorations ou d’alternatives.

En suivant cette méthodologie, nous serons en mesure de fournir un guide structuré et accessible pour les développeurs souhaitant implémenter la lecture de codes-barres dans leurs applications Android.

## 2. Comprendre la fonctionnalité attribuée

### 2.1 Définir la lecture de codes-barres

La lecture de codes-barres est un processus permettant d'extraire et d'interpréter les informations contenues dans un code imprimé ou affiché sur un écran. Un code-barres est une représentation graphique d’une information sous forme de motifs lisibles par une machine. Ces motifs peuvent être constitués de lignes parallèles (codes-barres 1D) ou de formes géométriques (codes-barres 2D).

#### Description technique

Le principe fondamental de la lecture de codes-barres repose sur la capture d’image et le traitement numérique des motifs du code-barres. Voici les étapes clés de ce processus :

1. **Acquisition de l’image** : Un capteur optique (caméra du smartphone ou scanner dédié) capture l’image du code-barres.
2. **Prétraitement** : Correction de l’éclairage, amélioration du contraste et suppression du bruit pour améliorer la lisibilité.
3. **Détection et analyse** : Localisation du code-barres dans l’image, puis conversion des motifs en données numériques.
4. **Décodage** : Interprétation des données en fonction du type de code (EAN, QR, Code 128, etc.).

Les algorithmes de reconnaissance s’appuient sur des bibliothèques logicielles spécifiques, qui analysent les pixels pour extraire les informations codées.

#### Types de codes supportés

Les codes-barres se déclinent en plusieurs formats, chacun ayant des caractéristiques et des usages spécifiques :

**1. Codes-barres linéaires (1D) :**

- **EAN-13** : Utilisé dans le commerce pour l’identification des produits.
- **UPC (Universal Product Code)** : Principalement utilisé en Amérique du Nord.
- **Code 128** : Couramment utilisé dans la logistique pour l’étiquetage des produits et la gestion des stocks.
- **Code 39** : Utilisé dans l’industrie et le secteur de la santé.

**2. Codes-barres bidimensionnels (2D) :**

- **QR Code (Quick Response Code)** : Permet de stocker plus d’informations qu’un code 1D et peut être scanné rapidement.
- **Data Matrix** : Utilisé dans l’industrie pour le marquage de pièces de petite taille.
- **PDF417** : Employé dans les documents d’identité et les cartes d’embarquement.

Les codes 2D offrent des capacités de stockage plus importantes et peuvent contenir du texte, des URL, ou des données encodées pour des applications spécifiques (paiements, authentification, etc.).

## 2.2 Identifier les problématiques résolues

L’intégration de la lecture de codes-barres dans une application Android permet de répondre à plusieurs problématiques majeures dans différents domaines d’activité. Cette technologie améliore l’efficacité des processus en facilitant l’accès aux informations encodées, réduisant les erreurs humaines et optimisant l’expérience utilisateur.

### Automatisation des tâches

L’automatisation est l’un des principaux bénéfices de la lecture de codes-barres. Grâce à cette technologie, il est possible d’accélérer et d’optimiser plusieurs processus sans intervention humaine excessive :

- **Saisie automatique des données** : Plutôt que d’entrer manuellement un numéro de série ou un identifiant, un simple scan permet de récupérer l’information instantanément.
- **Gestion des stocks** : L’ajout et la mise à jour d’articles en entrepôt ou en magasin deviennent plus rapides et plus fiables.
- **Validation et suivi des produits** : Dans l’industrie, le suivi des pièces et des marchandises est facilité en scannant directement les codes apposés sur les objets.

### Réduction des erreurs humaines

La saisie manuelle des données est souvent source d’erreurs, qu’il s’agisse d’erreurs typographiques ou de confusion entre différents produits ou références. L’utilisation de la lecture de codes-barres élimine ce risque en permettant une lecture directe des informations encodées. Cela se traduit par :

- **Une meilleure précision dans l’enregistrement des données**.
- **Une diminution des erreurs de saisie** entraînant des pertes de temps ou des malentendus.
- **Un meilleur suivi des produits et documents**, garantissant une gestion plus fiable des processus commerciaux et industriels.

### Amélioration de l’expérience utilisateur

Les applications mobiles intégrant la lecture de codes-barres permettent aux utilisateurs de gagner en rapidité et en efficacité dans leurs interactions avec un service. Cette amélioration se manifeste de plusieurs façons :

- **Gain de temps** : Les clients peuvent scanner un produit pour obtenir des informations instantanées (prix, composition, avis, etc.).
- **Simplification des interactions** : Par exemple, dans les aéroports, la lecture des cartes d’embarquement permet d’accélérer le passage des contrôles.
- **Fiabilité accrue** : Moins d’interruptions dues à des erreurs humaines ou à une mauvaise interprétation des données.

## 2.3 Domaines d’application

La lecture de codes-barres est une technologie largement utilisée dans divers secteurs. Voici quelques domaines où son intégration apporte une réelle valeur ajoutée :

### Commerce (paiements, étiquetage)

- **Supermarchés et magasins** : Utilisation de scanners pour enregistrer les achats à la caisse.
- **E-commerce** : Suivi des colis et vérification de l’authenticité des produits.
- **Paiements mobiles** : Certains services permettent de scanner un code-barres pour régler un achat via une application.

### Santé (suivi des patients)

- **Identification des patients** : Utilisation de bracelets dotés de codes-barres pour assurer une traçabilité sans faille des soins.
- **Gestion des médicaments** : Suivi des prescriptions et administration des traitements pour éviter les erreurs.
- **Accès aux dossiers médicaux** : Un simple scan peut permettre d’accéder rapidement aux informations essentielles d’un patient.

### Logistique (gestion d’entrepôts)

- **Gestion des stocks** : Suivi des entrées et sorties de marchandises en entrepôt.
- **Suivi des expéditions** : Identification et suivi des colis à travers la chaîne d’approvisionnement.
- **Optimisation des processus industriels** : Réduction du temps d’inventaire et amélioration de l’exactitude des données de stock.

Grâce à ces différentes applications, la lecture de codes-barres devient un outil incontournable dans l’optimisation des processus et la réduction des erreurs humaines, apportant une meilleure expérience utilisateur et une gestion plus efficace des ressources.

## 3. Rechercher les outils et bibliothèques disponibles

### 3.1 Présentation des bibliothèques principales

L’intégration de la lecture de codes-barres dans une application Android repose sur l’utilisation de bibliothèques spécialisées. Deux des solutions les plus populaires sont **ML Kit Barcode Scanning** et **ZXing (Zebra Crossing)**. Ces bibliothèques offrent des fonctionnalités adaptées aux besoins des développeurs souhaitant scanner et interpréter différents types de codes-barres.

#### ML Kit Barcode Scanning

**Description :**
ML Kit Barcode Scanning est une bibliothèque développée par Google qui fait partie de Firebase Machine Learning. Elle utilise l’apprentissage automatique pour détecter et lire les codes-barres de manière efficace et précise.

**Avantages :**

- **Facilité d’intégration** : Disponible sous forme d’API simple à utiliser.
- **Compatibilité multi-formats** : Prise en charge de plusieurs types de codes-barres (EAN-13, Code 128, QR Code, etc.).
- **Traitement optimisé** : Fonctionne en temps réel avec des performances élevées.
- **Mise à jour continue** : Bénéficie du support et des améliorations constantes de Google.
- **Support natif pour Android CameraX** : Simplifie l’intégration avec la caméra du téléphone.

**Limitations :**

- **Dépendance à Google Play Services** : Nécessite un accès aux services Firebase.
- **Non disponible hors ligne sans configuration avancée**.

#### ZXing (Zebra Crossing)

**Description :**
ZXing est une bibliothèque open-source largement utilisée pour la lecture de codes-barres et QR codes. Elle fonctionne via des appels directs ou via l'utilisation d'un Intent pour scanner.

**Avantages :**

- **Open-source et gratuit** : Accessible sans frais ni restrictions.
- **Utilisation hors ligne** : Fonctionne sans dépendre de Google Play Services.
- **Polyvalence** : Peut être intégrée directement dans une application ou via une application externe (Intent).
- **Large compatibilité** : Fonctionne sur une grande variété d’appareils Android.

**Limitations :**

- **Interface utilisateur plus basique** : Nécessite parfois des ajustements pour une expérience utilisateur optimale.
- **Moins optimisé pour le traitement en temps réel** : Performances variables selon les appareils.

---

### 3.2 Comparaison des bibliothèques

| Critère                | ML Kit Barcode Scanning  | ZXing                 |
|------------------------|-------------------------|-----------------------|
| **Facilité d’intégration** | Facile via Firebase   | Nécessite une implémentation manuelle ou un Intent |
| **Performance**        | Optimisé pour le traitement en temps réel | Variable selon l’appareil |
| **Compatibilité**      | Google Play Services requis | Fonctionne sans dépendance |
| **Limitations**        | Nécessite Firebase, pas toujours offline | Interface plus basique, performances moindres |

---

### 3.3 Sélection de la solution adaptée

Le choix de la bibliothèque dépend des besoins spécifiques du projet. Voici quelques considérations clés :

- **Si l’application doit fonctionner hors ligne**, ZXing est plus adapté car il ne nécessite pas Google Play Services.
- **Si l’application nécessite une reconnaissance rapide et fluide en temps réel**, ML Kit est un meilleur choix grâce à son optimisation.
- **Si l’intégration doit être simple et rapide**, ML Kit permet une implémentation plus intuitive avec moins de configurations.
- **Si l’application est open-source et que l’on veut éviter des dépendances propriétaires**, ZXing peut être préféré.

En fonction de ces critères, le choix de la bibliothèque idéale dépend du contexte d’utilisation. Pour une application moderne nécessitant un traitement optimisé, ML Kit est recommandé, tandis que ZXing reste une alternative fiable et indépendante de Firebase.

## 4. Tutoriel d’intégration

### 4.1 Préparation du projet Android

Avant d’intégrer la lecture de codes-barres avec **ML Kit Barcode Scanning**, il est nécessaire d’effectuer plusieurs préparations dans le projet Android.

#### Ajout des dépendances nécessaires (`build.gradle`)

Dans le fichier **`build.gradle (Module: app)`**, ajoutez la dépendance suivante pour inclure ML Kit :

```gradle
dependencies {
    implementation (libs.barcode.scanning)
}
```

Assurez-vous également que Google Play Services est activé dans votre projet.

#### Permissions requises pour l’utilisation de la caméra

Ajoutez les permissions nécessaires dans le fichier **`AndroidManifest.xml`** :

```xml
<uses-permission android:name="android.permission.CAMERA" />
```

Si vous ciblez Android 10 (API 29) ou supérieur, ajoutez également cette permission dans `manifest` pour activer la caméra :

```xml
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />
```

Dans le **code Java/Kotlin**, demandez la permission de la caméra à l’exécution si nécessaire.

---

### 4.2 Implémentation de base

#### Configuration initiale de ML Kit

Créez un **BarcodeScanner** avec ML Kit :

```kotlin
val scanner = BarcodeScanning.getClient()
```

#### Mise en place du scanner de code-barres

Utilisez **CameraX** pour capturer l’image et traiter le code-barres :

```kotlin
val imageAnalyzer = ImageAnalysis.Builder()
    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
    .build()
    .also {
        it.setAnalyzer(executor, ImageAnalysis.Analyzer { image ->
            val mediaImage = image.image
            if (mediaImage != null) {
                val imageInput = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
                scanner.process(imageInput)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            Log.d("Barcode", "Data: ${barcode.rawValue}")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("Barcode", "Erreur : ${e.message}")
                    }
                    .addOnCompleteListener {
                        image.close()
                    }
            }
        })
    }
```

---

### 4.3 Affichage des résultats

Lorsque le scanner détecte un code-barres, les données peuvent être affichées dans une `TextView` ou utilisées pour déclencher une action spécifique.

Exemple :

```kotlin
barcodeScanner.process(imageInput)
    .addOnSuccessListener { barcodes ->
        for (barcode in barcodes) {
            textView.text = "Code détecté : ${barcode.rawValue}"
        }
    }
```

---

### 4.4 Gestion des erreurs

#### Scénarios d’erreurs possibles

- **Caméra indisponible** : Assurez-vous que l’appareil dispose d’une caméra fonctionnelle.
- **Échec de la reconnaissance** : Vérifiez que le code-barres est bien visible et bien éclairé.
- **Permissions manquantes** : Demandez la permission d’accès à la caméra avant d’utiliser ML Kit.

#### Solutions proposées

Ajoutez une gestion d’erreur pour avertir l’utilisateur :

```kotlin
.addOnFailureListener { e ->
    Toast.makeText(this, "Erreur de détection : ${e.message}", Toast.LENGTH_SHORT).show()
}
```

---

### 4.5 Optimisations

#### Gestion de la batterie et performances

- **Utilisez la stratégie `STRATEGY_KEEP_ONLY_LATEST`** pour éviter de surcharger le processeur.
- **Désactivez la caméra lorsque l’application est en arrière-plan** pour économiser la batterie.

#### Réduction des temps de traitement

- **Optimisez l’éclairage et la mise au point** de la caméra pour faciliter la lecture.
- **Utilisez `CameraX` avec des paramètres adaptés** pour une meilleure fluidité.

---

## 5. Limitations et alternatives

### 5.1 Limitations de la lecture de codes-barres

Bien que la lecture de codes-barres soit une technologie largement adoptée, elle présente certaines limitations qui peuvent impacter son efficacité et son adoption dans certaines applications.

#### Problèmes matériels

- **Qualité de la caméra** : Les appareils dotés de caméras de faible résolution peuvent rencontrer des difficultés à détecter et interpréter correctement les codes-barres, en particulier lorsque ceux-ci sont petits ou partiellement endommagés.
- **Faible luminosité** : Un éclairage insuffisant peut réduire la précision de la détection, nécessitant des ajustements d’exposition ou l’utilisation d’un flash pour améliorer la lisibilité.
- **Distance et mise au point** : Une caméra sans autofocus ou un code-barres scanné à une mauvaise distance peut altérer la reconnaissance des données.

#### Limites des formats pris en charge

- **Types de codes-barres** : Certains formats spécifiques ne sont pas pris en charge par ML Kit, obligeant les développeurs à recourir à d’autres bibliothèques.
- **Données encodées** : Contrairement aux QR codes, les codes-barres 1D ne permettent de stocker qu’un nombre limité de caractères, limitant leur utilisation pour des données volumineuses.
- **Problèmes de standardisation** : Certaines variantes de codes-barres propriétaires peuvent ne pas être interprétées correctement par ML Kit.

---

### 5.2 Approches alternatives

Lorsque la lecture de codes-barres présente des limitations, il est possible d’explorer des alternatives pour répondre à des besoins spécifiques.

#### Utilisation de QR codes pour des données plus complexes

- **Capacité de stockage élevée** : Contrairement aux codes-barres traditionnels, les QR codes peuvent contenir des informations plus riches comme des URL, des identifiants ou des blocs de texte structurés.
- **Résistance aux erreurs** : Grâce à la correction d’erreur intégrée, un QR code peut être partiellement endommagé et rester lisible.
- **Usage étendu** : Les QR codes sont utilisés dans divers domaines, notamment pour l’authentification, le paiement mobile et le marketing interactif.

#### OCR (Reconnaissance Optique de Caractères) comme complément

- **Lecture d’informations textuelles** : Lorsque les codes-barres ne sont pas lisibles ou absents, l’OCR permet d’extraire les données imprimées directement sur les documents.
- **Compatibilité universelle** : Fonctionne avec tous les types de documents sans nécessiter de formats standardisés.
- **Applications étendues** : Utile pour la numérisation de factures, tickets de caisse ou documents d’identité.

L’intégration de ces alternatives en complément de la lecture de codes-barres peut améliorer la robustesse et la fiabilité des applications, offrant une expérience utilisateur plus fluide et flexible.

## **6. Cas pratiques et exemples**

### **6.1 Exemples d’implémentation complète**

#### **Exemple de lecture et affichage d’un code-barres**

Nous avons développé une application Android intégrant **ML Kit Barcode Scanning** pour détecter et afficher le contenu d'un code-barres. L'application utilise **CameraX** pour capturer l’image et analyse le code-barres uniquement lorsqu’un bouton "Scanner" est pressé. Si aucun code n'est détecté, un message d'erreur s'affiche.

---

### **Code final de `MainActivity.kt`**

Ce fichier **MainActivity.kt** contient l'implémentation complète du scanner, avec :
**Gestion des permissions**  
**Bouton pour déclencher le scan**  
**Affichage du résultat ou d’un message d’erreur en cas d’échec**  

```kotlin
package ch.heigvd.iict.daa.template

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var textView: TextView
    private lateinit var scanButton: Button
    private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()
    private var isScanning = false // État du scan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        scanButton = findViewById(R.id.scanButton)
        cameraExecutor = Executors.newSingleThreadExecutor()

        checkCameraPermission()

        scanButton.setOnClickListener {
            if (!isScanning) {
                isScanning = true
                textView.text = "Scanning en cours..."
            }
        }
    }

    private val CAMERA_PERMISSION_CODE = 100

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Log.e("Permission", "Permission caméra refusée")
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(findViewById<PreviewView>(R.id.viewFinder).surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { image ->
                        if (isScanning) { // Scan seulement si on a cliqué
                            processImage(image)
                        } else {
                            image.close()
                        }
                    }
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
            } catch (exc: Exception) {
                Log.e("Camera", "Erreur de démarrage de la caméra", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    @SuppressLint("SetTextI18n")
    private fun processImage(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        barcodeScanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    textView.text = "Code détecté: ${barcodes.first().rawValue}"
                } else {
                    textView.text = "Aucun code détecté, réessayez."
                }
                isScanning = false
            }
            .addOnFailureListener {
                textView.text = "Erreur de scan, réessayez."
                Log.e("Barcode", "Erreur lors de la lecture du code-barres", it)
                isScanning = false
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
```

---

### **Code final `activity_main.xml`**

Ce fichier **`activity_main.xml`** gère l'interface utilisateur, en structurant bien les éléments :

**Un `PreviewView` pour afficher la caméra**  
**Un bouton `scanButton` pour déclencher le scan**  
**Un `TextView` pour afficher le résultat**  

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <!-- PreviewView pour afficher la caméra -->
    <androidx.camera.view.PreviewView
        android:id="@+id/viewFinder"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@+id/scanButton"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <!-- Bouton Scanner -->
    <Button
        android:id="@+id/scanButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Scanner"
        android:padding="16dp"
        app:layout_constraintTop_toBottomOf="@id/viewFinder"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <!-- TextView pour afficher les résultats du scan -->
    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Scannez un code-barres..."
        android:textSize="18sp"
        android:textStyle="bold"
        android:padding="16dp"
        app:layout_constraintTop_toBottomOf="@+id/scanButton"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## **6.2 Visualisation**

### **Tests réalisés et résultats obtenus**

**Test de lecture de code-barres :**  
→ Un scan réussi affiche immédiatement le contenu du code-barres dans le `TextView`.

**Test de gestion des erreurs :**  
→ Si aucun code n'est détecté, un message `"Aucun code détecté, réessayez."` s'affiche.  

**Test de gestion de la caméra :**  
→ La caméra s’ouvre correctement et se réactive après fermeture et réouverture de l’application.  
→ Aucun crash n’a été observé lors de l’utilisation prolongée.

---

## 7. Conclusion

### 7.1 Synthèse

Dans ce rapport, nous avons exploré l’intégration de la lecture de codes-barres dans une application Android en utilisant **ML Kit Barcode Scanning**. Nous avons commencé par comprendre l’importance et les problématiques résolues par cette technologie avant de passer à la mise en œuvre pratique.

Les points abordés incluent :

- **Présentation et fonctionnement des codes-barres** : Importance, formats pris en charge et problématiques résolues.
- **Choix des bibliothèques** : Analyse de ML Kit comme solution optimale pour la reconnaissance en temps réel.
- **Tutoriel d’intégration** : Configuration du projet, gestion des permissions et implémentation du scanner avec **CameraX**.
- **Exemples pratiques** : Lecture d’un code-barres, gestion des erreurs et affichage des résultats.
- **Visualisation et tests** : Vérification des performances sur un appareil ou un émulateur.

Nous avons ainsi démontré que l’intégration de la lecture de codes-barres via ML Kit est accessible et permet de répondre efficacement à divers besoins d’automatisation et de gestion de données.

---

### 7.2 Recommandations

Pour améliorer et étendre cette implémentation, plusieurs pistes d’amélioration sont envisageables :

#### **Ajout de nouvelles fonctionnalités**

- **Support d’autres formats** : Actuellement, nous avons utilisé les codes-barres classiques et QR codes. Il serait intéressant d’explorer d’autres formats comme **DataMatrix ou PDF417**.
- **Enregistrement des scans** : Stocker l’historique des scans dans une base de données locale ou sur le cloud.
- **Intégration avec d’autres services** : Par exemple, connecter les résultats à une API externe pour valider des produits ou obtenir des informations supplémentaires en ligne.

#### **Optimisation des performances**

- **Améliorer la gestion des erreurs** : Ajouter un retour utilisateur plus interactif, comme des animations ou des alertes contextuelles.
- **Optimisation de la consommation énergétique** : Éviter l’utilisation prolongée de la caméra en ajoutant une fonction de mise en veille après une inactivité prolongée.
- **Meilleure compatibilité** : Tester l’application sur une plus grande variété d’appareils pour s’assurer de sa robustesse.

L’ajout de ces améliorations permettrait de renforcer l’expérience utilisateur et d’optimiser l’efficacité du scanner, rendant l’application plus robuste et polyvalente.

## 8. Bibliographie et références

### **Documentation officielle**

- [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning) - Documentation officielle de Google.
- [Android CameraX](https://developer.android.com/training/camerax) - Documentation sur CameraX pour l’intégration de la caméra.

### **Articles et ressources en ligne**

- [Barcode Scanning with ML Kit - Medium](https://medium.com/androiddevelopers/ml-kit-barcode-scanning-android-tutorial-5c8ad3e109de) - Tutoriel détaillé sur l’intégration de ML Kit pour la lecture de codes-barres.
- [Introduction to CameraX](https://proandroiddev.com/camerax-introduction-28959131a1dc) - Article expliquant les bases de CameraX.

### **Utilisation d’outils d’assistance**

- ChatGPT a été utilisé pour structurer et relire le rapport, ainsi que nous fournir des explications détaillées sur des choses que l'on ne comprennait pas.
