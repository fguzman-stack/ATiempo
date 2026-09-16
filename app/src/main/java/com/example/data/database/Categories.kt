package com.example.data.database

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val icon: ImageVector,
    val color: Long
)

val DEFAULT_CATEGORY_ICON_RESOURCE = Icons.Default.Star
const val DEFAULT_CATEGORY_COLOR = 0xFF90A4AE
val HEALTH_CATEGORY_ICON_RESOURCE = Icons.Default.Favorite
const val HEALTH_CATEGORY_COLOR = 0xFFE57373
val WORK_CATEGORY_ICON_RESOURCE = Icons.Default.Work
const val WORK_CATEGORY_COLOR = 0xFF4FC3F7
val STUDY_CATEGORY_ICON_RESOURCE = Icons.Default.School
const val STUDY_CATEGORY_COLOR = 0xFF9575CD
val HOME_CATEGORY_ICON_RESOURCE = Icons.Default.Home
const val HOME_CATEGORY_COLOR = 0xFFFFB74D
val FINANCE_CATEGORY_ICON_RESOURCE = Icons.Default.AttachMoney
const val FINANCE_CATEGORY_COLOR = 0xFF81C784
val SOCIAL_CATEGORY_ICON_RESOURCE = Icons.Default.People
const val SOCIAL_CATEGORY_COLOR = 0xFFBA68C8

object Categories {

    private val _categories = listOf(
        Category("Salud", HEALTH_CATEGORY_ICON_RESOURCE, HEALTH_CATEGORY_COLOR),
        Category("Trabajo", WORK_CATEGORY_ICON_RESOURCE, WORK_CATEGORY_COLOR),
        Category("Estudio", STUDY_CATEGORY_ICON_RESOURCE, STUDY_CATEGORY_COLOR),
        Category("Hogar", HOME_CATEGORY_ICON_RESOURCE, HOME_CATEGORY_COLOR),
        Category("Finanzas", FINANCE_CATEGORY_ICON_RESOURCE, FINANCE_CATEGORY_COLOR),
        Category("Social", SOCIAL_CATEGORY_ICON_RESOURCE, SOCIAL_CATEGORY_COLOR),
        Category("Otros", DEFAULT_CATEGORY_ICON_RESOURCE, DEFAULT_CATEGORY_COLOR)
    )

    fun getAllCategories(): List<Category> = _categories

    fun findByName(name: String): Category? = _categories.find { it.name == name }

    fun getIconResource(categoryName: String): ImageVector {
        return findByName(categoryName)?.icon ?: DEFAULT_CATEGORY_ICON_RESOURCE
    }

    fun getColorResource(categoryName: String): Long {
        return findByName(categoryName)?.color ?: DEFAULT_CATEGORY_COLOR
    }
}