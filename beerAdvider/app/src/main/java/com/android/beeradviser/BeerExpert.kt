package com.android.beeradviser

class BeerExpert {

    fun getBrands(color: String): List<String> {
        val brands: MutableList<String> = mutableListOf()

        if (color == "amber") {
            brands.add("Jack Amber")
            brands.add("Red Moose")
        } else {
            brands.add("Jail Pale Ale")
            brands.add("Gout State")
        }
        return brands
    }
}