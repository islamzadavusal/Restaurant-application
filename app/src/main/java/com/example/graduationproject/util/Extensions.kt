package com.example.graduationproject.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.go(it:View,id:NavDirections){
    findNavController(it).navigate(id)

}