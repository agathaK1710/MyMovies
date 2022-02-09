package com.android.bitsandpizzas

class Pasta(val name: String, val imageId: Int){
    companion object{
        val pasta = arrayOf(
            Pasta("Spaghetti Bolognese", R.drawable.spaghetti_bolognese),
            Pasta("Lesagne", R.drawable.lasagne)
        )
    }
}