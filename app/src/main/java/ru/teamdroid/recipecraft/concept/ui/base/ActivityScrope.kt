package ru.teamdroid.recipecraft.concept.ui.base

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * An activity scope for PresenterComponent.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScope