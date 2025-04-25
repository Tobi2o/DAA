package ch.heigvd.iict.daa.template.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.models.State
import ch.heigvd.iict.daa.template.models.Type

/**
 * Utilitaire pour la gestion des aspects visuels des notes.
 */
object NoteUtils {

    fun getIconResource(type: Type): Int {
        return when (type) {
            Type.TODO -> R.drawable.todo
            Type.SHOPPING -> R.drawable.shopping
            Type.WORK -> R.drawable.work
            Type.FAMILY -> R.drawable.family
            Type.NONE -> R.drawable.note
        }
    }

    fun getStateTint(context: Context, state: State): ColorStateList? {
        return when (state) {
            State.IN_PROGRESS -> ContextCompat.getColorStateList(context, R.color.grey)
            State.DONE -> ContextCompat.getColorStateList(context, R.color.green)
        }
    }

    fun getScheduleTint(context: Context, isLate: Boolean): ColorStateList? {
        return when (isLate) {
            true -> ContextCompat.getColorStateList(context, R.color.red)
            false -> ContextCompat.getColorStateList(context, R.color.grey)
        }
    }
}
