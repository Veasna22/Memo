package kh.edu.rupp.ite.memo.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kh.edu.rupp.ite.memo.R
import kh.edu.rupp.ite.memo.databinding.FragmentNoteBinding
import kh.edu.rupp.ite.memo.models.NoteRequest
import kh.edu.rupp.ite.memo.models.NoteResponse
import kh.edu.rupp.ite.memo.utils.NetworkResponse
import kh.edu.rupp.ite.memo.viewmodel.NoteViewModel

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()
    private var note: NoteResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
        val items = listOf("Not Started", "In Progress", "Done")
        val autocomplete: AutoCompleteTextView = binding.dropdownMenu
        val adapter =ArrayAdapter(requireContext(), R.layout.list_item, items)
        autocomplete.setAdapter(adapter)
        autocomplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(requireContext(), "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResponse.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResponse.Error -> {

                }
                is NetworkResponse.Loading -> {

                }
            }
        })
    }

    private fun bindHandlers() {
        binding.more.setOnClickListener {
            showPopupMenu(it)
        }
        binding.apply {
            btnSubmit.setOnClickListener {
                val title = txtTitle.text.toString()
                val description = txtDescription.text.toString()
                val noteRequest = NoteRequest(title, description)
                if (note == null) {
                    noteViewModel.createNote(noteRequest)
                } else {
                    noteViewModel.updateNote(note!!._id, noteRequest)
                }
            }
        }
        binding.backButtom.setOnClickListener {
            it.findNavController().navigate(R.id.action_noteFragment_to_homeFragment)
        }
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null) {
            note = Gson().fromJson<NoteResponse>(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        }
    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.delete_note_menu) // Inflate the menu resource
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_note -> {
                    // Perform delete operation here
                    note?.let { noteViewModel.deleteNote(it._id) }
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}