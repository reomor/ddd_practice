package com.stringconcat.dev.course.app.component

import com.stringconcat.ddd.common.events.DomainEventPublisher
import com.stringconcat.ddd.shop.persistence.cart.InMemoryCartRepository
import com.stringconcat.ddd.shop.persistence.cart.InMemoryIncrementalCartIdGenerator
import com.stringconcat.ddd.shop.persistence.menu.InMemoryIncrementalMealIdGenerator
import com.stringconcat.ddd.shop.persistence.menu.InMemoryMealRepository
import com.stringconcat.ddd.shop.persistence.order.InMemoryIncrementalShopOrderIdGenerator
import com.stringconcat.ddd.shop.persistence.order.InMemoryShopOrderRepository
import com.stringconcat.ddd.shop.usecase.MockOrderExporter
import com.stringconcat.dev.course.app.configuration.TelnetConfiguration
import com.stringconcat.dev.course.app.configuration.shop.ShopPaymentConfiguration
import com.stringconcat.dev.course.app.configuration.shop.ShopRestConfiguration
import com.stringconcat.dev.course.app.configuration.shop.ShopTelnetConfiguration
import com.stringconcat.dev.course.app.configuration.shop.ShopUseCaseConfiguration
import com.stringconcat.dev.course.app.event.EventPublisherImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    ShopPaymentConfiguration::class,
    ShopTelnetConfiguration::class,
    ShopRestConfiguration::class,
    ShopUseCaseConfiguration::class,
    TelnetConfiguration::class)
class ShopComponentTestConfiguration {
    @Bean
    fun eventPublisher() = EventPublisherImpl()

    @Bean
    fun mockOrderExporter() = MockOrderExporter()

    @Bean
    fun mealRepository(eventPublisher: DomainEventPublisher) =
        InMemoryMealRepository(eventPublisher)

    @Bean
    fun mealIdGenerator() = InMemoryIncrementalMealIdGenerator()

    @Bean
    fun cartRepository(eventPublisher: DomainEventPublisher) = InMemoryCartRepository(eventPublisher)

    @Bean
    fun shopOrderRepository(eventPublisher: DomainEventPublisher) = InMemoryShopOrderRepository(eventPublisher)

    @Bean
    fun cartIdGenerator() = InMemoryIncrementalCartIdGenerator()

    @Bean
    fun shopOrderIdGenerator() = InMemoryIncrementalShopOrderIdGenerator()
}