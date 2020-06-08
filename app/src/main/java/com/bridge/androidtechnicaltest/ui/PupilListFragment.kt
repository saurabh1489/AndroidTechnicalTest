package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.ui.adaptor.PupilAdapter
import com.bridge.androidtechnicaltest.viewmodel.PupilListViewModel
import kotlinx.android.synthetic.main.fragment_pupillist.*
import org.koin.android.viewmodel.ext.android.viewModel

class PupilListFragment : Fragment() {
    private val pupilListViewModel: PupilListViewModel by viewModel()
    private val adapter = PupilAdapter {
        navigateToPupilDetailScreen(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pupillist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        add_pupil.setOnClickListener {
            findNavController().navigate(PupilListFragmentDirections.actionGoToAddPupilScreen())
        }

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        pupil_list.addItemDecoration(decoration)

        initAdapter()
        pupilListViewModel.fetchPupil()
    }

    private fun initAdapter() {
        pupil_list.adapter = adapter
        pupilListViewModel.pupilList.observe(this, Observer {
            Log.d("Activity", "list: ${it?.size}")
            adapter.submitList(it)
        })
    }

    private fun navigateToPupilDetailScreen(pupilId: Long) {
        val action = PupilListFragmentDirections.actionGoToDetailScreen(pupilId)
        findNavController().navigate(action)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_reset) {
            pupilListViewModel.fetchPupil()
        }
        return super.onOptionsItemSelected(item)
    }
}