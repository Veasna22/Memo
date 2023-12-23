package kh.edu.rupp.ite.memo.ui.note

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kh.edu.rupp.ite.memo.R
import kh.edu.rupp.ite.memo.databinding.FragmentNoteBinding
import kh.edu.rupp.ite.memo.models.NoteRequest
import kh.edu.rupp.ite.memo.models.NoteResponse
import kh.edu.rupp.ite.memo.utils.NetworkResponse
import kh.edu.rupp.ite.memo.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

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
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)

                val markdown = it.description

                val markwon = Markwon.create(requireContext())
                val markdownSpanned = markwon.toMarkdown(markdown ?: "")

                binding.txtDescription.text = markdownSpanned as Editable?

                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()) // Modify this based on your timestamp format
                val outputFormat = SimpleDateFormat("yyyy / MM / dd", Locale.getDefault())
                try {
                    val parsedDate = inputFormat.parse(it.createdAt)
                    val parsedDateUpdate = inputFormat.parse(it.updatedAt)
                    val formattedDate = parsedDate?.let { it1 -> outputFormat.format(it1) }
                    val formattedUpdateDate = parsedDateUpdate?.let { it1 -> outputFormat.format(it1) }
                    binding.statustext.text = formattedDate
                    binding.addEditText.text = formattedUpdateDate
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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