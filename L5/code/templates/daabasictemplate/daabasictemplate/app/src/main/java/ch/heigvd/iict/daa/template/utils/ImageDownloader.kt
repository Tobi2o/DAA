package ch.heigvd.iict.daa.template.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

/**
 * Utilitaire pour télécharger et décoder des images.
 */
object ImageDownloader {

    /**
     * Télécharge une image depuis une URL et retourne les données brutes sous forme de ByteArray.
     * @param url L'URL de l'image à télécharger.
     * @return ByteArray des données téléchargées ou null si une erreur se produit.
     */
    suspend fun downloadImageData(url: URL): ByteArray? = withContext(Dispatchers.IO) {
        try {
            Log.d("ImageDownloader", "Tentative de téléchargement depuis URL : $url")
            url.readBytes()
        } catch (e: Exception) {
            Log.e("ImageDownloader", "Erreur lors du téléchargement : ${e.message}")
            e.printStackTrace()
            null // Retourne null si le téléchargement échoue.
        }
    }

    /**
     * Décode un ByteArray en une image Bitmap.
     * @param imageData Données brutes de l'image.
     * @return Bitmap décodé ou null en cas d'échec.
     */
    suspend fun decodeImage(imageData: ByteArray): Bitmap? = withContext(Dispatchers.Default) {
        try {
            BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        } catch (e: Exception) {
            Log.e("ImageDownloader", "Erreur lors du décodage : ${e.message}")
            e.printStackTrace()
            null
        }
    }
}
