package ch.heigvd.iict.daa.template.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.R

class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        val imageView = findViewById<ImageView>(R.id.full_screen_image)

        // Récupère le chemin ou l’image envoyée via l’intent
        val imagePath = intent.getStringExtra("IMAGE_PATH")

        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
            } else {
                // Gestion d'erreur si le fichier est corrompu ou introuvable
                imageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
        } else {
            // Aucun chemin fourni, affichage d'une image par défaut
            imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
}
