package ch.heigvd.iict.daa.template.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ch.heigvd.iict.daa.template.databinding.NoteItemListViewBinding as NoteViewBinding
import ch.heigvd.iict.daa.template.databinding.NoteScheduleItemListViewBinding as ScheduleViewBinding
import ch.heigvd.iict.daa.template.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.listener.OnClickListener
import ch.heigvd.iict.daa.template.utils.DateUtils
import ch.heigvd.iict.daa.template.utils.NoteUtils

/**
 * Adaptateur pour la RecyclerView permettant d'afficher des notes et leurs éventuels horaires.
 */
class NotesAdapter(
    private val clickListener: OnClickListener,
    initialItems: List<NoteAndSchedule> = emptyList()
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    // DiffUtil pour gérer efficacement les mises à jour de la RecyclerView.
    private val differ = AsyncListDiffer(this, NotesDiffCallback())

    // Propriété pour définir les éléments de la liste.
    var items: List<NoteAndSchedule>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    init {
        items = initialItems
    }

    companion object {
        private const val TYPE_NOTE = 0
        private const val TYPE_NOTE_SCHEDULE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_NOTE) {
            ViewHolder(NoteViewBinding.inflate(inflater, parent, false))
        } else {
            ViewHolder(ScheduleViewBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].schedule != null) TYPE_NOTE_SCHEDULE else TYPE_NOTE
    }

    override fun getItemCount(): Int = items.size

    /**
     * ViewHolder pour gérer chaque élément de la RecyclerView.
     */
    inner class ViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onNoteClicked(items[position])
                }
            }
        }

        fun bind(noteAndSchedule: NoteAndSchedule) {
            when (binding) {
                is NoteViewBinding -> bindNote(binding, noteAndSchedule)
                is ScheduleViewBinding -> bindNoteWithSchedule(binding, noteAndSchedule)
            }
        }

        private fun bindNote(binding: NoteViewBinding, noteAndSchedule: NoteAndSchedule) {
            binding.apply {
                textViewTitle.text = noteAndSchedule.note.title
                textViewText.text = noteAndSchedule.note.text
                imageViewIcon.setImageResource(NoteUtils.getIconResource(noteAndSchedule.note.type))
                imageViewIcon.imageTintList =
                    NoteUtils.getStateTint(itemView.context, noteAndSchedule.note.state)
            }
        }

        private fun bindNoteWithSchedule(
            binding: ScheduleViewBinding,
            noteAndSchedule: NoteAndSchedule
        ) {
            binding.apply {
                val (dateText, isLate) =
                    DateUtils.getDateDiff(itemView.context, noteAndSchedule.schedule!!.date)
                textViewTitle.text = noteAndSchedule.note.title
                textViewText.text = noteAndSchedule.note.text
                imageViewIcon.setImageResource(NoteUtils.getIconResource(noteAndSchedule.note.type))
                imageViewIcon.imageTintList =
                    NoteUtils.getStateTint(itemView.context, noteAndSchedule.note.state)
                textViewSchedule.text = dateText
                imageViewClock.imageTintList =
                    NoteUtils.getScheduleTint(itemView.context, isLate)
            }
        }
    }

    /**
     * DiffUtil pour optimiser les mises à jour des éléments.
     */
    private class NotesDiffCallback : DiffUtil.ItemCallback<NoteAndSchedule>() {
        override fun areItemsTheSame(
            oldItem: NoteAndSchedule,
            newItem: NoteAndSchedule
        ): Boolean = oldItem.note.noteId == newItem.note.noteId

        override fun areContentsTheSame(
            oldItem: NoteAndSchedule,
            newItem: NoteAndSchedule
        ): Boolean = oldItem == newItem
    }
}
