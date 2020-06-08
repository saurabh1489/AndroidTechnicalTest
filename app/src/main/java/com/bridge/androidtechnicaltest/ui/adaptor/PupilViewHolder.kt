package com.bridge.androidtechnicaltest.ui.adaptor

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.db.Pupil
import kotlinx.android.synthetic.main.pupil_list_item.view.*

class PupilViewHolder(val view: View, val clickAction: (Long) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(pupil: Pupil) {
        view.name.text = pupil.name
        view.country.text = pupil.country
        view.setOnClickListener {
            clickAction.invoke(pupil.pupilId)
        }
    }

}