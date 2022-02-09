package com.android.bitsandpizzas

class Pizza(val name: String, val imageResourceId: Int) {
    companion object{
        val pizza = arrayOf(
            Pizza("Diavolo", R.drawable.diavolo),
            Pizza("Funghi", R.drawable.funghi)
        )
    }
}