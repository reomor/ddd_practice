package com.stringconcat.ddd.shop.rest.menu

import com.stringconcat.ddd.shop.domain.menu.MealId
import com.stringconcat.ddd.shop.rest.API_V1_MENU
import com.stringconcat.ddd.shop.usecase.menu.RemoveMealFromMenu
import com.stringconcat.ddd.shop.usecase.menu.RemoveMealFromMenuUseCaseError
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RemoveMealFromMenuEndpoint(private val removeMealFromMenu: RemoveMealFromMenu) {

    @DeleteMapping(path = ["$API_V1_MENU/{id}"])
    fun execute(@PathVariable("id") mealId: Long) =
        removeMealFromMenu.execute(MealId(mealId))
            .fold({
                when (it) {
                    is RemoveMealFromMenuUseCaseError.MealNotFound -> resourceNotFound()
                }
            }, {
                noContent()
            })
}