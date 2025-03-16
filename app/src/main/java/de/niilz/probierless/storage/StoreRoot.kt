package de.niilz.probierless.storage

import de.niilz.probierless.storage.entity.DrinkEntity

class StoreRoot {

    var drinks = mutableMapOf<Int, DrinkEntity>()
}
