package com.reihan.noteapp.presentation.detail

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.reihan.noteapp.MainActivity
import com.reihan.noteapp.R
import com.reihan.noteapp.databinding.FragmentDetailBinding
import com.reihan.noteapp.presentation.NoteViewModel
import com.reihan.noteapp.utils.ExtensionFunctions.setActionBar

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val args by navArgs<DetailFragmentArgs>()

    private val detailViewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.toolbarDetail.setActionBar(requireActivity())
        binding.detail = args

//        binding.apply{
//            tvTitleDetail.text = args.note.title
//            tvDescriptionDetail.text = args.note.desc
//            tvDateDetail.text = args.note.date
//        }
//
//        Log.i("detail", args.note.title)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_edit -> {
                val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(args.note)
                findNavController().navigate(action)
            }
            R.id.menu_delete -> confirmDeleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun confirmDeleteDialog() {
        AlertDialog.Builder(context)
            .setTitle("Delete \n${args.note.title}\"")
            .setMessage("Are You Sure Want To Delete This Note")
            .setPositiveButton("Yes") { _, _ ->
                detailViewModel.deleteNote(args.note)
                Toast.makeText(context, "Note Successfully Deleted", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }


}