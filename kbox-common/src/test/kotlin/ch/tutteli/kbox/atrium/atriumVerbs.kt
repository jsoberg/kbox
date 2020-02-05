@file:Suppress("MatchingDeclarationName")

package ch.tutteli.kbox.atrium

import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.reporting.ExpectBuilder
import ch.tutteli.atrium.reporting.translating.StringBasedTranslatable
import ch.tutteli.kbox.atrium.AssertionVerb.ASSERT

internal fun <T> assert(subject: T): Expect<T> =
    ExpectBuilder.forSubject(subject)
        .withVerb(ASSERT)
        .withoutOptions()
        .build()

internal fun <T : Any> assert(subject: T, createAssertions: Expect<T>.() -> Unit) =
    assert(subject).addAssertionsCreatedBy(createAssertions)

internal enum class AssertionVerb(override val value: String) : StringBasedTranslatable {
    ASSERT("assert"),
}
