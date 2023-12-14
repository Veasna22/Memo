package kh.edu.rupp.ite.memo.ui.home

import NoteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kh.edu.rupp.ite.memo.R
import kh.edu.rupp.ite.memo.databinding.FragmentHomeBinding
import kh.edu.rupp.ite.memo.models.NoteResponse
import kh.edu.rupp.ite.memo.utils.NetworkResponse
import kh.edu.rupp.ite.memo.viewmodel.NoteViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = NoteAdapter(::onNoteClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(2) // Default layout
        setupDropdown()

        noteViewModel.getAllNotes()
        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_noteFragment)
        }
        bindObservers()
    }
    private fun setupDropdown() {
        binding.dropdownButton.setOnClickListener {
            showDropdownMenu()
        }
    }

    private fun showDropdownMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.dropdownButton)
        popupMenu.menu.apply {
            add(Menu.NONE, 0, 0, "Vertical")
            add(Menu.NONE, 1, 1, "Horizontal")
        }

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                0 -> setupRecyclerView(2)  //Vertical
                1 -> setupRecyclerView(1, LinearLayoutManager.VERTICAL) // Horizontal
            }
            true
        }

        popupMenu.show()
    }


    private fun setupRecyclerView(spanCount: Int, orientation: Int = RecyclerView.VERTICAL) {
        layoutManager = if (orientation == RecyclerView.HORIZONTAL) {
            LinearLayoutManager(requireContext(), orientation, false)
        } else {
            StaggeredGridLayoutManager(spanCount, orientation)
        }
        binding.noteList.layoutManager = layoutManager
        binding.noteList.adapter = adapter
    }
    private fun bindObservers() {
        noteViewModel.notesLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResponse.Success -> {
                    adapter.submitList(it.data)
                }
                is NetworkResponse.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResponse.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onNoteClicked(noteResponse: NoteResponse){
        val bundle = Bundle()
        bundle.putString("note", Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_homeFragment_to_noteFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
