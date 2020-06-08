package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.db.Pupil
import com.bridge.androidtechnicaltest.viewmodel.AddPupilViewModel
import com.bridge.androidtechnicaltest.vo.Status
import com.bridge.androidtechnicaltest.vo.Status.ERROR
import com.bridge.androidtechnicaltest.vo.Status.SUCCESS
import kotlinx.android.synthetic.main.fragment_addpupil.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddPupilFragment : Fragment() {
    companion object {
        private val IMAGE_URL = "http://lorempixel.com/640/480/business?name=Abby Becker";
    }

    private val addPupilViewModel: AddPupilViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_addpupil, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        submit.setOnClickListener {
            val pupil = Pupil(0L, name.text.toString(), country.text.toString(),IMAGE_URL,0.0,0.0 )
            addPupilViewModel.createPupil(pupil)
            name.setText("")
            country.setText("")
        }
    }

    private fun observeViewModel() {
        addPupilViewModel.uploadStatusLiveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                SUCCESS -> Toast.makeText(context, "Upload Successful", Toast.LENGTH_LONG).show()
                ERROR -> Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}