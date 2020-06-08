package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.viewmodel.PupilDetailViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_pupildetail.*
import kotlinx.android.synthetic.main.fragment_pupillist.*
import org.koin.android.viewmodel.ext.android.viewModel

class PupilDetailFragment : Fragment() {

    private var pupilId = 0L
    private val pupilDetailViewModel: PupilDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pupildetail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        arguments?.let {
            pupilId = PupilDetailFragmentArgs.fromBundle(it).pupilId
            pupilDetailViewModel.getPupilDetail(pupilId)
        }
    }

    private fun observeViewModel() {
        pupilDetailViewModel.pupilLiveData.observe(this, Observer { pupil ->
            pupil?.let { fillPupilData(it) }
        })
    }

    private fun fillPupilData(pupil: Pupil) {
        Glide.with(pupil_image).load(pupil.image).into(pupil_image)
        pupil_id.text = pupil.pupilId.toString()
        pupil_name.text = pupil.name
        country.text = pupil.country
    }
}