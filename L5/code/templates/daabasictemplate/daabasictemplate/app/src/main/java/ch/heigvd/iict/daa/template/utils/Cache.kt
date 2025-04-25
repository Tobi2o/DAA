package ch.heigvd.iict.daa.template.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

object Cache {

    private lateinit var cacheDir: File
    private const val EXPIRATION_TIME = 5 * 60 * 1000L // 5 minutes

    /**
     * Initialise le répertoire utilisé pour le cache.
     * @param directory Le dossier où les images seront stockées.
     */
    fun initialize(directory: File) {
        cacheDir = directory
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    /**
     * Récupère une image du cache si elle existe et n'est pas expirée.
     * @param name Nom du fichier à chercher dans le cache.
     * @return Bitmap de l'image ou null si elle n'existe pas ou est expirée.
     */
    fun getImage(name: String): Bitmap? {
        val file = File(cacheDir, name)
        if (file.exists() && !isExpired(file)) {
            return BitmapFactory.decodeFile(file.absolutePath)
        }
        return null
    }

    /**
     * Enregistre une image dans le cache.
     * @param name Nom du fichier à enregistrer.
     * @param bitmap Image à sauvegarder.
     */
    fun saveImage(name: String, bitmap: Bitmap) {
        val file = File(cacheDir, name)
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
        }
    }

    /**
     * Supprime toutes les images du cache.
     */
    fun clear() {
        cacheDir.deleteRecursively()
        cacheDir.mkdirs()
    }

    /**
     * Vérifie si un fichier est expiré.
     * @param file Le fichier à vérifier.
     * @return True si le fichier est expiré, false sinon.
     */
    private fun isExpired(file: File): Boolean {
        return System.currentTimeMillis() - file.lastModified() > EXPIRATION_TIME
    }

    fun getCacheDir(): File {
        return cacheDir
    }

}
