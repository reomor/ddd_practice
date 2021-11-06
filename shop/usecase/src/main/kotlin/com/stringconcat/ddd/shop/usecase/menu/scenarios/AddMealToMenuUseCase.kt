package com.stringconcat.ddd.shop.usecase.menu.scenarios

import arrow.core.Either
import com.stringconcat.ddd.shop.domain.menu.AlreadyExistsWithSameNameError
import com.stringconcat.ddd.shop.domain.menu.Meal
import com.stringconcat.ddd.shop.domain.menu.MealAlreadyExists
import com.stringconcat.ddd.shop.domain.menu.MealId
import com.stringconcat.ddd.shop.domain.menu.MealIdGenerator
import com.stringconcat.ddd.shop.usecase.menu.AddMealToMenu
import com.stringconcat.ddd.shop.usecase.menu.AddMealToMenuRequest
import com.stringconcat.ddd.shop.usecase.menu.AddMealToMenuUseCaseError

class AddMealToMenuUseCase(
    private val mealPersister: MealPersister,
    private val idGenerator: MealIdGenerator,
    private val mealExists: MealAlreadyExists
) : AddMealToMenu {

    override fun execute(request: AddMealToMenuRequest): Either<AddMealToMenuUseCaseError, MealId> =
        Meal.addMealToMenu(
            idGenerator = idGenerator,
            mealExists = mealExists,
            name = request.name,
            description = request.description,
            price = request.price
        ).mapLeft { e -> e.toError() }
            .map { meal ->
                mealPersister.save(meal)
                meal.id
            }
}

fun AlreadyExistsWithSameNameError.toError() = AddMealToMenuUseCaseError.AlreadyExists