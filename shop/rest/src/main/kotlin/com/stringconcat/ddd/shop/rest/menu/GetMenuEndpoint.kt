package com.stringconcat.ddd.shop.rest.menu

import com.stringconcat.ddd.shop.usecase.menu.GetMenu
import com.stringconcat.ddd.shop.usecase.menu.MealInfo
import java.math.BigDecimal
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetMenuEndpoint(private val getMenu: GetMenu) {

    @GetMapping(path = ["/menu"])
    fun getMenu(): ResponseEntity<RepresentationModel<*>> {
        val menuModel = getMenu.execute().map { MealModel.from(it) }

        val collectionModel = CollectionModel.of(menuModel)
            .add(linkTo(methodOn(GetMenuEndpoint::class.java).getMenu()).withSelfRel())

        return ResponseEntity.ok(collectionModel)
    }
}

@Relation(collectionRelation = "meals")
data class MealModel(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
) : RepresentationModel<MealModel>() {

    companion object {
        fun from(mealInfo: MealInfo): MealModel =
            MealModel(id = mealInfo.id.value,
                name = mealInfo.name.value,
                price = mealInfo.price.value,
                description = mealInfo.description.value)
    }
}