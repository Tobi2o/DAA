package ch.heigvd.iict.daa.template.listener

import ch.heigvd.iict.daa.template.models.NoteAndSchedule

interface OnClickListener {
    fun onNoteClicked(noteAndSchedule: NoteAndSchedule)
}