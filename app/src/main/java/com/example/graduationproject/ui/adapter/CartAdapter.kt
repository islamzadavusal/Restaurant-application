package com.example.graduationproject.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.graduationproject.data.entity.FoodsCart
import com.example.graduationproject.databinding.CardDesignCartBinding
import com.example.graduationproject.ui.viewmodel.MyCartViewModel

class CartAdapter(
    var mContext: Context,
    var cartList:List<FoodsCart>,
    var viewModel: MyCartViewModel
) : RecyclerView.Adapter<CartAdapter.CardDesignHolder>() {

    inner class CardDesignHolder(var binding: CardDesignCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val binding = CardDesignCartBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CardDesignHolder, position: Int) {
        val foodcart = cartList.get(position)
        val b = holder.binding

        val url = "http://kasimadalan.pe.hu/foods/images/${foodcart.image}"
        Glide.with(mContext).load(url).into(b.imageViewCart)

        b.textViewFoodNameCart.text = "${foodcart.name}"
        b.textViewPriceCart.text = "${foodcart.price} ₼"
        b.textViewUsername.text = "Username:  ${foodcart.userName}"
        b.textViewCategoryinCart.text = "Category:  ${foodcart.category}"
        b.textVieworderAmountCart.text = "Order:  ${foodcart.orderAmount}"

        b.imageViewDelete.setOnClickListener{
            val builder = AlertDialog.Builder(mContext)
            builder.setMessage("Do you want to delete ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                    viewModel.delete(foodcart.cartId, foodcart.userName)
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
            dialog.setOnShowListener {
                val messageView = dialog.findViewById<TextView>(android.R.id.message)
                messageView.setTextColor(Color.parseColor("#FFFFFFFF"))

                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setTextColor(Color.WHITE)

                val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                negativeButton.setTextColor(Color.WHITE)
            }
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}

