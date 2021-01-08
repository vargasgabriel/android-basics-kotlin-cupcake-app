package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.cupcake.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<Flavor>()
    val flavor: LiveData<Flavor> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()
    val flavorOptions = getFlavors()

    init {
        resetOrder()
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: Flavor) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun flavorAllowPickupToday(): Boolean {
        val isAllowed = _flavor.value != flavorOptions[5]
        if (!isAllowed)
            setDate(dateOptions[1])

        return isAllowed
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    private fun getFlavors(): List<Flavor> {
        return listOf(
            Flavor(1, R.string.vanilla),
            Flavor(2, R.string.chocolate),
            Flavor(3, R.string.red_velvet),
            Flavor(4, R.string.salted_caramel),
            Flavor(5, R.string.coffee),
            Flavor(6, R.string.special_flavor)
        )
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = flavorOptions[0]
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    /*

        * Offer a special flavor that has some special conditions around it, such as not being available for same day pickup.
        * Ask the user for their name for the cupcake order.
        * Allow the user to select multiple cupcake flavors for their order if the quantity is more than 1 cupcake.

     */
}