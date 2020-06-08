package com.bridge.androidtechnicaltest.ui.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.db.Pupil

class PupilAdapter(val clickAction: (Long) -> Unit) : PagedListAdapter<Pupil, RecyclerView.ViewHolder>(PUPIL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pupil_list_item, parent, false)
        return PupilViewHolder(view, clickAction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { pupil ->
            (holder as PupilViewHolder).bind(pupil)
        }
    }

    companion object {
        private val PUPIL_COMPARATOR = object : DiffUtil.ItemCallback<Pupil>() {
            override fun areItemsTheSame(oldItem: Pupil, newItem: Pupil): Boolean =
                    oldItem.pupilId == newItem.pupilId

            override fun areContentsTheSame(oldItem: Pupil, newItem: Pupil): Boolean =
                    oldItem == newItem
        }
    }

}