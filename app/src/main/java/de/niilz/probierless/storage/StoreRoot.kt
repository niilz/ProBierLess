package de.niilz.probierless.storage

import de.niilz.probierless.storage.entity.DrinkEntity

class StoreRoot {

    var idSequence = 0
    var drinks = mutableMapOf<Int, DrinkEntity>()
    var alcoholDayLimit = 0
}
