package com.reihan.noteapp.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.reihan.noteapp.MainActivity
import com.reihan.noteapp.R
import com.reihan.noteapp.data.room.Note
import com.reihan.noteapp.databinding.FragmentHomeBinding
import com.reihan.noteapp.presentation.NoteAdapter
import com.reihan.noteapp.presentation.NoteViewModel
import com.reihan.noteapp.utils.ExtensionFunctions.setActionBar
import com.reihan.noteapp.utils.HelperFunction
import com.reihan.noteapp.utils.HelperFunction.checkIfDataIsEmpty


class HomeFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val homeViewModel: NoteViewModel by viewModels()
    private val homeAdapter by lazy { NoteAdapter() }

    private var _currentData: List<Note>? = null
    private val currentData get() = _currentData as List<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.mHelperFunction = HelperFunction

        binding.apply {
            toolbarHome.setActionBar(requireActivity())

            fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        homeViewModel.getNoteData().observe(viewLifecycleOwner){
            checkIfDataIsEmpty(it)
            homeAdapter.setData(it)
            _currentData = it
        }
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, 1)
        }
        swipeToDelete(binding.rvHome)
    }

//    private fun checkNotesIsEmpty(data: List<Note>) {
//        binding.apply {
//            if (data.isEmpty()) {
//                imgNoNote.visibility = View.VISIBLE
//                rvHome.visibility = View.INVISIBLE
//            } else {
//                imgNoNote.visibility = View.INVISIBLE
//                rvHome.visibility = View.VISIBLE
//            }
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)

        val searchView = menu.findItem(R.id.menu_search)
        val actionView = searchView.actionView as? androidx.appcompat.widget.SearchView
        actionView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchNoteByQuery(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchNoteByQuery(it)
        }
        return true
    }

    private fun searchNoteByQuery(query: String) {
        val querySearch = "%$query%"
        homeViewModel.searchNoteQuery(querySearch).observe(this) {
            homeAdapter.setData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_priority_high -> homeViewModel.sortByHighPriority.observe(this) {
                homeAdapter.setData(it)
            }
            R.id.menu_priority_low -> homeViewModel.sortByLowPriority.observe(this) {
                homeAdapter.setData(it)
            }
            R.id.menu_delete -> confirmDeleteAllNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteAllNote() {
        if (currentData.isEmpty()) {
            AlertDialog.Builder(context)
                .setTitle("No Notes")
                .setMessage("There is no data to delete here")
                .setPositiveButton("Ok") { _, _ -> }
                .show()

        } else {
            AlertDialog.Builder(context)
                .setTitle("Delete All Note")
                .setMessage("Are you sure want to remove all of the notes?")
                .setPositiveButton("Yes") { _, _ ->
                    homeViewModel.deleteAllNote()
                    Toast.makeText(context, "Successfully deleted note", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { _, _ -> }
                .show()
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = homeAdapter.listNote[viewHolder.adapterPosition]
                homeViewModel.deleteNote(deletedItem)
                reStoredData(viewHolder.itemView, deletedItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun reStoredData(view: View, deletedItem: Note) {
        Snackbar.make(view, "Are You Sure Want To Delete \"${deletedItem.title}\"", Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(view.context, R.color.black))
            .setAction("Undo"){
                homeViewModel.insertNote(deletedItem)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}