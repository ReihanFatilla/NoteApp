package com.reihan.noteapp.utils

import android.view.View
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.card.MaterialCardView
import com.reihan.noteapp.R
import com.reihan.noteapp.data.room.Note
import com.reihan.noteapp.data.room.Priority
import com.reihan.noteapp.presentation.home.HomeFragmentDirections

object BindingAdapter {
    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(cardView: MaterialCardView, priority: Priority){
        when(priority){
            Priority.HIGH -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink))
            Priority.MEDIUM -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
            Priority.LOW -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
        }
    }

    @BindingAdapter("android:sendDataToDetail")
    @JvmStatic
    fun sendDataToDetail(view: ConstraintLayout, note: Note){
        view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(note)
            view.findNavController().navigate(action)
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(view: AppCompatSpinner, priority: Priority) {
        when (priority) {
            Priority.HIGH -> {
                view.setSelection(0)
            }
            Priority.MEDIUM -> {
                view.setSelection(1)
            }
            Priority.LOW -> {
                view.setSelection(2)
            }
        }
    }

    @BindingAdapter("android:emptyDatabase")
    @JvmStatic
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
        when(emptyDatabase.value){
            true -> view.visibility = View.VISIBLE
            else -> view.visibility = View.INVISIBLE
        }
    }
}