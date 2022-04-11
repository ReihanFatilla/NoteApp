package com.reihan.noteapp.presentation.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.reihan.noteapp.R
import com.reihan.noteapp.data.room.Note
import com.reihan.noteapp.data.room.Priority
import com.reihan.noteapp.databinding.FragmentUpdateBinding
import com.reihan.noteapp.presentation.NoteViewModel
import com.reihan.noteapp.utils.ExtensionFunctions.setActionBar
import com.reihan.noteapp.utils.HelperFunction.parseToPriority
import com.reihan.noteapp.utils.HelperFunction.setPriorityColor
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val args by navArgs<UpdateFragmentArgs>()

    private val updateViewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding.updateArgs = args

        binding.apply{
            //Mengatur Toolbar yang erada di layout
            toolbarUpdate.setActionBar(requireActivity())
            // Memberi warna pada spinner priority
            spinnerPrioritiesUpdate.onItemSelectedListener =
                context?.let { setPriorityColor(it, priorityIndicator) }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_update, menu)
        //Menginisialisasi Menu save
        val item = menu.findItem(R.id.menu_save)
        //Memanggil Button yang ada di layout menu_save untuk memberi CLickListener
        item.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            updateNote()
            val action = UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(args.note)
            findNavController().navigate(action)
            Toast.makeText(context, "Successfully Updating Note", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNote() {
        binding.apply{
            val title = edtTitleUpdate.text.toString()
            val description = edtDescriptionUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()

            val calendar = Calendar.getInstance().time
            val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar)

            val updatedData = Note(
                args.note.id,
                title,
                formattedDate,
                description,
                parseToPriority(context, priority)
            )
            updateViewModel.updateNote(updatedData)
        }
    }

}
