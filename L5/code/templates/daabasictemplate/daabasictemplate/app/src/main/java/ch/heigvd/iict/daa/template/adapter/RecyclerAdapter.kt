package ch.heigvd.iict.daa.template.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.activities.FullScreenActivity
import ch.heigvd.iict.daa.template.utils.Cache
import ch.heigvd.iict.daa.template.utils.ImageDownloader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class RecyclerAdapter(private val imageUrls: List<URL>) :
    RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val progressBar: ProgressBar = view.findViewById(R.id.progressbar_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = imageUrls[position]
        val fileName = url.path.substring(url.path.lastIndexOf('/') + 1)

        holder.progressBar.visibility = View.VISIBLE
        holder.imageView.visibility = View.INVISIBLE

        // Vérifie d'abord le cache
        CoroutineScope(Dispatchers.Main).launch {
            val cachedImage = withContext(Dispatchers.IO) {
                Cache.getImage(fileName)
            }

            if (cachedImage != null) {
                holder.imageView.setImageBitmap(cachedImage)
                holder.progressBar.visibility = View.GONE
                holder.imageView.visibility = View.VISIBLE
            } else {
                val imageBytes = withContext(Dispatchers.IO) {
                    ImageDownloader.downloadImageData(url)
                }

                val bitmap: Bitmap? = if (imageBytes != null) {
                    withContext(Dispatchers.Default) {
                        ImageDownloader.decodeImage(imageBytes)
                    }
                } else null

                if (bitmap != null) {
                    holder.imageView.setImageBitmap(bitmap)
                    holder.progressBar.visibility = View.GONE
                    holder.imageView.visibility = View.VISIBLE

                    withContext(Dispatchers.IO) {
                        Cache.saveImage(fileName, bitmap)
                    }
                } else {
                    holder.imageView.setImageResource(R.drawable.ic_launcher_foreground)
                    holder.progressBar.visibility = View.GONE
                    holder.imageView.visibility = View.VISIBLE
                }
            }

            // Ajout d'un clic avec animation
            holder.imageView.setOnClickListener {
                it.animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(100)
                    .withEndAction {
                        it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()

                        // Ouvrir l'image en plein écran
                        val context = holder.itemView.context
                        val intent = Intent(context, FullScreenActivity::class.java)

                        val cachedFile = File(Cache.getCacheDir(), fileName)
                        if (cachedFile.exists()) {
                            intent.putExtra("IMAGE_PATH", cachedFile.absolutePath)
                        }

                        context.startActivity(intent)
                    }
                    .start()
            }
        }
    }

    override fun getItemCount(): Int = imageUrls.size
}
