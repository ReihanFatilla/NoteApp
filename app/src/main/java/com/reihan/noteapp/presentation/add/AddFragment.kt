package com.reihan.noteapp.presentation.add

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.reihan.noteapp.R
import com.reihan.noteapp.data.room.Note
import com.reihan.noteapp.data.room.Priority
import com.reihan.noteapp.databinding.FragmentAddBinding
import com.reihan.noteapp.presentation.NoteViewModel
import com.reihan.noteapp.utils.ExtensionFunctions.setActionBar
import com.reihan.noteapp.utils.HelperFunction.setPriorityColor
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding

    private val addViewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Membuat dan menyambungkan toolbar
        setHasOptionsMenu(true)

        binding.apply{
            //Mengatur Toolbar yang erada di layout
            toolbarAdd.setActionBar(requireActivity())
            // Memberi warna pada spinner priority
            spinnerPriorities.onItemSelectedListener =
                context?.let { setPriorityColor(it, priorityIndicator) }
        }
    }

    // Untuk Menampilkan Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_update, menu)
        //Menginisialisasi Menu save
        val item = menu.findItem(R.id.menu_save)
        //Memanggil Button yang ada di layout menu_save untuk memberi CLickListener
        item.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener{
            // Memasukkan data dari ViewModel
            insertNote()
        }
    }

    private fun insertNote() {
        binding.apply{
            val title = edtTitle.text.toString()
            val desc = edtDescription.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()

            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar)

            if(edtTitle.text.isEmpty() || edtDescription.text.isEmpty()){
                when{
                    edtTitle.text.isEmpty() -> edtTitle.error = "Please fill this Field"
                    edtDescription.text.isEmpty() -> edtDescription.error = "Please fill this Field"
                    else -> {
                        edtTitle.error = "Please fill this Field"
                        edtDescription.error = "Please fill this Field"
                    }
                }
            } else {
                val data = Note(
                    0,
                    title,
                    date,
                    desc,
                    parseToPriority(priority)
                )
                addViewModel.insertNote(data)
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
                Toast.makeText(context, "Successfully Saving Note", Toast.LENGTH_SHORT).show()
                Log.i("AddNote", "Succes adding Note with data $data")
            }
        }
    }

    private fun parseToPriority(priority: String): Priority {
        val arrPriority = resources.getStringArray(R.array.priorities)
        return when(priority){
            arrPriority[0] -> Priority.HIGH
            arrPriority[1] -> Priority.MEDIUM
            arrPriority[2] -> Priority.LOW
            else -> Priority.LOW
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}